package werwolf.application.game.library;

import werwolf.domain.game.content.KartenRepository;
import werwolf.domain.game.content.RollenRepository;

//Singleton!
public enum LibraryManager {
	
	INSTANCE();
	
	 private KartenRepository kartenRepository;
	 private RollenRepository rollenRepository;
	//private final LibraryInterface outputInterface;
	
	private LibraryManager() {
			
	}
	
	public void initialisiereLibrary() {
		rollenRepository.ladeRollenAusSpeicher();
		kartenRepository.ladeKartenAusSpeicher();
		kartenRepository.verknuepfeKartenMit(rollenRepository);
	}
	
	public KartenRepository getKartenRepository() {
		return kartenRepository;
	}
	
	public RollenRepository getRollenRepository() {
		return rollenRepository;
	}
	public void setKartenRepository(KartenRepository kr) {
		this.kartenRepository = kr;
	}
	
	public void setRollenRepository(RollenRepository rr) {
		this.rollenRepository = rr;
	}
	

}
