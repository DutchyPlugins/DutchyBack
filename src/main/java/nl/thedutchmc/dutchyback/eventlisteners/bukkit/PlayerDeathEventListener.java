package nl.thedutchmc.dutchyback.eventlisteners.bukkit;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import nl.thedutchmc.dutchyback.DutchyBack;

public class PlayerDeathEventListener implements Listener {

	DutchyBack module;
	
	public PlayerDeathEventListener(DutchyBack module) {
		this.module = module;
	}
	
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event) {
		Location deathLocation = event.getEntity().getLocation();
		UUID playerUuid= event.getEntity().getUniqueId();
		
		this.module.setBackLocation(playerUuid, deathLocation);
	}
}
