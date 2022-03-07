package werwolf.adapter.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import werwolf.adapter.sql.SQLKartenRepository;
import werwolf.adapter.sql.SQLRollenRepository;


public class KartenUndRollenTest {
	
	/**
	 * Testet SQLKartenRepository.listeKartenNamen() 
	 * -> werden die Karten alle und in der richtigen Reihenfolge ausgegeben
	 *
	 * @throws SQLException
	 */
	@Test 
	public void listeKartenNamenTest() throws SQLException  {
			
		//Capture
		ResultSet rs = EasyMock.createMock(ResultSet.class);
		EasyMock.expect(rs.next()).andReturn(true);
		EasyMock.expect(rs.getString("Name")).andReturn("Dorfbewohner");
		EasyMock.expect(rs.next()).andReturn(true);
		EasyMock.expect(rs.getString("Name")).andReturn("Werwolf");
		EasyMock.expect(rs.next()).andReturn(false);
		
		EasyMock.replay(rs);
				
		//Arrange
		SQLKartenRepository repo = new SQLKartenRepository(null);
		repo.initialisiereKarten(rs);
		
		ArrayList<String> namen = new ArrayList<String>();
		ArrayList<String> testListe = new ArrayList<String>();
		testListe.add("Dorfbewohner");
		testListe.add("Werwolf");
		
		//Act
		namen = repo.listeAllerNamen();
		
		//Assert
		Assertions.assertEquals(testListe, namen);
		
		//Verify
		EasyMock.verify(rs);
	}
	
	
	
	/**
	 * Testet die Methode initialiserRolle() im SQLRollenRepository
	 * 
	 * @throws SQLException
	 */
	@Test
	public void initialisiereRolleTest() {
			

		//Arrange
		SQLRollenRepository repo = new SQLRollenRepository(null);
//		repo.initialisiereKarten(rs);
		repo.initialisiereRolle("Dorfbewohner", "lebt", "", false, false);
		repo.initialisiereRolle("Werwolf", "frisst", "", false, false);
		
		ArrayList<String> namen = new ArrayList<String>();
		ArrayList<String> testListe = new ArrayList<String>();
		testListe.add("Dorfbewohner");
		testListe.add("Werwolf");
		
		//Act
		namen = repo.listeAllerNamen();
		
		//Assert
		Assertions.assertEquals(testListe, namen);

	}
	
	
	@Test
	public void zeigeNameUndFunktionTest() throws SQLException  {
		
		//Capture
		ResultSet rs = EasyMock.createMock(ResultSet.class);
		EasyMock.expect(rs.next()).andReturn(true);
		EasyMock.expect(rs.getString("Name")).andReturn("Dorfbewohner");
		EasyMock.expect(rs.next()).andReturn(true);
		EasyMock.expect(rs.getString("Name")).andReturn("Werwolf");
		EasyMock.expect(rs.next()).andReturn(false);
		
		EasyMock.replay(rs);
				
		//Arrange
		SQLKartenRepository repo = new SQLKartenRepository(null);
		repo.initialisiereKarten(rs);
		repo.verknuepfeKartenMit(null);
		
		SQLRollenRepository role = new SQLRollenRepository(null);
		role.initialisiereRolle("Dorfbewohner", "lebt", "", false, false);
		role.initialisiereRolle("Werwolf", "frisst", "", false, false);
		
		repo.verknuepfeKartenMit(role);
		
		HashMap<String, String> namenFunk = new HashMap<String, String>();
		HashMap<String, String> testHash = new HashMap<String, String>();
		testHash.put("Dorfbewohner", "lebt");
		testHash.put("Werwolf", "frisst");
		
		//Act
		namenFunk = repo.zeigeNameUndFunktion();
		
		//Assert
		Assertions.assertEquals(testHash, namenFunk);
	}
}
