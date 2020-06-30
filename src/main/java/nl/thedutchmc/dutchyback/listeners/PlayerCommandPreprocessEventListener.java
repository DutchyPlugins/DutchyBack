package nl.thedutchmc.dutchyback.listeners;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import nl.thedutchmc.dutchyback.Back;

public class PlayerCommandPreprocessEventListener implements Listener {

	@EventHandler
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
		
		String command = event.getMessage().split(" ")[0];
		 
		if(command.equals("/tp") || command.equals("/tpa") || command.equals("/home")) {
			
			Location loc = event.getPlayer().getLocation();
			UUID uuid = event.getPlayer().getUniqueId();
			
			if(Back.playerBackLocs.containsKey(uuid)) {
				Back.playerBackLocs.replace(uuid, loc);
			} else {
				Back.playerBackLocs.put(uuid, loc);
			}
		}
	}
}
