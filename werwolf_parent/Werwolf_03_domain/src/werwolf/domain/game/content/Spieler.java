package werwolf.domain.game.content;

public class Spieler {
	
	private final Rolle rolle;
	private boolean lebendig;
	private boolean aktiv;
	private final String name;
	
	public Spieler(Rolle rolle, String name) {
		this.rolle = rolle;
		this.lebendig = true;
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
}
