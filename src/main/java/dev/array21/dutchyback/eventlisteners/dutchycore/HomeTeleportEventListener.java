package dev.array21.dutchyback.eventlisteners.dutchycore;

import dev.array21.dutchyback.DutchyBack;
import dev.array21.dutchycore.annotations.EventHandler;
import dev.array21.dutchycore.module.events.ModuleEventListener;
import dev.array21.dutchyhome.events.HomeTeleportEvent;

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
