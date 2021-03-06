package werwolf.adapter.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import werwolf.domain.game.content.Karte;
import werwolf.domain.game.content.KartenRepository;
import werwolf.domain.game.content.Rolle;
import werwolf.domain.game.content.RollenRepository;

public class SQLKartenRepository implements KartenRepository{

	//Key = Name der Karte in lower case 
	private Map<String, Karte> karten;
	private SQLVerbindung verbindung;

	public SQLKartenRepository(SQLVerbindung verbindung) {
		this.karten = new HashMap<>();
		this.verbindung = verbindung;

	}
	
	@Override
	public void speichere(Rolle rolle) {
		// Implementierung nicht vorgesehen
		
	}

	@Override
	public Optional<Karte> findeDurch(String name) {
		return Optional.ofNullable(karten.get(name.toLowerCase()));
	}

	@Override
	public List<Rolle> findeAlleKarten() {
		return null;
	}
	
	//Gibt eine alphabetisch sortierte Liste der Namen aller vorhandenen Karten aus 
	public ArrayList<String> listeAllerNamen(){		
		ArrayList<String> kartenNamenListe = new ArrayList<String>(this.karten.keySet());
		Collections.sort( kartenNamenListe );
		return kartenNamenListe;
	}
	

	public void initialisiereKarten(ResultSet resultSet) {
		try {
			while (resultSet.next()) {
				this.initialisiereKarte(resultSet.getString("Name"), new Karte(
																				null,
																				""));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//Karten werden vorinitialisiert, danach wird  noch die Rolle verlinkt
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

	@Override
	public void initialisiereKarte(String name , Karte karte) {
		this.karten.put(name, karte);
	}

	
	
	/**
	 * Gibt eine bestimmte Art von Karten zurueck
	 * @param filter: moegliche Werte: "gut", "boese", "spezial"
	 */
	@Override
	public Map<String, Karte> getKartenMitFilter(String filter) {
		
		Map<String, Karte> filteredKarten = new HashMap<>();
		
		for (String name: karten.keySet()) {
			if(filter == "gut") {
				if(!karten.get(name).getRolle().istBoese())
					filteredKarten.put(name, karten.get(name));
			}
			if(filter == "boese") {
				if(karten.get(name).getRolle().istBoese())
					filteredKarten.put(name, karten.get(name));
			}
			if(filter == "spezial") {
				if(karten.get(name).getRolle().istSpezial())
					filteredKarten.put(name, karten.get(name));
			}			
		}	
		return filteredKarten;
	}
	


	public Map<String, Karte> getKarten() {
		return karten;
	}


}
