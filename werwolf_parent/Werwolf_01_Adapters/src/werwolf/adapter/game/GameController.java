package werwolf.adapter.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import werwolf.application.game.GameLoop;
import werwolf.application.game.Spielphase;
import werwolf.application.game.library.LibraryManager;
import werwolf.domain.game.content.Rolle;
import werwolf.domain.game.content.Spieler;
import werwolf.domain.game.exceptions.GameException;

/**
 * adapter f�r die Kontrolle �ber ein Spiel.
 * Gibt in jeder Methode einen String zur�ck, der dem User angezeigt werden kann
 * @author meise
 *
 */
public class GameController {
	
	private GameLoop game;
	private boolean gameCreated;
	
	
	public GameController() {
		this.gameCreated = false;
	}
	

	private String starteSpiel(List<Spieler>spieler) {
		try {
			game = new GameLoop(spieler); 
			gameCreated = true;
			game.starteErstePhase();
		}catch (GameException e) {
			return e.getMessage();
		}
			return "Neues Spiel gestartet.";
		}


	
	/**
	 * Erstellt ein Spiel mit einer zufaelligen Kombination aus Spielern und Rollen.
	 * F�r alle nicht erkannten oder zu wenig vorhandenen Rollen wird ein Dorfbewohner erzeugt.
	 * Alle vorhandenen Rollen werden aus dem LibraryManager entnommen.
	 * @param spielerNamen 	Liste aller Spielernamen
	 * @param rollenNamen	Liste an Rollennamen
	 * @return
	 */
	public String starteSpiel(List<String> spielerNamen, List<String> rollenNamen) {	
		List<Spieler> spieler = erzeugeSpieler(spielerNamen, rollenNamen, "Dorfbewohner");
		return starteSpiel(spieler);
		
	}
	
	public String beendeSpiel() {
		if(gameCreated) {
			game.beende();
			return "Spiel beendet.";
		}
		return "Spiel noch nicht gestartet!";
	}
	
	/**
	 * fuehrt ein Spielschritt aus
	 * Dies kann sein:
	 * 		Nachster Schritt in der aktuellen Phase;
	 * 		Start der neachsten Phase;
	 * @return
	 */
	public String naechsterSchritt() {
		try{
			//ueberpfuefe zuerst ob ein neuer Schritt in dieser Phase m�glich ist
			if(game.naechsterSchritt()) {
				return "Phase wird fortgefuehrt";
			}
			//wenn false, dann neue Phase erstellen
			if(game.naechstePhase()) {
				return "Die Phase ist vorbei. Eine neue Phase beginnt.";
			}
			//wenn beides false, dann ist das Spiel vorbei.
			return "Das Spiel ist vorbei";
		}catch (GameException e) {
			return e.getMessage() + System.lineSeparator();
			
			
		}
		
	}
	
	public String eliminiereSpieler(String spielerName) {
		Optional<Spieler> so = game.getSpielerBy(spielerName);
		if(so.isEmpty()) {
			return "Spieler " + spielerName + "nicht gefunden";
		}
		if (game.eliminiereSpieler(so.get())) {
			return "Spieler " + spielerName + " eliminiert.";
		}
		return "Spieler konnte nicht eliminiert werden.";
		
	}
	
	public String setBuergermeister(String spielerName) {
		Optional<Spieler> so = game.getSpielerBy(spielerName);
		if(so.isEmpty()) {
			return "Spieler " + spielerName + "nicht gefunden";
		}
		if (game.setBuergermeister(so.get())) {
			return game.getStatus();
		}
		return "Aktion konnte nicht durchgeführt werden";
		
	}

	public Map<String, String> getAktiverSpielerDetails(){
		Spieler s = game.getAktiverSpieler();
		return getDetailsOfSpieler(s);
	}
	
	public Map<String,String> getSpielerDetails(String spielerName){
		Optional<Spieler> so = game.getSpielerBy(spielerName);
		Map<String,String> returnMap = new HashMap<>();
		if (so.isEmpty()) {
			returnMap.put("Error", "Spielername " + spielerName + " nicht gefunden.");
			return returnMap;
		}
		Spieler s = so.get();
		
		return getDetailsOfSpieler(s);
	}
	
	public List<Map<String, String>> listeAlleSpieler() {
		List< Map<String,String>> returnList = new ArrayList<>();
		List<Spieler> alleSpieler = game.getSpieler();
		for (Spieler spieler : alleSpieler) {
			returnList.add(getDetailsOfSpieler(spieler));
		}
		return returnList;
	}
	
	public List<Map<String,String>> listGewinner(){
		List< Map<String,String>> returnList = new ArrayList<>();
		List<Spieler> gewinner = game.getGewinner();
		if(gewinner == null) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("Error", "Spiel ist noch nicht vorbei");
			returnList.add(map);
		}
		for (Spieler spieler : gewinner) {
			returnList.add(getDetailsOfSpieler(spieler));
		}
		return returnList;
	}
	

	public Map<String,String> listSpielphase() {
		Spielphase n = game.getAktuellePhase();
		Map<String,String> returnMap = new HashMap<>();
		returnMap.put("Aktiver_Spieler", n.getAktiverSpieler().getName());
		returnMap.put("Anzahl", String.valueOf(game.getPhasen().size()));
		returnMap.put("Abgeschlossen", String.valueOf(n.istAbgeschlossen()));
		
		return returnMap;
	}
	
	
	
	
	//eigentlich privat, nur wegen einem Test public
 	public List<Spieler> erzeugeSpieler(List<String> spielerNamen, List<String> rollenNamen, String standardRollenString) {
		List<Rolle> rollen =  new ArrayList<Rolle>();
		List<Spieler> spieler = new LinkedList<Spieler>();
		//ueberpruefe die uebergebenen Rollennamen und erzeuge die dazugehoerigen Rollen
		for (String rname : rollenNamen) {
			Optional<Rolle>passendeRolle = LibraryManager.INSTANCE.getRollenRepository().findeDurch(rname);
			passendeRolle.ifPresent( rolle -> rollen.add(rolle));
		}
		
		//fuelle  mit Standardrolle (->Dorfbewohner) auf;
		Optional<Rolle> standardRolle = LibraryManager.INSTANCE.getRollenRepository().findeDurch(standardRollenString);
		Rolle standard = standardRolle.get();
		while (rollen.size() < spielerNamen.size()) {
			rollen.add(standard);
		}
		
		Collections.shuffle(spielerNamen);
		Collections.shuffle(rollen);
		
		for(int i = 0; i < spielerNamen.size(); i++) {
			spieler.add(new Spieler(rollen.get(i), spielerNamen.get(i)));
		}
		
		return spieler;
	}


	private Map<String,String> getDetailsOfSpieler(Spieler s) {
		Map<String,String> returnMap = new HashMap<>();
		String status = "lebendig";
		if (!s.istLebendig()) status = "verstorben";
		returnMap.put("Spielername", s.getName());
		
		returnMap.put("Status", status);
		returnMap.put("Rollenname", s.getRollenName());
		returnMap.put("Rollenfunktion", s.getRolle().getFunktion());
		returnMap.put("Rollenbeschreibung", s.getRolle().getBeschreibung());
		
		return returnMap;
	}

	public String getSpielStatus() {
		return game.getStatus();
	}

	public Spieler getBuergermeister() {
		return game.getBuergermeister();
	}
}
