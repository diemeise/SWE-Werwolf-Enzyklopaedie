package werwolf.adapter.sql;

import java.util.HashMap;
import java.util.Map;

import werwolf.application.game.library.LibraryManager;
import werwolf.domain.game.content.Karte;

public class OutputAdapter {
	
	private LibraryManager gamelib;

	public OutputAdapter(LibraryManager gamelib) {
		super();
		this.gamelib = gamelib;
	}
	
	public HashMap<String,String> getAlleKartenByFunktion(){
		return gamelib.getKartenRepository().zeigeNameUndFunktion();
	}
	

}
