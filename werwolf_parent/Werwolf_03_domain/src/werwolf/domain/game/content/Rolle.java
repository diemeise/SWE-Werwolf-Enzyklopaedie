package werwolf.domain.game.content;

public class Rolle {

	private final String name;
	private final String funktion;
	private final String beschreibung;
	private final boolean istSpezial;
	private boolean istBöse;
	
	
	public Rolle() {
		this.name = "";
		this.funktion = "";
		this.beschreibung = "";
		this.istSpezial = false;
		this.istBöse = false;
	}
	

	
	public Rolle(String name, String funktion, String beschreibung, boolean istSpezial, boolean istBöse) {
		super();
		this.name = name;
		this.funktion = funktion;
		this.beschreibung = beschreibung;
		this.istSpezial = istSpezial;
		this.istBöse = istBöse;
	}



	public void setAlignment(boolean alignment) {
		this.istBöse = alignment;
	}
	
	
	
	/*
	 * just getters here
	 */
	public String getName() {
		return name;
	}


	public String getFunktion() {
		return funktion;
	}


	public String getBeschreibung() {
		return beschreibung;
	}


	public boolean istSpezial() {
		return istSpezial;
	}


	public boolean istBöse() {
		return istBöse;
	}
	
	
	
}
