package werwolf.domain.game.content;


public class Karte {
	private Rolle rolle;
	//private final Rule rule;
	private String displayText;
	//private final Image image;
	
	
	public Karte(Rolle rolle, String displayText) {
		this.rolle = rolle;
		this.displayText = displayText;
	}
	
	
	public Karte(Rolle role) {
		this.rolle = role;
		this.displayText = role.getName();
	}
	
	
	public Rolle getRolle() {
		return rolle;
	}
	
	public void setRolle(Rolle rolle) {
		this.rolle = rolle;
		this.displayText = rolle.getName();
	}
	public String getDisplayText() {
		return displayText;
	}

}
