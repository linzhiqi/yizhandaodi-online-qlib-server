package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PropertyReader {
	public static String getProperty(String propertyName, String realPath) {
        BufferedReader reader = null;
        String res = null;

        try {
        	InputStream input = new FileInputStream(realPath);
            reader = new BufferedReader(new InputStreamReader(input));
            String text = null;
            while ((text = reader.readLine()) != null) {
                if (text.indexOf('=') > 0) {                    
                    String temp = text.substring(0, text.indexOf('=')).trim();                    
                    if (propertyName.equalsIgnoreCase(temp)) {
                        res = text.substring(text.indexOf('=') + 1).trim();
                        break;
                    }                
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}
