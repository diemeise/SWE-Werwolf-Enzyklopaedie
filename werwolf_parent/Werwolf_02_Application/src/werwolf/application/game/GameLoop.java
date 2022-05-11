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
	private List<Spielphase> phasen;
	private Spieler buergermeister;
	private String status;
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
		this.phasen = new LinkedList<Spielphase>();
		this.aktiv = false;
		this.gespielt = false;
	}
	
	
	

/**
 * startet den GameLoop und führt den ersten Schritt in der ersten Nacht aus
 * @throws GameException wenn Spiel bereits gespielt
 * @return Statusmessage 
 */
	public String starteErstePhase() throws GameException {	

		if (istGespielt()) {
			return "Spiel wurde schon abgeschlossen!";
		}
		
		//startet die erste Phase mit allen Spielern
		this.phasen.add(new Tag(spieler, this));
		this.aktiv = true;
		//this.naechsterSchritt();
		return getStatus();
		
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
			if(getAktuellePhase().getClass() == Tag.class)
			{
				this.buergermeister = getAktuellePhase().getBuergermeister();
				phasen.add(new Nacht(getAktuellePhase().getUeberlebendeSpieler(), this));
				return true;
			}
			//
			phasen.add(new Tag(getAktuellePhase().getUeberlebendeSpieler(), this, buergermeister));
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

		
	}
	
	public boolean eliminiereSpieler(Spieler spieler) {
		spieler.setLebendig(false);
		return getAktuellePhase().eliminiere(spieler);
	}
	
	public boolean setBuergermeister(Spieler bg) {
		return getAktuellePhase().setBuergermeister(bg);
	}
	public Spieler getBuergermeister() {
		return getAktuellePhase().getBuergermeister();
	}
	
	
	
	public Spieler getAktiverSpieler() {
		return getAktuellePhase().getAktiverSpieler();
	}
	
	public Spielphase getAktuellePhase() {
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


	public List<Spielphase> getPhasen() {
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	/**
	 * prueft ob nach einer Spielphase das Spiel von einer Partei gewonnen wurde
	 * @return
	 */
	public boolean pruefeSpielGewonnen() {
		List<Spieler> lebendeSpieler;
		lebendeSpieler = this.getAktuellePhase().getUeberlebendeSpieler();
		
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
