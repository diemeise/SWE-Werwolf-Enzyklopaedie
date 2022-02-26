package werwolf.plugins.main;

import java.sql.SQLException;

import werwolf.application.game.library.LibraryManager;
import werwolf.adapter.sql.SQLKartenRespository;
import werwolf.adapter.sql.SQLRollenRepository;
import werwolf.adapter.sql.SQLVerbindung;
import werwolf.domain.game.content.KartenRepository;
import werwolf.domain.game.content.RollenRepository;
import werwolf.plugins.sql.MySQLVerbindung;

public class Main {

	

	public static void main(String[] args) {
		
	 
		//Fehler kann ignoriert werden?
		try{
			SQLVerbindung mysql = new MySQLVerbindung();
			LibraryManager gameLibrary= new LibraryManager(new SQLKartenRespository(mysql), new SQLRollenRepository(mysql));
			gameLibrary.initialisiereLibrary();
			System.out.println("meep moop");
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
		

	}

}
