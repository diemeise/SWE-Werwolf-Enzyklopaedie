package werwolf.domain.game.content;

import java.util.HashMap;
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
	
	//Erstelle eine HashMap mit Name und Funktion fuer den spaeteren output
	public Map<String, String> zeigeNameUndFunktion();
	
	//Get HashMap mit Name und Karte
	public Map<String, Karte> getKarten();
}
