package werwolf.application.tests;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import werwolf.application.game.GameLoop;
import werwolf.domain.game.content.Rolle;
import werwolf.domain.game.content.Spieler;
import werwolf.domain.game.exceptions.GameException;

public class GameLoopTest {
	
	Spieler spieler1 = new Spieler(new Rolle("Werwolf", "frisst", "ist böse", false, true ));
	Spieler spieler2 = new Spieler(new Rolle("Dorfbewohner", "lebt", "ordinärer Bürger", false, false));
	Spieler spieler3 = new Spieler(new Rolle("Dorfbewohner", "lebt", "ordinärer Bürger", false, false));
	Spieler spieler4 = new Spieler(new Rolle("Dorfbewohner", "lebt", "ordinärer Bürger", false, false));
	Spieler spieler5 = new Spieler(new Rolle("Dorfbewohner", "lebt", "ordinärer Bürger", false, false));
	Spieler spieler6 = new Spieler(new Rolle("Hexe", "zaubert", "Magie", false, false));
	
	@Test
	public void erstelleSpielTest() {
		List<Spieler> spieler = new ArrayList<>();
		spieler.add(spieler1);
		spieler.add(spieler2);
		spieler.add(spieler3);
		spieler.add(spieler4);
		spieler.add(spieler5);
		spieler.add(spieler6);
		GameLoop game =  new GameLoop(spieler);
		MatcherAssert.assertThat(game.getSpieler(), is(spieler));
		
	}
	
	@Test
	public void erstelleSpielOhneSpielerTest() {
		List<Spieler> spieler = new ArrayList<>();
		Exception e = Assertions.assertThrows(GameException.class,() ->{ new GameLoop(spieler);});
		
		MatcherAssert.assertThat(e.getMessage(), is("Keine Spieler uebergeben!"));
	}
	
	@Test
	public void erstelleSpielKeinWolfTest() {
		List<Spieler> spieler = new ArrayList<>();
		spieler.add(spieler2);
		spieler.add(spieler3);
		spieler.add(spieler4);
		spieler.add(spieler5);
		spieler.add(spieler6);
		Exception e = Assertions.assertThrows(GameException.class,() ->{ new GameLoop(spieler);});
		
		MatcherAssert.assertThat(e.getMessage(), is("Es muss ein Wolf / eine boese Karte vorhanden sein!"));
	
	}

}
