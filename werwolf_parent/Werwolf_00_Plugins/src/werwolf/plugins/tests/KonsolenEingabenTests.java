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

import werwolf.adapter.sql.SQLKartenRepository;
import werwolf.adapter.sql.SQLRollenRepository;
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
			EasyMock.expect(rs.getString("Name")).andReturn("Dorfbewohner");
			EasyMock.expect(rs.next()).andReturn(true);
			EasyMock.expect(rs.getString("Name")).andReturn("Werwolf");
			EasyMock.expect(rs.next()).andReturn(false);
			
			EasyMock.replay(rs);
				
		//Arrange
			
		
			//Karten init
			SQLKartenRepository repo = new SQLKartenRepository(null);
			repo.initialisiereKarten(rs);
			
			//Rollen init
			SQLRollenRepository role = new SQLRollenRepository(null);
			role.initialisiereRolle("Dorfbewohner", "lebt", "", false, false);
			role.initialisiereRolle("Werwolf", "frisst", "", false, false);
			
			//Karten und Rollen verknüpfen
			repo.verknuepfeKartenMit(role);
			
			//Hashmap mit Name und Funktion erstellen
			HashMap<String, String> namenFunk = new HashMap<String, String>();
			namenFunk = repo.zeigeNameUndFunktion();
			
			//Kommando Arrange
			String eingabe = "list-karten";
			String ausgabe = "";
			
			
		
		//Act
			try {
				Kommandos.executeMatching(eingabe);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		//Assert
		Assertions.assertEquals(":(" + System.lineSeparator(), outContent.toString());
		
		//Verify
		EasyMock.verify(rs);
		
		
	}
}
	
//	@Test
//	public void err() {
//	    System.err.print("hello again");
//	    assertEquals("hello again", errContent.toString());
//	}
//}
