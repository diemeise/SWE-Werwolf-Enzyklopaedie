package werwolf.plugins.tests;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.System;

import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import werwolf.adapter.sql.OutputAdapter;
import werwolf.adapter.sql.SQLKartenRepository;
import werwolf.adapter.sql.SQLRollenRepository;
import werwolf.application.game.library.LibraryManager;
import werwolf.plugins.console.Kommandos;

public class KonsolenEingabenTests{
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@BeforeEach
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@AfterEach
	public void restoreStreams() {
	    System.setOut(originalOut);
	    System.setErr(originalErr);
	}

	
	//Test
	@Test
	public void fuerKartenliste() throws SQLException  {
			
				
		//Capture
			ResultSet rs = EasyMock.createMock(ResultSet.class);
			EasyMock.expect(rs.next()).andReturn(true);
			EasyMock.expect(rs.getString("Name")).andReturn("Werwolf");
			EasyMock.expect(rs.next()).andReturn(true);
			EasyMock.expect(rs.getString("Name")).andReturn("Dorfbewohner");
			EasyMock.expect(rs.next()).andReturn(false);
			
			EasyMock.replay(rs);
				
		//Arrange
			
		
			//Karten init
			SQLKartenRepository repo = new SQLKartenRepository(null);
			repo.initialisiereKarten(rs);
			
			//Rollen init
			SQLRollenRepository role = new SQLRollenRepository(null);
			role.initialisiereRolle("Werwolf", "frisst", "", false, false);
			role.initialisiereRolle("Dorfbewohner", "lebt", "", false, false);
			
			
			//Karten und Rollen verkn�pfen
			repo.verknuepfeKartenMit(role);
			LibraryManager gamelib = new LibraryManager(repo, role);
			OutputAdapter out = new OutputAdapter(gamelib);
			
			//Kommando Arrange
			String eingabe = "list-karten";
			
			
		
		//Act
			try {
				Kommandos.executeMatching(eingabe, out);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		//Assert
		Assertions.assertEquals("Werwolf: frisst" + System.lineSeparator() + "Dorfbewohner: lebt" + System.lineSeparator(), outContent.toString());
		
		//Verify
		EasyMock.verify(rs);
		
		
	}
	
	@Test
	public void fuerSpezialListe() throws SQLException  {
			
				
		//Capture
			ResultSet rs = EasyMock.createMock(ResultSet.class);
			EasyMock.expect(rs.next()).andReturn(true);
			EasyMock.expect(rs.getString("Name")).andReturn("Werwolf");
			EasyMock.expect(rs.next()).andReturn(true);
			EasyMock.expect(rs.getString("Name")).andReturn("Dorfbewohner");
			EasyMock.expect(rs.next()).andReturn(true);
			EasyMock.expect(rs.getString("Name")).andReturn("Wei�er Werwolf");
			EasyMock.expect(rs.next()).andReturn(false);
			
			EasyMock.replay(rs);
				
		//Arrange
			
		
			//Karten init
			SQLKartenRepository repo = new SQLKartenRepository(null);
			repo.initialisiereKarten(rs);
			
			//Rollen init
			SQLRollenRepository role = new SQLRollenRepository(null);
			role.initialisiereRolle("Werwolf", "frisst", "", true, false);
			role.initialisiereRolle("Dorfbewohner", "lebt", "", false, false);
			role.initialisiereRolle("Wei�er Werwolf", "frisst Dorfbewohner und Woelfe", "", true, true);
			
			
			
			//Karten und Rollen verkn�pfen
			repo.verknuepfeKartenMit(role);
			LibraryManager gamelib = new LibraryManager(repo, role);
			OutputAdapter out = new OutputAdapter(gamelib);
			
			//Kommando Arrange
			String eingabe = "list-spezial";
			
			
		
		//Act
			try {
				Kommandos.executeMatching(eingabe, out);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		//Assert
		Assertions.assertEquals("Wei�er Werwolf: frisst Dorfbewohner und Woelfe" + System.lineSeparator() , outContent.toString());
		
		//Verify
		EasyMock.verify(rs);
		
		
	}
	
	@Test
	public void fuerSucheNachNamen() throws SQLException  {
			
				
		//Capture
			ResultSet rs = EasyMock.createMock(ResultSet.class);
			EasyMock.expect(rs.next()).andReturn(true);
			EasyMock.expect(rs.getString("Name")).andReturn("Werwolf");
			EasyMock.expect(rs.next()).andReturn(true);
			EasyMock.expect(rs.getString("Name")).andReturn("Dorfbewohner");
			EasyMock.expect(rs.next()).andReturn(false);
			
			EasyMock.replay(rs);
				
		//Arrange
			
		
			//Karten init
			SQLKartenRepository repo = new SQLKartenRepository(null);
			repo.initialisiereKarten(rs);
			
			//Rollen init
			SQLRollenRepository role = new SQLRollenRepository(null);
			role.initialisiereRolle("Werwolf", "frisst", "Erwacht Nachts und sucht mit seinem Rudel ein Opfer zum fressen", true, false);
			role.initialisiereRolle("Dorfbewohner", "lebt", "", false, false);
						
			
			//Karten und Rollen verkn�pfen
			repo.verknuepfeKartenMit(role);
			LibraryManager gamelib = new LibraryManager(repo, role);
			OutputAdapter out = new OutputAdapter(gamelib);
			
			//Kommando Arrange
			String eingabe = "suche Werwolf";
			
			
		
		//Act
			try {
				Kommandos.executeMatching(eingabe, out);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		//Assert
		Assertions.assertEquals("Name: Werwolf" + System.lineSeparator() +
								"Funktion: frisst" + System.lineSeparator() +
								"Beschreibung: Erwacht Nachts und sucht mit seinem Rudel ein Opfer zum fressen" + System.lineSeparator() +
								"Gesinnung: boese" + System.lineSeparator() +
								"Spezialrolle: nein" + System.lineSeparator(), outContent.toString());
		
		
		//Verify
		EasyMock.verify(rs);
		
		
	}
}

