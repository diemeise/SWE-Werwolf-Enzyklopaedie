package werwolf.adapter.sql;

import java.sql.ResultSet;
import java.util.Map;

public interface SQLVerbindung {

	public ResultSet führeAus(Map<String,String> queryTable);
}
