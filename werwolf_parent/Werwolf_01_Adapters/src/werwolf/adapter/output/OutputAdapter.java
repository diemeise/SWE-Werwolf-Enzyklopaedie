package werwolf.adapter.output;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import werwolf.adapter.game.GameController;
import werwolf.application.game.library.LibraryManager;
import werwolf.domain.game.content.Karte;


public class OutputAdapter {
	
	private LibraryManager gamelib;
	private GameController gameCon;
	private Map<String, Karte> karten = new HashMap<>();
	
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
	
	public Map<String,String> getAlleKartenByFunktion(){
		Map<String,Karte> alleKarten = gamelib.getKartenRepository().getKarten();
		
		return konvertKartenMapZuStringMap(alleKarten);
	}	
	
	public Map<String, String> getAlleSpezialKarten(){
		return filterKarten("spezial");
	}
	
	public Map<String, String> getAlleBoesenKarten(){
		return filterKarten("boese");
	}
	
	public Map<String, String> getAlleGutenKarten(){
		return filterKarten("gut");
	}

	
	private Map<String, String> konvertKartenMapZuStringMap(Map<String, Karte> karten) {
		Map<String, String> returnMap = new HashMap<>();
		for (String key: karten.keySet()) {
			name = karten.get(key).getRolle().getName();
			funk = karten.get(key).getRolle().getFunktion();
			returnMap.put(name, funk);
		}
		return returnMap;
	}
	
	public Map<String,String> filterKarten(String filter){
		Map<String, Karte> filteredKarten = gamelib.getKartenRepository().getKartenMitFilter(filter);
				
		return konvertKartenMapZuStringMap(filteredKarten);
		
	}
	
public HashMap<String, String> getKartenDetails(String k){
		Karte karte;
		karten = gamelib.getKartenRepository().getKarten(); //TODO au�erhalb der Methoden initialisieren?
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

//###################Spiel-Funktionen###################

	public String starteSpiel(List<String> spielerNamen, List<String> rollenNamen) {
		return gameCon.starteSpiel(spielerNamen, rollenNamen);
	}
	
	
	public String beendeSpiel() {
		return gameCon.beendeSpiel();
	}
	
	public String neachsterSpielSchritt() {
		return gameCon.naechsterSchritt();
	}
	
	public String eliminereSpieler(String spielerName) {
		return gameCon.eliminiereSpieler(spielerName);
	}
	
	public Map<String,String> getDetailsOfSpieler(String spielerName){
		return gameCon.getSpielerDetails(spielerName);
	}
	
	public Map<String,String> getAktiverSpieler(){
		return gameCon.getAktiverSpielerDetails();
	}
	
	public List<Map<String,String>> listeAlleSpieler(){
		
		return gameCon.listeAlleSpieler();
	}

	public List<Map<String,String>> listGewinner(){
		return gameCon.listGewinner();
	}
	
	public Map<String,String> listSpielphase(){
		return gameCon.listSpielphase();
	}
}
