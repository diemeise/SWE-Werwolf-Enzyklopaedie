package werwolf.plugins.main;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import werwolf.application.game.library.LibraryManager;

import werwolf.adapter.sql.SQLRollenRepository;
import werwolf.adapter.sql.SQLKartenRepository;
import werwolf.adapter.sql.SQLVerbindung;
import werwolf.plugins.sql.MySQLAuthentifizierung;
import werwolf.plugins.sql.MySQLVerbindung;
import werwolf.domain.game.content.Spieler;
import werwolf.domain.game.content.Rolle;
public class Main {
	
	private static String url;
	private static String nutzer;
	private static String passwort;
	
	private static String datei;
	

	public static void main(String[] args) {

		String basePath = new File("").getAbsolutePath();
		String relativePath = "\\data\\auth.txt";
	    String authconfigPath = basePath + relativePath;
		datei = authconfigPath;//"C:\\Users\\tamar\\eclipse-workspace\\SWE-Werwolf-Enzyklopaedie\\auth.txt";
		MySQLAuthentifizierung.ladeDatei(datei);
		
		url = MySQLAuthentifizierung.getUrl();
		nutzer = MySQLAuthentifizierung.getNutzer();
		passwort = MySQLAuthentifizierung.getPw();
	 
		//Fehler kann ignoriert werden?
		try{
			SQLVerbindung mysql = new MySQLVerbindung(url, nutzer, passwort);
			LibraryManager gameLibrary= new LibraryManager(new SQLKartenRepository(mysql), new SQLRollenRepository(mysql));
			gameLibrary.initialisiereLibrary();
			System.out.println("meep moop");
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
		

	}

}
