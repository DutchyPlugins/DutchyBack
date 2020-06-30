package nl.thedutchmc.dutchyback;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import nl.thedutchmc.dutchyback.listeners.PlayerCommandPreprocessEventListener;
import nl.thedutchmc.dutchyback.listeners.PlayerDeathEventListener;

public class Back extends JavaPlugin {

	public static HashMap<UUID, Location> playerBackLocs = new HashMap<>();
	
	@Override
	public void onEnable() {
		
		Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessEventListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDeathEventListener(), this);
		
		getCommand("back").setExecutor(new CommandHandler());
		
	}
	
}
