package werwolf.adapter.sql;

import java.sql.ResultSet;
import java.util.Map;

public interface SQLVerbindung {

	/**
	 * 
	 * @param queryTable Eine Map mit folgenden Möglichen werten:
	 * Key: Tabellen, Value: Alle Tabellen der Abfrage, mit Komma getrennt
	 * Key: Spalten, Value: Alle Spalten der Abfrage, mit Komma getrennt
	 * Key: Join, Value: Tabelle an der ein Join durchgeführt werden soll
	 * Key: JoinArgument, Value: Das verwendete Join-Argument
	 * Key: Bedingung, Value: Der "where" Block
	 * 
	 * @return
	 */
	public ResultSet fuehreAus(Map<String,String> queryTable);
}
