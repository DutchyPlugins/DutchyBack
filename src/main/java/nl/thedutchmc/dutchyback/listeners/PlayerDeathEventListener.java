package nl.thedutchmc.dutchyback.listeners;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import nl.thedutchmc.dutchyback.Back;

public class PlayerDeathEventListener implements Listener {

	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event) {
		
		Location loc = event.getEntity().getLocation();
		UUID uuid = event.getEntity().getUniqueId();
		
		if(Back.playerBackLocs.containsKey(uuid)) {
			Back.playerBackLocs.replace(uuid, loc);
		} else {
			Back.playerBackLocs.put(uuid, loc);
		}
	}
}
