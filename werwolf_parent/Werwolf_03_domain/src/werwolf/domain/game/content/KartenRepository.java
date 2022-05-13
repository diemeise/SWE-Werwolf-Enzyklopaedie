package werwolf.domain.game.content;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface KartenRepository {
	
	public void speichere(Rolle rolle);
	
	public Optional<Karte> findeDurch(String name);
	
	public List<Rolle> findeAlleKarten();
	
	public List<String> listeAllerNamen();	
	
	//lade Karten aus externen Speicher
	public void ladeKartenAusSpeicher();
	
	//fuege eine einzelne Karte dem interne Set hinzu
	public void initialisiereKarte(String name, Karte karte);
	
	public void verknuepfeKartenMit(RollenRepository rollenRepository);
	
		
	//Erstellt eine Hashmap, die nur Karten eines bestimmten Typs enthält
	public Map<String, Karte> getKartenMitFilter(String filter);
	
	public Map<String, Karte> getKarten();
}
