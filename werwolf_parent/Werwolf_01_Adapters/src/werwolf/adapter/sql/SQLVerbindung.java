package werwolf.adapter.sql;

import java.sql.ResultSet;
import java.util.Map;

public interface SQLVerbindung {

	public ResultSet fuehreAus(Map<String,String> queryTable);
}
