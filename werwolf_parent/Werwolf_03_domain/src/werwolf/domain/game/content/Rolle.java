package werwolf.domain.game.content;

public class Rolle {

	private final String name;
	private final String funktion;
	private final String beschreibung;
	private final int prioritaet;
	private final boolean istSpezial;
	private boolean istBoese;
	
	
	private Rolle() {
		this.name = "";
		this.funktion = "";
		this.beschreibung = "";
		this.istSpezial = false;
		this.istBoese = false;
		this.prioritaet = 1;
	}
	

	
	public Rolle(String name, String funktion, String beschreibung, boolean istSpezial, boolean istBoese, int prioritaet) {
		super();
		this.name = name;
		this.funktion = funktion;
		this.beschreibung = beschreibung;
		this.istSpezial = istSpezial;
		this.istBoese = istBoese;
		this.prioritaet = prioritaet;
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
	
	public int getPrioritaet() {
		return prioritaet;
	}
	
	
	
}
