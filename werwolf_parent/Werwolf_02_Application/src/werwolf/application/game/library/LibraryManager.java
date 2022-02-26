package werwolf.application.game.library;

import werwolf.domain.game.content.KartenRepository;
import werwolf.domain.game.content.RollenRepository;

public class LibraryManager {
	
	private final KartenRepository kartenRepository;
	private final RollenRepository rollenRepository;
	//private final LibraryInterface outputInterface;
	
	public LibraryManager(KartenRepository kartenRepository, RollenRepository rollenRepository) {
		this.kartenRepository = kartenRepository;
		this.rollenRepository = rollenRepository;		
	}
	
	public void initialisiereLibrary() {
		rollenRepository.ladeRollenAusSpeicher();
		kartenRepository.ladeKartenAusSpeicher();
		kartenRepository.verknüpfeKartenMit(rollenRepository);
	}
	
	

}
