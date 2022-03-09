package werwolf.application.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import werwolf.domain.game.content.Rolle;
import werwolf.domain.game.content.Spieler;
import werwolf.domain.game.exceptions.GameException;

public class GameLoop {
	
	private List<Spieler> spieler;
	private List<Nacht> phasen;
	private boolean aktiv;
	private boolean gespielt;

	/**
	 * 
	 * @param 
	 * @throws GameException wenn keine Spieler oder keine "bösen" Spieler vorhanden sind
	 */
	public GameLoop(List<Spieler> spieler) throws GameException{
 		this.ueberpruefeSpieler(spieler); 
 		Collections.sort(spieler);
 		this.spieler = spieler;
		this.phasen = new LinkedList<Nacht>();
		this.aktiv = false;
		this.gespielt = false;
	}
	
	



/**
 * startet den GameLoop und führt den ersten Schritt in der ersten Nacht aus
 */
	public void starteErstePhase() {	
		//startet die erste Phase mit allen Spielern
		this.phasen.add(new Nacht(spieler));
		this.aktiv = true;
		this.naechsterSchritt();
	}
	
	/**
	 * Ein SPiel kann immer beendet werden
	 */
	public void beende() {
		this.aktiv = false;
		this.gespielt = true;
	}
	
	/**
	 * startet eine neue Phase 
	 * nur möglich wenn die aktuelle Phase abgeschlossen ist
	 */
	public void naechstePhase() {
		try {
				phasen.add(new Nacht(getAktuellePhase().getUeberlebendeSpieler()));
		} catch (GameException e) {
			//Spielphase noch nicht abgeschlossen
				System.err.print(e.getMessage());
		}
	}
	
	/**
	 * führt einen Schritt in der aktuellen Phase aus
	 * gibt false zurück wenn die aktuelle Phase abgeschlossen ist
	 */
	public boolean naechsterSchritt() {
		try {
			getAktuellePhase().naechsterSpielschritt();
			return true;
		} catch (GameException e) {
			System.err.print(e.getMessage());
			return false;
		}
	}
	
	public void eliminiereSpieler(Spieler spieler) {
		getAktuellePhase().eliminiere(spieler);
	}
	
	
	public Spieler getAktiverSpieler() {
		return getAktuellePhase().getAktiverSpieler();
	}
	
	public Nacht getAktuellePhase() {
		return this.phasen.get(this.phasen.size()-1);
	}
	
	
	public List<Spieler> getSpieler(){
		return spieler;
	}


	public boolean istAktiv() {
		return aktiv;
	}


	public boolean istGespielt() {
		return gespielt;
	}


	public List<Nacht> getPhasen() {
		return phasen;
	}
	
	private void ueberpruefeSpieler(List<Spieler> spieler2) throws GameException {
		if(spieler2.isEmpty()) {
			throw new GameException("Keine Spieler uebergeben!");
		}
		boolean boeseRolleVorhanden = false;
		for (Spieler s : spieler2) {
			if (s.istBoese()) {
				boeseRolleVorhanden = true;
			}
		}
		if (!boeseRolleVorhanden) {
			throw new GameException("Es muss ein Wolf / eine boese Karte vorhanden sein!");
		}
	}
	


}
