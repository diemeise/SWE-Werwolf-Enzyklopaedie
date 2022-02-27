package werwolf.domain.game.content;

public class Rolle {

	private final String name;
	private final String funktion;
	private final String beschreibung;
	private final boolean istSpezial;
	private boolean istBoese;
	
	
	public Rolle() {
		this.name = "";
		this.funktion = "";
		this.beschreibung = "";
		this.istSpezial = false;
		this.istBoese = false;
	}
	

	
	public Rolle(String name, String funktion, String beschreibung, boolean istSpezial, boolean istBoese) {
		super();
		this.name = name;
		this.funktion = funktion;
		this.beschreibung = beschreibung;
		this.istSpezial = istSpezial;
		this.istBoese = istBoese;
	}



	public void setAlignment(boolean alignment) {
		this.istBoese = alignment;
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


	public boolean istBoese() {
		return istBoese;
	}
	
	
	
}
