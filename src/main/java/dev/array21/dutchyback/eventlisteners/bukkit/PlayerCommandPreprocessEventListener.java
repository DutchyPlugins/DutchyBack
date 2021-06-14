package dev.array21.dutchyback.eventlisteners.bukkit;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import dev.array21.dutchyback.DutchyBack;

public class PlayerCommandPreprocessEventListener implements Listener {

	private DutchyBack module;
	private List<String> listenForCommands;
	
	public PlayerCommandPreprocessEventListener(DutchyBack module, List<String> listenForCommands) {
		this.module = module;
		this.listenForCommands = listenForCommands;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
		String command = event.getMessage().split(" ")[0].replace("/", "").strip();
		
		if(listenForCommands.contains(command)) {			
			Location location = event.getPlayer().getLocation();
			UUID uuid = event.getPlayer().getUniqueId();
			
			this.module.setBackLocation(uuid, location);
		}
	}
}
