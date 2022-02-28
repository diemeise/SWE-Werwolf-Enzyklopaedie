package werwolf.domain.game.content;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RollenRepository {
	
	public void speichere(Rolle rolle);
	
	public Optional<Rolle> findeDurch(String name);
	
	public List<Rolle> findeAlleRollen();
	
	//lade Rollen aus externen Speicher
	public void ladeRollenAusSpeicher();
	
	//füge eine einzelne Rolle dem internen Set hinzu
	public void initialisiereRolle(String name, String funktion, boolean istBoese, boolean istSpezial);
}
