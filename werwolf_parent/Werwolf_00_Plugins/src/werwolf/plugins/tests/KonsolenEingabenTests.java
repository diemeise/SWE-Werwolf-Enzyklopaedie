package werwolf.plugins.tests;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import werwolf.adapter.sql.SQLRollenRepository;

public class KonsolenEingabenTests {
	
//	@Test
//	public void fuerRichtigeEingabe() throws SQLException  {
//			
//		//Capture
//		ResultSet rs = EasyMock.createMock(ResultSet.class);
//		EasyMock.expect(rs.next()).andReturn(true);
//		EasyMock.expect(rs.getString("Name")).andReturn("Dorfbewohner");
//		EasyMock.expect(rs.getString("Name")).andReturn("Dorfbewohner");
//		EasyMock.expect(rs.getString("Funktion")).andReturn("Lebt");
//		EasyMock.expect(rs.getBoolean("istBoese")).andReturn(false);
//		EasyMock.expect(rs.getBoolean("istSpezial")).andReturn(false);
//		EasyMock.expect(rs.next()).andReturn(true);
//		EasyMock.expect(rs.getString("Name")).andReturn("Werwolf");
//		EasyMock.expect(rs.getString("Name")).andReturn("Werwolf");
//		EasyMock.expect(rs.getString("Funktion")).andReturn("Frisst");
//		EasyMock.expect(rs.getBoolean("istBoese")).andReturn(true);
//		EasyMock.expect(rs.getBoolean("istSpezial")).andReturn(false);
//		EasyMock.expect(rs.next()).andReturn(false);
//		
//		EasyMock.replay(rs);
//				
//		//Arrange
//		SQLRollenRepository repo = new SQLRollenRepository(null);
//		
//		//Act
//		repo.initialisiereRollen(rs);
//		repo.findeAlleRollen();
//		
//		//Assert
//		Assertions.assertFalse(repo.findeDurch("Dorfbewohner").get().istBoese());
//		
//		//Verify
//		EasyMock.verify(rs);
//		
//	}
}
