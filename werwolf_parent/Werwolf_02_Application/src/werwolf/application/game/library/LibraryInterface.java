package werwolf.application.game.library;

import java.util.List;

public interface LibraryInterface {

	public List<String[]> getAlleRollenAlsString();
	
	public String[] getRolleAlsString(String rollenName);
	
	public List<String[]> getKarteAlsString(String kartenName);
	
	public String[] getAlleKartenAlsString();
}
