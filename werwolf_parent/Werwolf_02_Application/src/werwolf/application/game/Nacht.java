package werwolf.application.game;

import java.util.LinkedList;
import java.util.List;

import werwolf.domain.game.content.Spieler;
import werwolf.domain.game.exceptions.GameException;

public class Nacht extends Spielphase{
	
	
	public Nacht(List<Spieler> lebendeSpieler, GameLoop gm) {
		super(lebendeSpieler, gm);
		setStatus("Eine neue Nacht hat begonnen");
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
		
		if (!phaseAngefangen) {
			phaseAngefangen = true;
			
			if(setErstenSpieler()) {
				aktiverSpieler.setAktiv(true);
				return true;
			}			
			phaseAbgeschlossen = true;
			return false;			
		}
		

		aktiverSpieler.setAktiv(false);						
		if(!setNaechstenSpieler()) {
			phaseAbgeschlossen = true;
			return false;
		}
		
		
		aktiverSpieler.setAktiv(true);
		return true;
	}

	
	/**
	 * findet den ersten Spieler, dessen Rolle eine positive Prio hat (Spielerliste ist nach Prio sortiert)
	 * ==> es wurden noch keine Spieler eliminiert daher keine weiteren Ueberpruefungen notwendig
	 * @return true wenn es einen Spieler gibt, sonst false
	 * @throws GameException 
	 */
	public boolean setErstenSpieler(){
		for (Spieler spieler : lebendeSpielerBeiStart) {
			if(spieler.getPrio() > 0) {
				aktiverSpieler = spieler;
				setStatus("Aktiver Spieler: " + spieler.getName());
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setBuergermeister(Spieler bg) {
		setStatus("Nachts kann kein neuer Buergermeister gewaehlt werden!");
		return false;
	}
	
}
