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
 * startet den GameLoop und führt den ersten Schritt in der ersten Nacht aus
 * @throws GameException wenn Spiel bereits gespielt
 * @return Statusmessage 
 */
	public String starteErstePhase() throws GameException {	
//		if (istGespielt()) {
//			throw new GameException("Spiel wurde schon gespielt!");
//		}
		if (istGespielt()) {
			return "Spiel wurde schon abgeschlossen!";
		}
		//startet die erste Phase mit allen Spielern
		this.phasen.add(new Nacht(spieler));
		this.aktiv = true;
		this.naechsterSchritt();
		
		return "Spiel gestartet";
		
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
	 * gibt false zur�ck wenn Phase nicht abgeschlossen oder nach der Phase ein Gewinner feststeht
	 * 
	 * @throws GameException wenn kein Spiel aktiv
	 */
	public boolean naechstePhase() throws GameException{
		if (pruefeSpielGewonnen()) {
			return false;
		}
		//zb: spiel starte ->spiel beende ->spielschritt
		if(!this.aktiv || this.gespielt) {
			throw new GameException("Spiel nicht aktiv!");
		}
		if(getAktuellePhase().istAbgeschlossen()) {
			phasen.add(new Nacht(getAktuellePhase().getUeberlebendeSpieler()));
			return true;
		}
		//naechste Phase kann nicht gestartet werden
		return false;
		
		
		//throw new GameException("Spiel kann nicht forgesetzt werden.");

	}
	
	
	/**
	 * fuehrt einen Schritt in der aktuellen Phase aus
	 * gibt false zurueck wenn die aktuelle Phase bereits abgeschlossen ist
	 * @throws GameException wenn kein Spiel aktiv
	 * @return false wenn Spielphase schon abgeschlossen, sonst true
	 */
	public boolean naechsterSchritt() throws GameException {
		if(!this.aktiv || this.gespielt) {
			throw new GameException("Spiel nicht aktiv!");
		}
	
		return getAktuellePhase().naechsterSpielschritt();
//		try {
//			getAktuellePhase().naechsterSpielschritt();
//			return true;
//		} catch (GameException e) {
//			System.err.print(e.getMessage());
//			return false;
//		}
		
	}
	
	public boolean eliminiereSpieler(Spieler spieler) {
		spieler.setLebendig(false);
		return getAktuellePhase().eliminiere(spieler);
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
	
	public List<Spieler> getGewinner(){
		return gewinner;
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
	
	/**
	 * 
	 * @param spieler2
	 * @throws GameException wenn keine Spieler oder keine bösen Spieler vorhanden sind
	 */
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
