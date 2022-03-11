package werwolf.adapter.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import werwolf.application.game.library.LibraryManager;
import werwolf.domain.game.content.Karte;

public class OutputAdapter {
	
	private LibraryManager gamelib;
	private HashMap<String, Karte> karten = new HashMap<>();
	
	String name;
	String funk;
	String besch; 
	String gesinnung = "gut";
	String spezial = "nein";
	

	public OutputAdapter(LibraryManager gamelib) {
		super();
		this.gamelib = gamelib;
	}
	
	public HashMap<String,String> getAlleKartenByFunktion(){
		return gamelib.getKartenRepository().zeigeNameUndFunktion();
	}
	
	//TODO Das ist zu viel Code, da lieber ne Methode machen mit der dem put und die dann aufrufen mit irgendeinem Paramerter oder so [DRY]
	public HashMap<String, String> getAlleSpezialKarten(){
		
		HashMap<String, String> spezial = new HashMap<>();
		
		karten = gamelib.getKartenRepository().getKarten(); //TODO auﬂerhalb der Methoden initialisieren?
		
		for (String key: karten.keySet()) {
			
			if(karten.get(key).getRolle().istSpezial()) {
				name = karten.get(key).getRolle().getName();
				funk = karten.get(key).getRolle().getFunktion();
				
				spezial.put(name, funk);
			}
			
		}
		
		return spezial;
	}
	
	public HashMap<String, String> getKartenDetails(String k){
		Karte karte;
		karten = gamelib.getKartenRepository().getKarten(); //TODO auﬂerhalb der Methoden initialisieren?
		HashMap<String, String> kartenDetails = new HashMap<>();
		
		karte = karten.get(k);
		
		name = karte.getRolle().getName();
		funk = karte.getRolle().getFunktion();
		besch = karte.getRolle().getBeschreibung();
		
		if(karte.getRolle().istBoese()) {
			gesinnung = "boese";
		}
		
		if(karte.getRolle().istSpezial()) {
			spezial = "ja";
		}
		
		kartenDetails.put("Name", name);
		kartenDetails.put("Funktion", funk);
		kartenDetails.put("Beschreibung", besch);
		kartenDetails.put("Gesinnung", gesinnung);
		kartenDetails.put("Spezialrolle", spezial);
		
		return kartenDetails;
	}
	

}
