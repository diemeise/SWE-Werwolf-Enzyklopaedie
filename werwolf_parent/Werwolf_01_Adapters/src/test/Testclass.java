
package test;
import java.sql.*;

public class Testclass {
	
	public static void main(String[] args) {
		//Testen zum Verbinden der db - funktioniert aktuell nur lokal I guess
		
		String url = "jdbc:mysql://localhost:3306/werwolf";
		String user = "root";
		String password = "penispenis123";
		
		try (Connection con = DriverManager.getConnection(url, user, password)) {
			
			System.out.println("Verbindung erfolgreich,  uwu");
			System.out.println();
			
			//Datensaetze ausgeben
			
			String qry = "SELECT * FROM rolle";
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
