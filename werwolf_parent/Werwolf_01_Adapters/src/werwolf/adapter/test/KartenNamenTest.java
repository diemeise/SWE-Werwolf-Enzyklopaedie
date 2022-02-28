package werwolf.adapter.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import werwolf.adapter.sql.SQLKartenRepository;


public class KartenNamenTest {
	
	@Test
	public void alleKartenNamenTest() throws SQLException  {
			
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
}
