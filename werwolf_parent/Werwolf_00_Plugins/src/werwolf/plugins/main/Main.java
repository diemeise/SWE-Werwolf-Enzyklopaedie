package werwolf.plugins.main;

import java.io.File;

import werwolf.application.game.library.LibraryManager;

import werwolf.adapter.sql.SQLRollenRepository;
import werwolf.adapter.output.OutputAdapter;
import werwolf.adapter.sql.SQLKartenRepository;
import werwolf.adapter.sql.SQLVerbindung;
import werwolf.plugins.console.KonsolenMain;
import werwolf.plugins.gui.GUIMain;
import werwolf.plugins.sql.MySQLAuthentifizierung;
import werwolf.plugins.sql.MySQLVerbindung;
public class Main {
	
	private static String url;
	private static String nutzer;
	private static String passwort;	
	private static String datei;	
	private static OutputAdapter outputAdapter;

	public static void main(String[] args) {

		
		String basePath = new File("").getAbsolutePath();
		String relativePath = "\\data\\auth.txt";
	    String authconfigPath = basePath + relativePath;
		datei = authconfigPath;
		MySQLAuthentifizierung.ladeDatei(datei);
		
		url = MySQLAuthentifizierung.getUrl();
		nutzer = MySQLAuthentifizierung.getNutzer();
		passwort = MySQLAuthentifizierung.getPw();
	 
		//Fehler kann ignoriert werden
		try{
			SQLVerbindung mysql = new MySQLVerbindung(url, nutzer, passwort);
			//Initialisierung des Library-Singletons
			LibraryManager gameLibrary= LibraryManager.INSTANCE;
			gameLibrary.setKartenRepository(new SQLKartenRepository(mysql));
			gameLibrary.setRollenRepository(new SQLRollenRepository(mysql));
			gameLibrary.initialisiereLibrary();
			outputAdapter = new OutputAdapter();
			System.out.println("meep moop");
			//GUIMain.main(args);
			
			KonsolenMain.exeKonsole(new OutputAdapter());
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	//
	public static OutputAdapter getOutputAdapter() {
		return outputAdapter;		
	}

}
