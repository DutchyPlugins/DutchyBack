package nl.thedutchmc.dutchyback.listeners;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import nl.thedutchmc.dutchyback.Back;
import nl.thedutchmc.dutchytpa.SuccessfulTpaEvent;

public class SuccessfulTpaEventListener implements Listener {
	
	@EventHandler
	public void onSuccessfulTpaEvent(SuccessfulTpaEvent event) {
		
		Location loc = event.getLocationBeforeTpa();
		UUID uuid = event.getPlayer().getUniqueId();
		
		if(Back.playerBackLocs.containsKey(uuid)) {
			Back.playerBackLocs.replace(uuid, loc);
		} else {
			Back.playerBackLocs.put(uuid, loc);
		}
	}

}
