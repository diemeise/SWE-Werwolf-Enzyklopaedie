package werwolf.adapter.sql;

import java.util.HashMap;
import java.util.Map;

import werwolf.application.game.library.LibraryManager;
import werwolf.domain.game.content.Karte;

public class OutputAdapter {
	
	private LibraryManager gamelib;
	private HashMap<String, Karte> karten = new HashMap<>();

	public OutputAdapter(LibraryManager gamelib) {
		super();
		this.gamelib = gamelib;
	}
	
	public HashMap<String,String> getAlleKartenByFunktion(){
		return gamelib.getKartenRepository().zeigeNameUndFunktion();
	}
	
	//TODO Das ist zu viel Code, da lieber ne Methode machen mit der dem put und die dann aufrufen mit irgendeinem Paramerter oder so 
	public HashMap<String, String> getAlleSpezialKarten(){
		String name;
		String funk;
		HashMap<String, String> spezial = new HashMap<>();
		
		karten = gamelib.getKartenRepository().getKarten();
		
		for (String key: karten.keySet()) {
			
			if(karten.get(key).getRolle().istSpezial()) {
				name = karten.get(key).getRolle().getName();
				funk = karten.get(key).getRolle().getFunktion();
				
				spezial.put(name, funk);
			}
			
		}
		
		return spezial;
	}
	

}
