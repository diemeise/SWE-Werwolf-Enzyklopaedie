package werwolf.application.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import werwolf.domain.game.content.Spieler;
import werwolf.domain.game.exceptions.GameException;

public class GameLoop {
	
	private List<Spieler> spieler;
	private List<Spieler> gewinner;
	private List<Nacht> phasen;
	private boolean aktiv;
	private boolean gespielt;
	

	/**
	 * 
	 * @param 
	 * @throws GameException wenn keine Spieler oder keine "boesen" Spieler vorhanden sind
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
 * startet den GameLoop und fÃ¼hrt den ersten Schritt in der ersten Nacht aus
 * @throws GameException wenn Spiel bereits gesppielt
 */
	public void starteErstePhase() throws GameException {	
		if (istGespielt()) {
			throw new GameException("Spiel wurde schon gespielt!");
		}
		//startet die erste Phase mit allen Spielern
		this.phasen.add(new Nacht(spieler));
		this.aktiv = true;
		this.naechsterSchritt();
		
	}
	
	/**
	 * Ein aktives Spiel kann immer beendet werden
	 */
	public void beende() {
		if(aktiv) {
			this.aktiv = false;
			this.gespielt = true;
		}
	}
	
	/**
	 * startet eine neue Phase 
	 * nur moeglich wenn die aktuelle Phase abgeschlossen ist
	 * gibt false zurück wenn Phase nicht abgeschlossen oder nach der Phase ein Gewinner feststeht
	 * 
	 * @throws GameException wenn kein Spiel aktiv
	 */
	public boolean naechstePhase() throws GameException{
		if (pruefeSpielGewonnen()) {
			return false;
		}
		if(!this.aktiv || this.gespielt) {
			throw new GameException("Spiel nicht aktiv!");
		}
		try {
			phasen.add(new Nacht(getAktuellePhase().getUeberlebendeSpieler()));
			return true;
		} catch (GameException e) {
			//Spielphase noch nicht abgeschlossen
			System.err.print(e.getMessage());
			return false;
		}
	}
	
	
	/**
	 * fuehrt einen Schritt in der aktuellen Phase aus
	 * gibt false zurueck wenn die aktuelle Phase bereits abgeschlossen ist
	 * @throws GameException wenn kein Spiel aktiv
	 */
	public boolean naechsterSchritt() throws GameException {
		if(!this.aktiv || this.gespielt) {
			throw new GameException("Spiel nicht aktiv!");
		}
		try {
			getAktuellePhase().naechsterSpielschritt();
			return true;
		} catch (GameException e) {
			System.err.print(e.getMessage());
			return false;
		}
		
	}
	
	public void eliminiereSpieler(Spieler spieler) {
		spieler.setLebendig(false);
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
	
	public Optional<Spieler> getSpielerBy(String spielerName) {
		Spieler s = null;
		for (Spieler spieler : spieler) {
			if (spieler.getName().equalsIgnoreCase(spielerName)) {
				s = spieler;
				break;
			}			
		}
		return Optional.ofNullable(s);
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
	
	/**
	 * prueft ob nach einer Spielphase das Spiel von einer Partei gewonnen wurde
	 * @return
	 */
	public boolean pruefeSpielGewonnen() {
		List<Spieler> lebendeSpieler;
		try {
			lebendeSpieler = this.getAktuellePhase().getUeberlebendeSpieler();
		} catch (GameException e) {
			//Spielphase nicht vorbei
			return false;
		}
		
		//wenn alle Spieler "boese" oder alle Spieler "nichtBoese" dann ist das Spiel vorbei
		boolean boeseUebrig = false;
		boolean gutUebrig = false;
		for (Spieler spieler : lebendeSpieler) {
			if (spieler.istBoese()) {
				boeseUebrig = true;
			}
			else{
				gutUebrig = true;
			}			
		}
		
		if (boeseUebrig ^ gutUebrig) {
			this.gewinner = lebendeSpieler;
			this.beende();
			return true;
		}
		//kein Gewinner 
		return false;
		
	}

}
