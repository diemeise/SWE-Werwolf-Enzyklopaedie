package werwolf.adapter.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import werwolf.domain.game.content.Rolle;
import werwolf.domain.game.content.RollenRepository;

public class SQLRollenRepository implements RollenRepository{
	
	//Key = Name? TODO
	private HashMap<String, Rolle> rollen;
	private SQLVerbindung verbindung;

	public SQLRollenRepository(SQLVerbindung verbindung) {
		this.rollen = new HashMap<String, Rolle>();
		this.verbindung = verbindung;
	}
	
	@Override
	public void speichere(Rolle rolle) {
		// TODO implementierung nicht vorgesehen
		
	}

	@Override
	public Optional<Rolle> findeDurch(String name) {
		return Optional.ofNullable(rollen.get(name));
	}

	@Override
	public List<Rolle> findeAlleRollen() {
		return new ArrayList<Rolle>(rollen.values());
	}
	
	//TODO besser im interface implementieren
	public void initialisiereRollen(ResultSet resultSet) {
		try {
			while (resultSet.next()) {
				this.rollen.put(resultSet.getString("Name"), new Rolle(
																		resultSet.getString("Name"),
																		resultSet.getString("Funktion"),
																		"test",
																		resultSet.getBoolean("istBoese"),
																		resultSet.getBoolean("istSpezial")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
//	public void erstelleRolle(Map<String, String> map) {
//		
//	

	@Override
	public void ladeRollenAusSpeicher() {
		Map<String, String> ladeRollenArgs= new HashMap<>();
		//TODO wie geht das besser lol?
		ladeRollenArgs.put("Tabellen","Rolle");
		ladeRollenArgs.put("Spalten","Rolle.Name, Rolle.Funktion, Rolle.Name");
		
		initialisiereRollen(verbindung.fuehreAus(ladeRollenArgs));
	}

	

}
