package werwolf.adapter.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import werwolf.adapter.game.GameController;
import werwolf.adapter.sql.SQLRollenRepository;
import werwolf.application.game.library.LibraryManager;
import werwolf.domain.game.content.RollenRepository;
import werwolf.domain.game.content.Spieler;

public class GameControllerTests {
	
	@BeforeAll
	public void createGameLibrary() {
		LibraryManager gameLibrary= LibraryManager.INSTANCE;
	}
	
	@Test
	public void erzeugeSpielerTest(){
		//arrange
		RollenRepository repo = new SQLRollenRepository(null);
		repo.initialisiereRolle("Dorfbewohner", "lebt", null, false, false, 0);
		repo.initialisiereRolle("Werwolf", "frisst", "ist boese", true, false, 3 );
		repo.initialisiereRolle("Hexe", "hexhex", "hexhex", false, false, 5 );
		repo.initialisiereRolle("Jaeger", "peng", "peng", false, false, -1 );
		
		
		List<String> rollenNamen = new ArrayList<>();
		rollenNamen.add("Werwolf");
		rollenNamen.add("Werwolf");
		rollenNamen.add("Werwolf");
		rollenNamen.add("Hexe");
		rollenNamen.add("Jaeger");
		rollenNamen.add("Dorfbewohner");
		
		List<String> spielerNamen =  new ArrayList<>();
		spielerNamen.add("Adam");
		spielerNamen.add("Berta");
		spielerNamen.add("Claus");
		spielerNamen.add("Dorian");
		spielerNamen.add("Emil");
		spielerNamen.add("Florence");
		spielerNamen.add("Gundel");
		spielerNamen.add("Herbert");
		spielerNamen.add("Ismael");
		
		//act
		LibraryManager.INSTANCE.setRollenRepository(repo);
		GameController game = new GameController();
		List<Spieler> expectedSpieler = game.erzeugeSpieler(spielerNamen, rollenNamen, "Dorfbewohner");		

		//Assert
		//sortiert die Spieler nach ihrer Rollenprioritaet
		Collections.sort(expectedSpieler);
		MatcherAssert.assertThat(expectedSpieler.size(), is(spielerNamen.size()));
		MatcherAssert.assertThat(expectedSpieler.get(0).getRollenName(), is("Jaeger"));
		MatcherAssert.assertThat(expectedSpieler.get(1).getRollenName(), is("Dorfbewohner"));
		MatcherAssert.assertThat(expectedSpieler.get(2).getRollenName(), is("Dorfbewohner"));
		MatcherAssert.assertThat(expectedSpieler.get(3).getRollenName(), is("Dorfbewohner"));
		MatcherAssert.assertThat(expectedSpieler.get(4).getRollenName(), is("Dorfbewohner"));
		MatcherAssert.assertThat(expectedSpieler.get(5).getRollenName(), is("Werwolf"));
		MatcherAssert.assertThat(expectedSpieler.get(6).getRollenName(), is("Werwolf"));
		MatcherAssert.assertThat(expectedSpieler.get(7).getRollenName(), is("Werwolf"));
		MatcherAssert.assertThat(expectedSpieler.get(8).getRollenName(), is("Hexe"));
		
		
	}

}
