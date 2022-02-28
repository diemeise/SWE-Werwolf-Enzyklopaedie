package werwolf.adapter.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import werwolf.domain.game.content.Karte;
import werwolf.domain.game.content.KartenRepository;
import werwolf.domain.game.content.Rolle;
import werwolf.domain.game.content.RollenRepository;

public class SQLKartenRepository implements KartenRepository{

	//Key = Name? TODO
	private HashMap<String, Karte> karten;
	private SQLVerbindung verbindung;

	public SQLKartenRepository(SQLVerbindung verbindung) {
		this.karten = new HashMap<>();
		this.verbindung = verbindung;

	}
	
	@Override
	public void speichere(Rolle rolle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Karte> findeDurch(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rolle> findeAlleKarten() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//TODO implementieren
	public ArrayList<String> listeAllerNamen(){		
		return new ArrayList<>(this.karten.keySet());
	}
	

	//TODO besser im interface implementieren

	private void initialisiereKarten(ResultSet resultSet) {
		try {
			while (resultSet.next()) {
				this.karten.put(resultSet.getString("Name"), new Karte(
																							null,
																							""));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//Karten werden vor initialisiert, danach wird nur noch die Rolle verlinkt
	@Override
	public void verknuepfeKartenMit(RollenRepository rollenRepository) {
		//key = Karten-Name = Rollen-Name
		for (String key: karten.keySet()) {
			Optional<Rolle>passendeRolle = rollenRepository.findeDurch(key);
			passendeRolle.ifPresent( rolle -> karten.get(key).setRolle(rolle));
			//get(kartenName).setRolle(passendeRolle.get()));
		}
		
	}
	
	@Override
	public void ladeKartenAusSpeicher() {
		Map<String, String> ladeKartenArgs= new HashMap<>();
		ladeKartenArgs.put("Tabellen","Karte");
		ladeKartenArgs.put("Spalten","Rolle.Name, Rolle.Funktion, Karte.idKarte");
		ladeKartenArgs.put("Join", "Rolle");
		ladeKartenArgs.put("JoinArgument", "Karte.Rolle_idRolle = Rolle.idRolle");
		initialisiereKarten(verbindung.fuehreAus(ladeKartenArgs));
	}

	

	


}
