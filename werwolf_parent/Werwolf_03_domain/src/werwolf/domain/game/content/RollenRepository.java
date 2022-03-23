package werwolf.domain.game.content;

import java.util.List;
import java.util.Optional;

public interface RollenRepository {
	
	public void speichere(Rolle rolle);
	
	public Optional<Rolle> findeDurch(String name);
	
	public List<Rolle> findeAlleRollen();
	
	public List<String> listeAllerNamen();	
	
	//lade Rollen aus externen Speicher
	public void ladeRollenAusSpeicher();
	
	//füge eine einzelne Rolle dem internen Set hinzu
	public void initialisiereRolle(String name, String funktion,  String beschreibung, boolean istBoese, boolean istSpezial, int prioritaet);
}
