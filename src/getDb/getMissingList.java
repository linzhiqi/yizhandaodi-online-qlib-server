package getDb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.ListToString;
import util.PropertyReader;

/**
 * Servlet implementation class getMissingList
 */
public class getMissingList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<String> availableList;
	Logger logger = Logger.getLogger(getMissingList.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getMissingList() {
        super();
        
    }
    
    @Override
    public void init(){
    	try {
			super.init();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String absoluteDiskPath = getServletContext().getRealPath("/META-INF/questionDB.conf");
        System.out.println(absoluteDiskPath);
        String str = PropertyReader.getProperty("availableDbs", absoluteDiskPath);
        String[] strArray = str.split(",");
        availableList = new ArrayList<String>(Arrays.asList(strArray));
        logger.debug("availableList="+ListToString.listtoString(availableList));
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dbListStr = request.getParameter("dblist");
		if(dbListStr == null){
			logger.error("Abused by ip:"+request.getRemoteAddr()+". Request without para:dbname");
			String str = "Your ip are recorded, any possible damage will be prosecuted.";
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentLength(str.length());
			response.getWriter().write(str);
			return;
		}
		
		logger.info("dbList:" + dbListStr + " is received from ip:" + request.getRemoteAddr());
		String[] dbListArray = dbListStr.split(",");
		ArrayList<String> dbList  = new ArrayList<String>(Arrays.asList(dbListArray));
		ArrayList<String> missingList = (ArrayList<String>) this.availableList.clone();
		missingList.removeAll(dbList);
		String missingListStr = ListToString.listtoString(missingList);
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().write(missingListStr);
		logger.info("missingList:" + missingListStr + " is sent to ip:" + request.getRemoteAddr());
	}
		
		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
