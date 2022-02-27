package werwolf.domain.game.content;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RollenRepository {
	
	public void speichere(Rolle rolle);
	
	public Optional<Rolle> findeDurch(String name);
	
	public List<Rolle> findeAlleRollen();
	
	public void ladeRollenAusSpeicher();
	
	//public void initialisiereRollen();
}
