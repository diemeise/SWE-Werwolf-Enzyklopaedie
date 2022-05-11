package werwolf.application.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
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
		Collections.sort(spieler);
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
		try {
			game.starteErstePhase();
		} catch (GameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
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
		try {
			game.starteErstePhase();
		} catch (GameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
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
		try {
			game.starteErstePhase();
			game.setBuergermeister(spieler2);
		} catch (GameException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			fail();
		}
		//eine phase muss "durchgespielt" werden damit die nächste aufgerufen werden kann 
		try {
			while (game.naechsterSchritt()) {
			}
			game.naechstePhase();
		} catch (GameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
		//assert
		Assertions.assertEquals(2, game.getPhasen().size());
		Assertions.assertTrue(game.istAktiv());
	}

	/**
	 * Ueberprueft ob tote Spieler in der naechsten Phase nicht mehr hinzugefuegt werden 
	 */
	@Test
	public void eliminiereSpielerTest() {
		//arrange
		GameLoop game = null;
		try {
			game = new GameLoop(spieler);
			
		} catch (GameException e) {
		fail("Spiel konnte nicht erstellt werden");
		}
		
		//act
		try {
			game.starteErstePhase();
			game.setBuergermeister(spieler1);
		} catch (GameException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			fail();
		}
		game.eliminiereSpieler(spieler4);
		game.eliminiereSpieler(spieler3);
		
		List<Spieler> expectedKilledSpieler = new ArrayList<>();
		expectedKilledSpieler.add(spieler4);
		expectedKilledSpieler.add(spieler3);
		List<Spieler> actualKilledSpieler = game.getAktuellePhase().getEliminierteSpieler();
		

		//eine phase muss "durchgespielt" werden damit die nächste aufgerufen werden kann 
		try{
			while (game.naechsterSchritt()) {
			}		
			game.naechstePhase();
		} catch (GameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		List<Spieler> expectedAlivePlayers = game.getAktuellePhase().getSpieler();
		
		//assert
		MatcherAssert.assertThat(actualKilledSpieler, is(expectedKilledSpieler));
		for (Spieler killedSpieler : expectedKilledSpieler) {
			Assertions.assertFalse(expectedAlivePlayers.contains(killedSpieler));
		}
		
	}
	 
	 
	 
	 @Test
	 public void startePhaseWennAltePhaseNochLaufendTest() {
		//arrange
			GameLoop game = null;
			try {
				game = new GameLoop(spieler);
				
			} catch (GameException e) {
			fail("Spiel konnte nicht erstellt werden");
			}
			
			//act
			try {
				game.starteErstePhase();
			} catch (GameException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				fail();
			}
			try {
				game.naechstePhase();
			} catch (GameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//assert dass nur eine Phase vorhanden ist
			int expected = 1;
			MatcherAssert.assertThat(game.getPhasen().size(), is(expected));
	 }
}
