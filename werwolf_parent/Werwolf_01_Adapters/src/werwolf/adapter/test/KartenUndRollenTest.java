package werwolf.adapter.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	public void initialisiereRolleTest() throws SQLException  {
			

		//Arrange
		SQLRollenRepository repo = new SQLRollenRepository(null);
//		repo.initialisiereKarten(rs);
		repo.initialisiereRolle("Dorfbewohner", "lebt", "", false, false, 0);
		repo.initialisiereRolle("Werwolf", "frisst", "", false, false, 1);
		
		ArrayList<String> namen = new ArrayList<String>();
		ArrayList<String> testListe = new ArrayList<String>();
		testListe.add("Dorfbewohner");
		testListe.add("Werwolf");
		
		//Act
		namen = repo.listeAllerNamen();
		
		//Assert
		Assertions.assertEquals(testListe, namen);

	}
	
}
