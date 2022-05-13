package werwolf.domain.game.content;


public class Spieler  implements Comparable<Spieler> {
	
	private final Rolle rolle;
	private boolean lebendig;
	private boolean aktiv;
	private final String name;
	
	public Spieler(Rolle rolle, String name){ 
		this.rolle = rolle;
		this.setLebendig(true);
		this.aktiv = false;
		this.name = name;
	}
	
	public boolean istBoese() {
		return this.rolle.istBoese();
	}
	
	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
	}
	public String getRollenName() {
		return this.rolle.getName();
	}
	
	public Rolle getRolle() {
		return this.rolle;
	}
	public int getPrio() {
		return this.rolle.getPrioritaet();
	}
	
	public String getName() {
		return this.name;
	}

	public boolean istLebendig() {
		return lebendig;
	}

	public void setLebendig(boolean lebendig) {
		this.lebendig = lebendig;
	}


	@Override
	public int compareTo(Spieler o) {
		return this.getRolle().compareTo(o.getRolle());
	}

	@Override
	public String toString() {
		String r = "Spieler " + this.name + "("+ this.getRollenName() + "): " + this.getRolle().getFunktion();
		return r;
	}
	
	
}
