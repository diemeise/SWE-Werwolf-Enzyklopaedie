package werwolf.domain.game.content;

/**
 * 
 * @author Simon
 *
 */
public class Card {
	private final Role role;
	//private final Rule rule;
	private final String displayText;
	//private final Image image;
	
	
	/**
	 * @param role
	 * @param displayText
	 */
	public Card(Role role, String displayText) {
		this.role = role;
		this.displayText = displayText;
	}
	
	
	public Card(Role role) {
		this.role = role;
		this.displayText = role.getDescription();
	}


	
	
	public Role getRole() {
		return role;
	}


	public String getDisplayText() {
		return displayText;
	}

}
