package werwolf.domain.game.content;

public class Spieler {
	
	private Rolle rolle;
	private boolean lebendig;
	private boolean aktiv;
	
	public Spieler(Rolle rolle) {
		this.rolle = rolle;
		this.lebendig = true;
		this.aktiv = false;
	}
	
	public boolean istBoese() {
		return this.rolle.istBoese();
	}
	
	public String getRollenName() {
		return this.rolle.getName();
	}
	
}
