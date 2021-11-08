package werwolf.domain.game.content;

public class Role {

	private final String name;
	private final String function;
	private final String description;
	private final boolean isSpecial;
	private boolean isEvil;
	
	
	public Role() {
		this.name = "";
		this.function = "";
		this.description = "";
		this.isSpecial = false;
		this.isEvil = false;
	}

	
	public void setAlignment(boolean alignment) {
		this.isEvil = alignment;
	}
	
	
	
	/*
	 * just getters here
	 */
	public String getName() {
		return name;
	}


	public String getFunction() {
		return function;
	}


	public String getDescription() {
		return description;
	}


	public boolean isSpecial() {
		return isSpecial;
	}


	public boolean isEvil() {
		return isEvil;
	}
	
	
	
}
