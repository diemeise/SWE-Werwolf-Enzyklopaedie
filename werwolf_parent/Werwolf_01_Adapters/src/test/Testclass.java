
package test;
import java.sql.*;

public class Testclass {
	
	public static void main(String[] args) {
		
		//IP HIER EINGEBEN
		String ip = "";
				
		String url = "jdbc:mysql://" + ip + "/s1440177";
		
		// BENUTZERKENNUNG HIER EINGEBEN
		String user = "";
		String password = "";
		
		try (Connection con = DriverManager.getConnection(url, user, password)) {
			
			System.out.println("Verbindung erfolgreich,  uwu");
			System.out.println();
			
			//Datensaetze ausgeben
			
			String qry = "SELECT * FROM Rolle";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			
			//Spaltennamen mit ausgeben
			int col = rs.getMetaData().getColumnCount();
			for( int i = 1; i<= col; i++) {
				System.out.print(String.format("%-15s", rs.getMetaData().getColumnLabel(i)));
			}
			
			System.out.println();
			System.out.println("-----------------------------------------------------------------------");
				
			while(rs.next()) {
				for( int j = 1; j<= col; j++) {
					System.out.print(String.format("%-15s", rs.getString(j)));
				}
			}
				
			
			rs.close();
			stmt.close();
			
		} catch(SQLException e) {
			
			System.err.println(e.getMessage());
		
		}
	}
	

}
