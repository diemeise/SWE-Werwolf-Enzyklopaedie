package werwolf.application.game;

import java.util.LinkedList;
import java.util.List;

import werwolf.domain.game.content.Spieler;
import werwolf.domain.game.exceptions.GameException;

public class Tag extends Spielphase{
	
	boolean temp;
	public Tag(List<Spieler> lebendeSpieler, GameLoop gm) {
		super(lebendeSpieler, gm);
		temp = false;
		setStatus("Ein neuer Tag hat begonnen");
	}
	
	
	public Tag(List<Spieler> lebendeSpieler, GameLoop gm, Spieler buergermeister) {
		super(lebendeSpieler, gm);
		this.buergermeister = buergermeister;
		temp = false;
		setStatus("Ein neuer Tag hat begonnen");
	}
	
	
	/*
	 *  waehlt bei Bedarf am Anfang des Tages einen neuen Buergermeister
	 *  danach wird dieser auf Aktiv gesetzt, da er die Diskussion leitet
	 * @return false, wenn Phase abgeschlossen, sonst true
	 */
	public boolean naechsterSpielschritt(){
		if(!phaseAngefangen && (buergermeister == null || !buergermeister.istLebendig())) {
			setStatus("Ein neuer Buergermeister muss gewaehlt werden!");
			return true;
		}
		if(!phaseAngefangen) {
			//setzt den Buergermeister aktiv
			setErstenSpieler();
			setStatus("Das Dorf waehlt einen Schuldigen, der an diesem Tage gehaengt wird.");
			phaseAngefangen = true;		
			return true;
		}
		
		if(!phaseAbgeschlossen) {
			phaseAbgeschlossen = true;
			return true;
		}
		
		return false;
	}

	public boolean setBuergermeister(Spieler bg) 
	{
		if(bg.istLebendig() && lebendeSpielerBeiStart.contains(bg))
		{
			this.buergermeister = bg;
			setStatus("Spieler*in " + bg.getName() + " ist neue*r Buergermeister*in");
			return true;
		}
		setStatus("Spieler*in " + bg.getName() + " konnte nicht zum*zur Buergermeister*in ernannt werden");
		return false;
	}
	

	
	/**
	 * setzt den Bürgermeister auf aktiv, da es sonst keine weiteren aktiven Spieler gibt am Tag
	 * @return true wenn es einen Spieler gibt, sonst false
	 * @throws GameException 
	 */
	public boolean setErstenSpieler(){
		aktiverSpieler = buergermeister;
		return true;
		
	}
		
}
