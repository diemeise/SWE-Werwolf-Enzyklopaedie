package werwolf.adapter.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
	private HashMap<String, String> kartenFunk;
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
	//TODO DRY -> wegwerfen weil die Hash mit Funktion hier reicht?
	//Gibt eine alphabetisch sortierte Liste der Namen aller vorhandenen Karten aus 
	public ArrayList<String> listeAllerNamen(){		
		ArrayList<String> kartenNamenListe = new ArrayList<String>(this.karten.keySet());
		Collections.sort( kartenNamenListe );
		return kartenNamenListe;
	}
	

	//TODO besser im interface implementieren
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

	
	//TODO In eigene Klasse auslagern?
	@Override
	public HashMap<String, String> zeigeNameUndFunktion() {
		String name;
		String funk;
		kartenFunk = new HashMap<>();
		
		for (String key: karten.keySet()) {
			name = karten.get(key).getRolle().getName();
			funk = karten.get(key).getRolle().getFunktion();
			
			kartenFunk.put(name, funk);
		}
	
		return kartenFunk;
	}

	public HashMap<String, Karte> getKarten() {
		return karten;
	}

	


}
