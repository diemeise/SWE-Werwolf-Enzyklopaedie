package werwolf.application.game;

import java.util.LinkedList;
import java.util.List;

import werwolf.domain.game.content.Spieler;

public abstract class Spielphase {
	//protected damit unterklassen zugriff auf diese Variablen haben
	protected List<Spieler> lebendeSpielerBeiStart;
	protected List<Spieler> eliminierteSpieler;
	protected Spieler aktiverSpieler;
	protected Spieler buergermeister;
	protected boolean phaseAngefangen;
	protected boolean phaseAbgeschlossen;
	protected GameLoop gm;
	
	
	public Spielphase(List<Spieler> lebendeSpieler, GameLoop gm) {
		this.lebendeSpielerBeiStart = lebendeSpieler;
		this.eliminierteSpieler = new LinkedList<>();
		this.phaseAbgeschlossen = false;
		this.phaseAngefangen = false;
		setzeAlleSpielerInaktiv();
		this.gm = gm;
	}
	

	public abstract boolean naechsterSpielschritt();
	
	public boolean eliminiere(Spieler spieler) {
		if (lebendeSpielerBeiStart.contains(spieler) && !eliminierteSpieler.contains(spieler)) {
			eliminierteSpieler.add(spieler);
			setStatus("Spieler " + spieler.getName() + " eliminiert!");
			return true;
		}
		setStatus("Spieler " + spieler.getName() + " konnte nicht eliminiert werden!");
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
	
	protected void setStatus(String status) {
		this.gm.setStatus(status);
	}
	public String getStatus() {
		return gm.getStatus();
	}

	public abstract boolean setBuergermeister(Spieler bg);
	
	public abstract boolean setErstenSpieler();
	
	
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


	protected Spieler getBuergermeister() {
		return buergermeister;
	}
}
