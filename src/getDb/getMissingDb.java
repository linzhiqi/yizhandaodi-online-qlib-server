package getDb;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.ListToString;
import util.PropertyReader;

/**
 * Servlet implementation class getMissingDb
 */
public class getMissingDb extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(getMissingDb.class);
	private String repositoryPath;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getMissingDb() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		try {
			super.init();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String absoluteDiskPath = getServletContext().getRealPath(
				"/META-INF/questionDB.conf");
		System.out.println(absoluteDiskPath);
		repositoryPath = PropertyReader.getProperty("questionRepositoryPath",
				absoluteDiskPath);
		System.out.println("repositoryPath="+repositoryPath);
		logger.debug("questionRepositoryPath=" + repositoryPath);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream output = null;
		int sum = 0;
		String dbFileName = request.getParameter("dbname");
		if(dbFileName == null){
			logger.error("Abused by ip:"+request.getRemoteAddr()+". Request without para:dbname");
			response.getWriter().write("Your ip are recorded, any possible damage will be prosecuted.");
			return;
		}
		logger.info("dbName:" + dbFileName + " is requested by ip:" + request.getRemoteAddr());
		System.out.println("dbName:" + dbFileName + " is requested by ip:" + request.getRemoteAddr());
		try {
			output = response.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		InputStream input = null;
		try {
			// serve a fixed file, located in the root folder of this web
			// app
			input = new FileInputStream(repositoryPath+"/"+dbFileName);
			// if raw bytes are already available, use this instead :
			// InputStream inputBytes = new ByteArrayInputStream(myBytes);

			// transfer input stream to output stream, via a buffer
			byte[] buffer = new byte[2048];
			int bytesRead;
			while ((bytesRead = input.read(buffer)) != -1) {
				sum += bytesRead;
				output.write(buffer, 0, bytesRead);
			}
			logger.info("db " + dbFileName + " is sent to ip:" + request.getRemoteAddr());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		} finally {
			// close all streams
			try {
				output.close();
				if (input != null)
					input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}

		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
