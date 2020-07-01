package nl.thedutchmc.dutchyback;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import nl.thedutchmc.dutchyback.listeners.PlayerCommandPreprocessEventListener;
import nl.thedutchmc.dutchyback.listeners.PlayerDeathEventListener;
import nl.thedutchmc.dutchyback.listeners.SuccesfulHomeTeleportEventListener;
import nl.thedutchmc.dutchyback.listeners.SuccessfulTpaEventListener;

public class Back extends JavaPlugin {

	public static HashMap<UUID, Location> playerBackLocs = new HashMap<>();
	
	public static boolean isDutchyTpaInstalled = false;
	public static boolean isDutchyHomeInstalled = false;
	
	@Override
	public void onEnable() {
		
		if(Bukkit.getPluginManager().isPluginEnabled("DutchyTPA")) isDutchyTpaInstalled = true;
		if(Bukkit.getPluginManager().isPluginEnabled("DutchyHome")) isDutchyHomeInstalled = true;
		
		Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessEventListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDeathEventListener(), this);
		
		if(isDutchyTpaInstalled) Bukkit.getPluginManager().registerEvents(new SuccessfulTpaEventListener(), this);
		if(isDutchyHomeInstalled) Bukkit.getPluginManager().registerEvents(new SuccesfulHomeTeleportEventListener(), this);
		
		
		getCommand("back").setExecutor(new CommandHandler());
		
	}
}
