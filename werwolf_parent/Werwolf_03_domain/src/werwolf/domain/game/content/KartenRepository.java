package werwolf.domain.game.content;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface KartenRepository {
	
	public void speichere(Rolle rolle);
	
	public Optional<Karte> findeDurch(String name);
	
	public List<Rolle> findeAlleKarten();
	
	public void ladeKartenAusSpeicher();
	
	public void verknuepfeKartenMit(RollenRepository rollenRepository);
}
