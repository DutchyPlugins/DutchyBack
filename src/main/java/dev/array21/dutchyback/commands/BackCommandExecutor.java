package dev.array21.dutchyback.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.array21.dutchyback.DutchyBack;
import dev.array21.dutchycore.Triple;
import dev.array21.dutchycore.module.commands.ModuleCommand;
import dev.array21.dutchycore.utils.Utils;

public class BackCommandExecutor implements ModuleCommand {

	private DutchyBack module;
	
	public BackCommandExecutor(DutchyBack module) {
		this.module = module;
	}
	
	@Override
	public boolean fire(CommandSender sender, String[] args) {

		if(!sender.hasPermission("dutchyback.back")) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
			return true;
		}
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players in-game may use this command!");
			return true;
		}
		
		Player player = (Player) sender;
		Location backLocation = this.module.getBackLocation(player.getUniqueId());
		
		if(backLocation == null) {
			sender.sendMessage(ChatColor.GOLD + "You currently have no back.");
			return true;
		}
		
		this.module.removeBackLocation(player.getUniqueId());
		
		String message = Utils.processColours(ChatColor.GOLD + "Teleporting to %d, %d, %d!", new Triple<String, ChatColor, ChatColor>("%s", ChatColor.RED, ChatColor.GOLD));
		sender.sendMessage(String.format(message, backLocation.getBlockX(), backLocation.getBlockY(), backLocation.getBlockZ()));
		
		player.teleport(backLocation);
		
		return true;
	}

}
