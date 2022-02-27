package werwolf.plugins.sql;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException; 

public class MySQLAuthentifizierung {
	
	private static String url;
	private static String nutzer;
	private static String pw;
	
    public static void main(String[] args) {
        String dateiName = "C:\\Users\\tamar\\eclipse-workspace\\SWE-Werwolf-Enzyklopaedie\\auth.txt";
        ladeDatei(dateiName);
    } 
	
	public static void ladeDatei(String datei) {
		
		BufferedReader in = null;
		
        try {
        	in = new BufferedReader(new FileReader(datei));
        	url = in.readLine();
        	nutzer = in.readLine();
        	pw = in.readLine();
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
    }

	public static String getNutzer() {
		return nutzer;
	}

	public static String getPw() {
		return pw;
	}
	
	public static String getUrl() {
		return url;
	}
	

	
}
