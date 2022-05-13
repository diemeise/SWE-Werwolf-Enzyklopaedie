package werwolf.adapter.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import werwolf.domain.game.content.Rolle;
import werwolf.domain.game.content.RollenRepository;

public class SQLRollenRepository implements RollenRepository{
	
	//Key = Name in lower case
	private Map<String, Rolle> rollen;
	private SQLVerbindung verbindung;

	public SQLRollenRepository(SQLVerbindung verbindung) {
		this.rollen = new HashMap<String, Rolle>();
		this.verbindung = verbindung;
	}
	
	@Override
	public void speichere(Rolle rolle) {
		//  implementierung nicht vorgesehen
		
	}

	@Override
	public Optional<Rolle> findeDurch(String name) {
		return Optional.ofNullable(rollen.get(name.toLowerCase()));
	}

	@Override
	public List<Rolle> findeAlleRollen() {
		return new ArrayList<Rolle>(rollen.values());
	}

	//Gibt eine alphabetisch sortierte Liste der Namen aller vorhandenen Rollen aus 
	@Override
	public ArrayList<String> listeAllerNamen() {
		ArrayList<String> rollenNamenListe = new ArrayList<String>(this.rollen.keySet());
		Collections.sort( rollenNamenListe );
		return rollenNamenListe;
	}
	
	public void initialisiereRollen(ResultSet resultSet) {
		try {
			while (resultSet.next()) {
				this.initialisiereRolle(resultSet.getString("Name"),
											resultSet.getString("Funktion"),
											"test",
											resultSet.getBoolean("istBoese"),
											resultSet.getBoolean("istSpezial"),
											resultSet.getInt("prioritaet"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void ladeRollenAusSpeicher() {
		Map<String, String> ladeRollenArgs= new HashMap<>();

		ladeRollenArgs.put("Tabellen","Rolle");
		ladeRollenArgs.put("Spalten","Rolle.Name, Rolle.Funktion, Rolle.Name, Rolle.istBoese, Rolle.istSpezial, Rolle.Prioritaet");
		
		initialisiereRollen(verbindung.fuehreAus(ladeRollenArgs));
	}

	//nur falls Datenbankserver abfackelt oder wir einen anderen usecase finden
	
	@Override
	public void initialisiereRolle(String name, String funktion, String beschreibung, boolean istBoese, boolean istSpezial, int prioritaet) {
		this.rollen.put(name.toLowerCase(), new Rolle(name, funktion, beschreibung, istSpezial, istBoese, prioritaet));
		
	}


	

}
