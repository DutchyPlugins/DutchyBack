package nl.thedutchmc.dutchyback.listeners;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import nl.thedutchmc.dutchyback.Back;
import nl.thedutchmc.dutchyhome.SuccessfulHomeTeleportEvent;

public class SuccesfulHomeTeleportEventListener implements Listener {

	@EventHandler
	public void onSuccesfulTpaEvent(SuccessfulHomeTeleportEvent event) {
		
		
		Location loc = event.getLocationBeforeHomeTp();
		UUID uuid = event.getPlayer().getUniqueId();
		
		if(Back.playerBackLocs.containsKey(uuid)) {
			Back.playerBackLocs.replace(uuid, loc);
		} else {
			Back.playerBackLocs.put(uuid, loc);
		}
	}
}
