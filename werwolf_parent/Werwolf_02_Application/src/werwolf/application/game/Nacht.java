package werwolf.application.game;

import java.util.LinkedList;
import java.util.List;

import werwolf.domain.game.content.Spieler;
import werwolf.domain.game.exceptions.GameException;

public class Nacht extends Spielphase{
	
	
	public Nacht(List<Spieler> lebendeSpieler) {
		super(lebendeSpieler);
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
	}
	
}
