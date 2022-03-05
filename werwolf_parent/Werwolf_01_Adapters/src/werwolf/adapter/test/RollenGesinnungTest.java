package werwolf.adapter.test;

import org.junit.jupiter.api.Assertions;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;


import werwolf.adapter.sql.SQLRollenRepository;

public class RollenGesinnungTest {
	
	@Test
	public void fuerGuteRollen() throws SQLException {
		//Capture
		ResultSet rs = EasyMock.createMock(ResultSet.class);
		EasyMock.expect(rs.next()).andReturn(true);
		EasyMock.expect(rs.getString("Name")).andReturn("Dorfbewohner");
		EasyMock.expect(rs.getString("Funktion")).andReturn("Lebt");
		EasyMock.expect(rs.getBoolean("istBoese")).andReturn(false);
		EasyMock.expect(rs.getBoolean("istSpezial")).andReturn(false);
		EasyMock.expect(rs.getInt("prioritaet")).andReturn(0);
		
		EasyMock.expect(rs.next()).andReturn(false);
		EasyMock.replay(rs);
				
		//Arrange
		SQLRollenRepository repo = new SQLRollenRepository(null);
		
		//Act
		repo.initialisiereRollen(rs);
		
		//Assert
		Assertions.assertFalse(repo.findeDurch("Dorfbewohner").get().istBoese());
		
		//Verify
		EasyMock.verify(rs);
	}
	


	@Test
	public void fuerBoeseRollen() throws SQLException {
		//Capture
		ResultSet rs = EasyMock.createMock(ResultSet.class);
		EasyMock.expect(rs.next()).andReturn(true);
		EasyMock.expect(rs.getString("Name")).andReturn("Werwolf");
	EasyMock.expect(rs.getString("Funktion")).andReturn("Frisst Dorfbewohner");
		EasyMock.expect(rs.getBoolean("istBoese")).andReturn(true);
		EasyMock.expect(rs.getBoolean("istSpezial")).andReturn(true);
		EasyMock.expect(rs.getInt("prioritaet")).andReturn(0);
		
		EasyMock.expect(rs.next()).andReturn(false);
		EasyMock.replay(rs);
				
		//Arrange
		SQLRollenRepository repo = new SQLRollenRepository(null);
		
		//Act
		repo.initialisiereRollen(rs);
		
		//Assert
		Assertions.assertTrue(repo.findeDurch("Werwolf").get().istBoese());
		
		//Verify
		EasyMock.verify(rs);
	}
	
	@Test
	public void fuerSpezialRollen() throws SQLException {
		//Capture
		ResultSet rs = EasyMock.createMock(ResultSet.class);
		EasyMock.expect(rs.next()).andReturn(true);
		EasyMock.expect(rs.getString("Name")).andReturn("Wei�er Werwolf");
		EasyMock.expect(rs.getString("Funktion")).andReturn("Frisst Dorfbewohner und Werw�lfe");
		EasyMock.expect(rs.getBoolean("istBoese")).andReturn(true);
		EasyMock.expect(rs.getBoolean("istSpezial")).andReturn(true);
		EasyMock.expect(rs.getInt("prioritaet")).andReturn(4);
		EasyMock.expect(rs.next()).andReturn(false);
		EasyMock.replay(rs);
				
		//Arrange
		SQLRollenRepository repo = new SQLRollenRepository(null);
		
		//Act
		repo.initialisiereRollen(rs);
		
		//Assert
		Assertions.assertTrue(repo.findeDurch("Wei�er Werwolf").get().istSpezial());
		
		//Verify
		EasyMock.verify(rs);
	}
	
	@Test
	public void fuerNormaleRollen() throws SQLException {
		//Capture
				ResultSet rs = EasyMock.createMock(ResultSet.class);
				EasyMock.expect(rs.next()).andReturn(true);
				EasyMock.expect(rs.getString("Name")).andReturn("Werwolf");
				EasyMock.expect(rs.getString("Funktion")).andReturn("Frisst Dorfbewohner");
				EasyMock.expect(rs.getBoolean("istBoese")).andReturn(false);
				EasyMock.expect(rs.getBoolean("istSpezial")).andReturn(false);
				EasyMock.expect(rs.getInt("prioritaet")).andReturn(3);
				
				EasyMock.expect(rs.next()).andReturn(false);
				EasyMock.replay(rs);
						
				//Arrange
				SQLRollenRepository repo = new SQLRollenRepository(null);
				
				//Act
				repo.initialisiereRollen(rs);
				
				//Assert
				Assertions.assertFalse(repo.findeDurch("Werwolf").get().istSpezial());
				
				//Verify
				EasyMock.verify(rs);
	}

}
