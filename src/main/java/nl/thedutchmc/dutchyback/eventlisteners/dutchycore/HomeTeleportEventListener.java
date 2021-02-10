package nl.thedutchmc.dutchyback.eventlisteners.dutchycore;

import nl.thedutchmc.dutchyback.DutchyBack;
import nl.thedutchmc.dutchycore.annotations.EventHandler;
import nl.thedutchmc.dutchycore.module.events.ModuleEventListener;
import nl.thedutchmc.dutchyhome.events.HomeTeleportEvent;

public class HomeTeleportEventListener implements ModuleEventListener {

	DutchyBack module;
	
	public HomeTeleportEventListener(DutchyBack module) {
		this.module = module;
	}
	
	@EventHandler
	public void onHomeTeleportEvent(HomeTeleportEvent event) {
		this.module.setBackLocation(event.getTeleportedPlayer().getUniqueId(), event.getPreTeleportLocation());
	}
}
