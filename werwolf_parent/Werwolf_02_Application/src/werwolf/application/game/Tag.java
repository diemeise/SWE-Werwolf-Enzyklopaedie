package werwolf.application.game;

import java.util.LinkedList;
import java.util.List;

import werwolf.domain.game.content.Spieler;
import werwolf.domain.game.exceptions.GameException;

public class Tag extends Spielphase{
	
	//nach Prio sortierte Liste aller aktivern Spieler bei Start
	boolean temp;
	public Tag(List<Spieler> lebendeSpieler) {
		super(lebendeSpieler);
		temp = false;
	}
	
	
	/*
	 *  waehlt bei Bedarf einen neuen Buergermeister
	 *  danach wird dieser auf Aktiv gesetzt, da er die Diskussion leitet
	 * @return false, wenn Phase abgeschlossen, sonst true
	 */
	public boolean naechsterSpielschritt(){
		aktiverSpieler = lebendeSpielerBeiStart.get(0);
		//		if(buergermeister == null || !buergermeister.istLebendig()) {
//			return false;
//		}
		
		if(!temp) {
			temp = true;
			return true;
		}
		
		if(!phaseAbgeschlossen) {
			phaseAbgeschlossen = true;
			return true;
		}
		
		
		return false;
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
		
}
