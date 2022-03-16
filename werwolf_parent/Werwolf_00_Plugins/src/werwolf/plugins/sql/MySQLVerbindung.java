//TODO ist eigentlich ein Plugin?
package werwolf.plugins.sql;

import werwolf.adapter.sql.SQLVerbindung;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class MySQLVerbindung implements SQLVerbindung {
	

	private  Connection verbindung;

	
	//private Konstruktor für Testzwecke
//	public MySQLVerbindung() throws SQLException {	
//		
//		
//		this("jdbc:mysql://46.20.34.173/s1440177",nutzer,passwort);
//	}
	
	public MySQLVerbindung(String url, String benutzer, String passwort) throws SQLException {
		this.verbindung= this.connectTo(url,  benutzer, passwort);
	}
	
	public Connection connectTo(String url, String benutzer, String passwort) throws SQLException{
		return DriverManager.getConnection(url, benutzer, passwort);
	}
	
	
	@Override
	public ResultSet fuehreAus(Map<String, String> queryTable) {
		String queryString= erstelleQuery(queryTable);
		Statement statement;

		try {
			statement = verbindung.createStatement();
			ResultSet resultSet = statement.executeQuery(queryString);
			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	
	private String erstelleQuery(Map<String, String> queryTable) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT ");
		builder.append(queryTable.get("Spalten") + " ");
		builder.append("FROM ");
		builder.append(queryTable.get("Tabellen") + " ");
		if (queryTable.containsKey("Join")) {
			builder.append("JOIN ");
			builder.append(queryTable.get("Join") + " ");
			builder.append("ON ");
			builder.append(queryTable.get("JoinArgument") + " ");
		}
		builder.append(';');
		
		return builder.toString();
	}

}
