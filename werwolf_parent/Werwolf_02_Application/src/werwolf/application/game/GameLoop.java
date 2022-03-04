package werwolf.application.game;

import java.util.LinkedList;
import java.util.List;

import werwolf.domain.game.content.Spieler;
import werwolf.domain.game.exceptions.GameException;

public class GameLoop {
	
	private List<Spieler> spieler;
	//TODO 
	private List<Object> phasen;
	private boolean aktiv;
	private boolean gespielt;

	public GameLoop(List<Spieler> spieler) throws GameException{
 		this.ueberpruefeSpieler(spieler);
		this.spieler = spieler;
		this.phasen = new LinkedList<Object>();
		this.aktiv = false;
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

	public void starte() {		
	}
	
	public void beende() {
		
	}
	
	public void fuehrePhaseAus() {
		
	}
	
	public void naechstePhase() {
		
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


	public List<Object> getPhasen() {
		return phasen;
	}
}
