package werwolf.application.game;

import java.util.LinkedList;
import java.util.List;

import werwolf.domain.game.content.Spieler;
import werwolf.domain.game.exceptions.GameException;

public class Nacht {
	
	//nach Prio sortierte Liste aller aktivern Spieler bei Start
	private List<Spieler> lebendeSpielerBeiStart;
	private List<Spieler> eliminierteSpieler;
	private Spieler aktiverSpieler;
	private boolean phaseAngefangen;
	private boolean phaseAbgeschlossen;
	
	public Nacht(List<Spieler> lebendeSpieler) {
		this.lebendeSpielerBeiStart = lebendeSpieler;
		this.eliminierteSpieler = new LinkedList<>();
		this.phaseAbgeschlossen = false;
		this.phaseAngefangen = false;
		setzeAlleSpielerInaktiv();
	}
	
	/**
	 * setzt den naechsten Spieler auf aktiv
	 * @return false, wenn Phase abgeschlossen, sonst true
	 */
	public boolean naechsterSpielschritt(){
	
//		if(phaseAbgeschlossen) {
//			throw new GameException("Phase ist bereits abgeschlossen!");
//		}
		if (phaseAbgeschlossen) {
			return false;
		}
		
		//setze ersten Spieler auf aktiv
		if (!phaseAngefangen) {
			phaseAngefangen = true;
			//aktiverSpieler = findeErstenSpieler();
			
			if(setErstenSpieler()) {
				aktiverSpieler.setAktiv(true);
				return true;
			}			
			phaseAbgeschlossen = true;
			return false;			
		}
		
		//setze naechsten Spieler aktiv
		aktiverSpieler.setAktiv(false);		
		
		
		if(!setNaechstenSpieler()) {
			phaseAbgeschlossen = true;
			return false;
		}
		
//		try {
//			aktiverSpieler = findeNaechstenSpieler();
//		}catch (GameException e) {
//			phaseAbgeschlossen = true;
//		}
		//schauen ob das so gut ist 
		aktiverSpieler.setAktiv(true);
		return true;
	}

	
	public boolean eliminiere(Spieler spieler) {
		if (lebendeSpielerBeiStart.contains(spieler) && !eliminierteSpieler.contains(spieler)) {
			eliminierteSpieler.add(spieler);
			return true;
		}
		return false;
	}
		

	public Spieler getAktiverSpieler() {
		return aktiverSpieler;
	}
	
	public List<Spieler> getSpieler(){
		return lebendeSpielerBeiStart;
	}
	
	/**
	 * funktioniert nur wenn die Spielphase als abgeschlossen markiert ist
	 * @return
	 * @throws GameException
	 */
	public List<Spieler> getUeberlebendeSpieler() throws GameException{

		List <Spieler> ueberlebendeSpieler = new LinkedList<>();
		for (Spieler spieler : lebendeSpielerBeiStart) {
			if (!eliminierteSpieler.contains(spieler)) {
				ueberlebendeSpieler.add(spieler);
			}
		}
		return ueberlebendeSpieler;
	}
	
	public List<Spieler> getEliminierteSpieler(){
		return this.eliminierteSpieler;
	}
	
	public boolean istAbgeschlossen() {
		return phaseAbgeschlossen;
	}
	
	private void setzeAlleSpielerInaktiv() {
		for (Spieler spieler : lebendeSpielerBeiStart) {
			spieler.setAktiv(false);
		}
	}
	
//	/**
//	 * findet den ersten Spieler, dessen Rolle eine positive Prio hat (Spielerliste ist nach Prio sortiert)
//	 * ==> es wurden noch keine Spieler eliminiert daher keine weiteren Ueberpruefungen notwendig
//	 * @return
//	 * @throws GameException 
//	 */
//	private Spieler findeErstenSpieler() throws GameException {
//		for (Spieler spieler : lebendeSpielerBeiStart) {
//			if(spieler.getPrio() > 0) {
//				return spieler;
//			}
//		}
//		//TODO null ist kacke
//		throw new GameException("Keine aktiven Spieler diese Nacht!");
//	}
	
	/**
	 * findet den ersten Spieler, dessen Rolle eine positive Prio hat (Spielerliste ist nach Prio sortiert)
	 * ==> es wurden noch keine Spieler eliminiert daher keine weiteren Ueberpruefungen notwendig
	 * @return true wenn es einen Spieler gibt, sonst false
	 * @throws GameException 
	 */
	private boolean setErstenSpieler(){
		for (Spieler spieler : lebendeSpielerBeiStart) {
			if(spieler.getPrio() > 0) {
				aktiverSpieler = spieler;
				return true;
			}
		}
		return false;
		//TODO null ist kacke
//		throw new GameException("Keine aktiven Spieler diese Nacht!");
	}
	
	
//	private Spieler findeNaechstenSpieler() throws GameException {
//		int indexAktiverSpieler = lebendeSpielerBeiStart.indexOf(aktiverSpieler);
//		
//		//finde den nächsten nicht eliminerten Spieler
//		while(indexAktiverSpieler < lebendeSpielerBeiStart.size()-1) {
//			indexAktiverSpieler++;
//			Spieler naechsterSpieler = lebendeSpielerBeiStart.get(indexAktiverSpieler);
//			if (!eliminierteSpieler.contains(naechsterSpieler)) {
//				return naechsterSpieler;
//			}			
//		}
//		//keine weiteren Spieler vorhanden
//		throw new GameException("Keine weiteren Spieler diese Nacht!"); 
//	}
	
	/**
	 * findet den naechsten aktiven Spieler, und setzt diesen auf aktiv, Falls es einen gibt
	 * @return true wenn naechsten Spieler gefunden, sonst false
	 */
	private boolean setNaechstenSpieler() {
		int indexAktiverSpieler = lebendeSpielerBeiStart.indexOf(aktiverSpieler);
		
		//finde den nächsten nicht eliminerten Spieler
		while(indexAktiverSpieler < lebendeSpielerBeiStart.size()-1) {
			indexAktiverSpieler++;
			Spieler naechsterSpieler = lebendeSpielerBeiStart.get(indexAktiverSpieler);
			if (!eliminierteSpieler.contains(naechsterSpieler)) {
				aktiverSpieler = naechsterSpieler;
				return true;
			}			
		}
		//keine weiteren Spieler vorhanden
		return false;
		//throw new GameException("Keine weiteren Spieler diese Nacht!"); 
	}

}
