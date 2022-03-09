package werwolf.application.tests;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import werwolf.application.game.GameLoop;
import werwolf.domain.game.content.Rolle;
import werwolf.domain.game.content.Spieler;
import werwolf.domain.game.exceptions.GameException;

public class GameLoopTest { 
	
	static Spieler spieler1 = new Spieler(new Rolle("Werwolf", "frisst", "ist boese", false, true, 1 ), "Adam");
	static Spieler spieler2 = new Spieler(new Rolle("Dorfbewohner", "lebt", "ordinaerer Buerger", false, false, 0), "Berta");
	static Spieler spieler3 = new Spieler(new Rolle("Dorfbewohner", "lebt", "ordinaerer Buerger", false, false, 0), "Claus");
	static Spieler spieler4 = new Spieler(new Rolle("Dorfbewohner", "lebt", "ordinaerer Buerger", false, false, 0), "Donald");
	static Spieler spieler5 = new Spieler(new Rolle("Dorfbewohner", "lebt", "ordinaerer Buerger", false, false, 0), "Emilia");
	static Spieler spieler6 = new Spieler(new Rolle("Hexe", "zaubert", "Magie", false, false, 2), "Fabian");
	static List<Spieler> spieler;
	
	@BeforeEach
	private void erstelleSpielerListe() {
		//Arrange
		spieler = new ArrayList<>();
		spieler.add(spieler1);
		spieler.add(spieler2);
		spieler.add(spieler3);
		spieler.add(spieler4);
		spieler.add(spieler5);
		spieler.add(spieler6);
	}
	//Spielerstellung
	@Test
	public void erstelleSpielTest() {
		//act
		GameLoop game = null;
		try {
			game = new GameLoop(spieler);			
		} catch (GameException e) {
			fail("Spiel konnte nicht erstellt werden");
		}
		//assert
		MatcherAssert.assertThat(game.getSpieler(), is(spieler));
		
	}
	
	@Test
	public void erstelleSpielOhneSpielerTest() {
		//arrange
		spieler = new ArrayList<>();
		//act
		Exception e = Assertions.assertThrows(GameException.class,() ->{ new GameLoop(spieler);});
		//assert
		MatcherAssert.assertThat(e.getMessage(), is("Keine Spieler uebergeben!"));
	}
	
	@Test
	public void erstelleSpielOhneWolfTest() {
		//arrange
		spieler = new ArrayList<>();
		spieler.add(spieler2);
		spieler.add(spieler3);
		spieler.add(spieler4);
		spieler.add(spieler5);
		spieler.add(spieler6);
		//act
		Exception e = Assertions.assertThrows(GameException.class,() ->{ new GameLoop(spieler);});
		//assert
		MatcherAssert.assertThat(e.getMessage(), is("Es muss ein Wolf / eine boese Karte vorhanden sein!"));
	}
	
	
	
	//Spieldurchfuehrung
	@Test
	public void starteSpielTest() {
		//arrange
		GameLoop game = null;
		try {
			game = new GameLoop(spieler);
			
		} catch (GameException e) {
		fail("Spiel konnte nicht erstellt werden");
		}
		//act
		game.starteErstePhase();
		//assert
		Assertions.assertTrue(game.istAktiv());
		Assertions.assertFalse(game.istGespielt());
		
	}
	
	@Test
	public void beendeSpielTest() {
		//arrange
		GameLoop game = null;
		try {
			game = new GameLoop(spieler);
			
		} catch (GameException e) {
		fail("Spiel konnte nicht erstellt werden");
		}
		//act
		game.starteErstePhase();
		game.beende();
		//assert
		Assertions.assertFalse(game.istAktiv());
		Assertions.assertTrue(game.istGespielt());
	}
			
	@Test
	public void neueSpielPhaseTest() {
		//arrange
		GameLoop game = null;
		try {
			game = new GameLoop(spieler);
			
		} catch (GameException e) {
		fail("Spiel konnte nicht erstellt werden");
		}
		
		//act
		game.starteErstePhase();
		//eine phase muss "durchgespielt" werden damit die nächste aufgerufen werden kann 
		while (game.neachsterSchritt()) {
		}
		game.naechstePhase();
		
		//assert
		Assertions.assertEquals(2, game.getPhasen().size());
		Assertions.assertTrue(game.istAktiv());
	}

}
