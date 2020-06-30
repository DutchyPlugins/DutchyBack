package nl.thedutchmc.dutchyback;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class CommandHandler implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(command.getName().equals("back")) {
			
			if(sender instanceof Player) {
				Player senderP = (Player) sender;

				if(Back.playerBackLocs.containsKey(senderP.getUniqueId())) {
					Location tpTo = Back.playerBackLocs.get(senderP.getUniqueId());
					
					sender.sendMessage(ChatColor.GOLD + "Teleporting...");
					senderP.teleport(tpTo);
					Back.playerBackLocs.remove(senderP.getUniqueId());
					
				} else {
					sender.sendMessage(ChatColor.GOLD + "You have not teleported or died yet, or you have already used /back!");
				}

				
			} else {
				sender.sendMessage(ChatColor.RED + "You can only use this command if you are a player!");
			}
			
			return true;
		}
		
		return false;
	}

}
