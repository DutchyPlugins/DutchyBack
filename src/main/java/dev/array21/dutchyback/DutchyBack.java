package dev.array21.dutchyback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.permissions.PermissionDefault;

import dev.array21.dutchyback.commands.BackCommandExecutor;
import dev.array21.dutchyback.commands.BackCommandTabCompleter;
import dev.array21.dutchyback.eventlisteners.bukkit.PlayerCommandPreprocessEventListener;
import dev.array21.dutchyback.eventlisteners.bukkit.PlayerDeathEventListener;
import dev.array21.dutchyback.eventlisteners.dutchycore.HomeTeleportEventListener;
import dev.array21.dutchycore.DutchyCore;
import dev.array21.dutchycore.annotations.Nullable;
import dev.array21.dutchycore.annotations.RegisterModule;
import dev.array21.dutchycore.module.PluginModule;
import dev.array21.dutchycore.module.file.ModuleConfiguration;
import dev.array21.dutchycore.module.file.ModuleFileHandler;
import dev.array21.dutchycore.module.file.ModuleStorage;

@RegisterModule(name = "DutchyBack", author = "Dutchy76", version = "@VERSION@", infoUrl = "https://github.com/DutchyPlugins/DutchyBack")
public class DutchyBack extends PluginModule {

	private ModuleStorage moduleStorage;
	private ModuleConfiguration moduleConfig;
	private HashMap<UUID, Location> backLocations = new HashMap<>();
	
	@Override
	public void enable(DutchyCore plugin) {		
		super.logInfo("Initializing...");
		
		//Get ModuleFileHandler
		ModuleFileHandler moduleFileHandler = super.getModuleFileHandler();
		
		//Read configuration
		File configFile = moduleFileHandler.getModuleConfigurationFile();
		if(!configFile.exists()) {
			super.logInfo("Config file does not exist. Copying from jar.");
			
			InputStream stream = null;
			
			stream = DutchyCore.getModuleLoader().getClassLoader().getResourceAsStream("dutchyback-config.yml");
			try {
				OutputStream out = new FileOutputStream(configFile);
				
				byte[] buffer = stream.readAllBytes();
				
				out.write(buffer);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		this.moduleConfig = moduleFileHandler.getModuleConfiguration();
		
		//Read storage
		this.moduleStorage = moduleFileHandler.getModuleStorage();
		readStorage(this.moduleStorage);
		
		//Register commands
		super.registerCommand("back", new BackCommandExecutor(this), this);
		
		//Register tab completers
		super.registerTabCompleter("back", new BackCommandTabCompleter(), this);
		
		//Register permissions
		super.registerPermissionNode("dutchyback.back", PermissionDefault.TRUE, "Allows full usage of /back", null);
		
		@SuppressWarnings("unchecked")
		List<String> commands = (List<String>) this.getConfigOption("commands");
		
		//Register event listeners
		super.registerEventListener(new PlayerCommandPreprocessEventListener(this, commands));
		super.registerEventListener(new PlayerDeathEventListener(this));
		
		super.logInfo("Initialization complete.");
	}

	@Override
	public void postEnable() {
		if(super.isModuleRegistered("DutchyHome")) {
			super.logInfo("Module DutchyHome is installed. Enabling listener!");
			super.registerModuleEventListener(new HomeTeleportEventListener(this));
		}
	}
	
	/**
	 * Get a back location from the map of back locations
	 * @param uuid The UUID to get the location for
	 * @return Returns the location, or null if none was found
	 */
	@Nullable
	public Location getBackLocation(UUID uuid) {
		return this.backLocations.get(uuid);
	}
	
	/**
	 * Add a back location to the map of back locations<br>
	 * Will also call {@link #writeToDisk()}
	 * @param uuid The UUID associated with the location
	 * @param location the Location to add
	 */
	public void setBackLocation(UUID uuid, Location location) {
		this.backLocations.put(uuid, location);
		this.writeToDisk();
	}
	
	/**
	 * Remove a location from the map of back locations<br>
	 * Also calls {@link #writeToDisk()}
	 * @param uuid The UUID for the location to remove
	 */
	public void removeBackLocation(UUID uuid) {
		this.backLocations.remove(uuid);
		this.writeToDisk();
	}
	
	/**
	 * Get a configuration option
	 * @param key The key to get
	 * @return Returns the configuration option associated with the key, or null if non was foudn
	 */
	@Nullable
	public Object getConfigOption(String key) {
		return this.moduleConfig.getValue(key);
	}
	
	/**
	 * Write the backlocations map to disk
	 */
	public void writeToDisk() {
		final String seperator = "<-=->";
		
		List<String> toWrite = new ArrayList<>();
		for(Map.Entry<UUID, Location> entry : this.backLocations.entrySet()) {
			
			String uuidStr = entry.getKey().toString();
			
			Location loc = entry.getValue();
			String worldName = loc.getWorld().getName();
			int x = loc.getBlockX();
			int y = loc.getBlockY();
			int z = loc.getBlockZ();
			
			String toWriteStr = uuidStr + seperator + worldName + seperator + x + seperator + y + seperator + z;
			toWrite.add(toWriteStr);
		}
		
		this.moduleStorage.setValue("backs", toWrite);
		this.moduleStorage.save();
	}
	
	@SuppressWarnings("unchecked")
	private void readStorage(ModuleStorage moduleStorage) {
		Object backLocationsRaw = moduleStorage.getValue("backs");
		
		//Storage file is empty, populate it and return
		if(backLocationsRaw == null) {
			String[] backs = new String[0];
			moduleStorage.setValue("backs", backs);
			return;
		}
		
		//Determine if we're dealing with a String[] or a List<String>
		//we want a List<String>, so get it into that format
		List<String> backs = null;
		if(backLocationsRaw instanceof List<?>) {
			backs = (List<String>) backLocationsRaw;
		} else if(backLocationsRaw instanceof String[]) {
			backs = Arrays.asList((String[]) backLocationsRaw);
			
			// https://stackoverflow.com/a/5755510/10765090
			backs = new ArrayList<>(backs);
		}
		
		//Loop over every String and parse it
		backs.forEach(backString -> {
			String[] parts = backString.split("<-=->");
			
			UUID backOwner = UUID.fromString(parts[0]);
			
			World world = Bukkit.getWorld(parts[1]);
			int x = Integer.valueOf(parts[2]);
			int y = Integer.valueOf(parts[3]);
			int z = Integer.valueOf(parts[4]);
			
			Location backLocation = new Location(world, x, y, z);
			
			this.backLocations.put(backOwner, backLocation);
			System.out.println("Adding back location for " + backOwner.toString());
		});
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
