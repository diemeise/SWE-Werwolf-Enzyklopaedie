package werwolf.adapter.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.print.attribute.HashAttributeSet;

import werwolf.application.game.GameLoop;
import werwolf.application.game.library.LibraryManager;
import werwolf.domain.game.content.Rolle;
import werwolf.domain.game.content.Spieler;
import werwolf.domain.game.exceptions.GameException;

/**
 * adapter für die Kontrolle über ein Spiel.
 * Gibt in jeder Methode einen String zurück, der dem User angezeigt werden kann
 * @author meise
 *
 */
public class GameController {
	
	//private LibraryManager gameLib;
	private GameLoop game;
	private boolean gameCreated;
	private LibraryManager gameLib;
	
	
	public GameController(LibraryManager lib) {
		this.gameCreated = false;
		this.gameLib = lib;
	}
	
	/**
	 * startet ein neues Spiel mit den gegebenen Spielern
	 * @param spieler
	 * @return
	 */
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
	 * fuehrt ein Spielschritt aus
	 * Dies kann sein:
	 * 		Nachster Schritt in der aktuellen Phase;
	 * 		Start der neachsten Phase;
	 * @return
	 */
	
	/**
	 * Erstellt ein Spiel mit einer zufälligen Kombination aus Spielern und Rollen.
	 * Für alle nicht erkannten oder zu wenig vorhandenen Rollen wird ein Dorfbewohner erzeugt.
	 * Alle vorhandenen Rollen werden aus dem LibraryManager entnommen.
	 * TODO andere Standardspieler ermöglichen
	 * @param spielerNamen 	Liste aller Spielernamen
	 * @param rollenNamen	Liste an Rollennamen
	 * @return
	 */
	public String starteSpiel(List<String> spielerNamen, List<String> rollenNamen) {
		;
		
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
	
	public String naechsterSchritt() {
		try{
			//üeberpfuefe zuerst ob ein neuer Schritt in dieser Phase möglich ist
			if(game.naechsterSchritt()) {
				return "Nacht wird fortgefuehrt";
			}
			//wenn false, dann neue Nacht erstellen
			if(game.naechstePhase()) {
				return "Die Nacht ist vorbei. Eine neue Nacht beginnt.";
			}
			//wenn beides false, dann ist das Spiel vorbei.
			return "Das Spiel ist vorbei";
		}catch (GameException e) {
			return e.getMessage();
		}
		
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
	
	
	
	
	
	
	//eigentlich privat, nur wegen einem Test public
	public List<Spieler> erzeugeSpieler(List<String> spielerNamen, List<String> rollenNamen, String standardRollenString) {
		List<Rolle> rollen =  new ArrayList<Rolle>();
		List<Spieler> spieler = new LinkedList<Spieler>();
		//ueberpruefe die uebergebenen Rollennamen und erzeuge die dazugehoerigen Rollen
		for (String rname : rollenNamen) {
			Optional<Rolle>passendeRolle = gameLib.getRollenRepository().findeDurch(rname);
			passendeRolle.ifPresent( rolle -> rollen.add(rolle));
		}
		//fuelle  mit Dorfbewohner auf;
		//TODO nicht schoen :(((
		
		Optional<Rolle> standardRolle = gameLib.getRollenRepository().findeDurch(standardRollenString);
		Rolle standard = standardRolle.get();
		while (rollen.size() < spielerNamen.size()) {
			rollen.add(standard);
		}
		
		//mische die spielerNamen und Rollen
		Collections.shuffle(spielerNamen);
		Collections.shuffle(rollen);
		
		//erzeugung der Spieler
		for(int i = 0; i < spielerNamen.size(); i++) {
			spieler.add(new Spieler(rollen.get(i), spielerNamen.get(i)));
		}
		
		return spieler;
	}

	public List<Map<String, String>> listeAlleSpieler() {
		List< Map<String,String>> returnList = new ArrayList<>();
		List<Spieler> alleSpieler = game.getSpieler();
		for (Spieler spieler : alleSpieler) {
			returnList.add(getDetailsOfSpieler(spieler));
		}
		return returnList;
	}

	private Map<String,String> getDetailsOfSpieler(Spieler s) {
		Map<String,String> returnMap = new HashMap<>();
		returnMap.put("Spielername", s.getName());
		returnMap.put("Status", String.valueOf(s.istLebendig()));
		returnMap.put("Rollenname", s.getRollenName());
		returnMap.put("Rollenfunktion", s.getRolle().getFunktion());
		returnMap.put("Rollenbeschreibung", s.getRolle().getBeschreibung());
		return returnMap;
	}
}
