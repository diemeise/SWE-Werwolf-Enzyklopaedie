package werwolf.application.game;

import java.util.LinkedList;
import java.util.List;

import werwolf.domain.game.content.Spieler;

public abstract class Spielphase {
	//protected damit unterklassen zugriff auf diese Variablen haben
	protected List<Spieler> lebendeSpielerBeiStart;
	protected List<Spieler> eliminierteSpieler;
	protected Spieler aktiverSpieler;
	protected boolean phaseAngefangen;
	protected boolean phaseAbgeschlossen;
	
	public Spielphase(List<Spieler> lebendeSpieler) {
		this.lebendeSpielerBeiStart = lebendeSpieler;
		this.eliminierteSpieler = new LinkedList<>();
		this.phaseAbgeschlossen = false;
		this.phaseAngefangen = false;
		setzeAlleSpielerInaktiv();
	}
	
	public abstract boolean naechsterSpielschritt();
	
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
	
	private boolean setErstenSpieler(){
		for (Spieler spieler : lebendeSpielerBeiStart) {
			if(spieler.getPrio() > 0) {
				aktiverSpieler = spieler;
				return true;
			}
		}
		return false;
	}
	
	protected boolean setNaechstenSpieler() {
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

	protected  List<Spieler> getUeberlebendeSpieler(){
		List <Spieler> ueberlebendeSpieler = new LinkedList<>();
		for (Spieler spieler : lebendeSpielerBeiStart) {
			if (!eliminierteSpieler.contains(spieler)) {
				ueberlebendeSpieler.add(spieler);
			}
		}
		return ueberlebendeSpieler;
	}
}
