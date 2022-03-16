package werwolf.adapter.output;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import werwolf.adapter.game.GameController;
import werwolf.application.game.library.LibraryManager;
import werwolf.domain.game.content.Karte;


public class OutputAdapter {
	
	private LibraryManager gamelib;
	private GameController gameCon;
	private HashMap<String, Karte> karten = new HashMap<>();
	
	String name;
	String funk;
	String besch; 
	String gesinnung = "gut";
	String spezial = "nein";
	

	public OutputAdapter(LibraryManager gamelib) {
		super();
		this.gamelib = gamelib;
		this.gameCon = new GameController(gamelib);
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
	
	//TODO Das hier und Spezial und gut macht ja quasi das gleiche? Irgendwie in eine Methode zusammen fassen?
	public HashMap<String, String> getAlleBoesenKarten(){
		
		HashMap<String, String> boese = new HashMap<>();
		
		karten = gamelib.getKartenRepository().getKarten(); //TODO auﬂerhalb der Methoden initialisieren?
		
		for (String key: karten.keySet()) {
			
			if(karten.get(key).getRolle().istBoese()) {
				name = karten.get(key).getRolle().getName();
				funk = karten.get(key).getRolle().getFunktion();
				
				boese.put(name, funk);
			}
			
		}
		
		return boese;
	}
	
	public HashMap<String, String> getAlleGutenKarten(){
		
		HashMap<String, String> gut = new HashMap<>();
		
		karten = gamelib.getKartenRepository().getKarten(); //TODO auﬂerhalb der Methoden initialisieren?
		
		for (String key: karten.keySet()) {
			
			if(!karten.get(key).getRolle().istBoese()) {
				name = karten.get(key).getRolle().getName();
				funk = karten.get(key).getRolle().getFunktion();
				
				gut.put(name, funk);
			}
			
		}
		
		return gut;
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

	public String starteSpiel(List<String> spielerNamen, List<String> rollenNamen) {
		return gameCon.starteSpiel(spielerNamen, rollenNamen);
	}
	public List<Map<String,String>> listeAlleSpieler(){
		
		return gameCon.listeAlleSpieler();
	}
	
	public Map<String,String> getDetailsOfSpieler(String spielerName){
		return gameCon.getSpielerDetails(spielerName);
	}
	
	public Map<String,String> getAktivenSpieler(){
		return gameCon.getAktiverSpielerDetails();
	}
	
}
