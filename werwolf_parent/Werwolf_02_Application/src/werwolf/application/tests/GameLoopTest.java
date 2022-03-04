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
	
	static Spieler spieler1 = new Spieler(new Rolle("Werwolf", "frisst", "ist böse", false, true ));
	static Spieler spieler2 = new Spieler(new Rolle("Dorfbewohner", "lebt", "ordinärer Bürger", false, false));
	static Spieler spieler3 = new Spieler(new Rolle("Dorfbewohner", "lebt", "ordinärer Bürger", false, false));
	static Spieler spieler4 = new Spieler(new Rolle("Dorfbewohner", "lebt", "ordinärer Bürger", false, false));
	static Spieler spieler5 = new Spieler(new Rolle("Dorfbewohner", "lebt", "ordinärer Bürger", false, false));
	static Spieler spieler6 = new Spieler(new Rolle("Hexe", "zaubert", "Magie", false, false));
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
		game.starte();
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
		game.starte();
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
		game.starte();
		game.naechstePhase();
		//assert
		Assertions.assertEquals(game.getPhasen().size(), 2);
	}

}
