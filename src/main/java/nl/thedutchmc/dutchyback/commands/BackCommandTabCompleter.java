package nl.thedutchmc.dutchyback.commands;

import org.bukkit.command.CommandSender;

import nl.thedutchmc.dutchycore.module.commands.ModuleTabCompleter;

public class BackCommandTabCompleter implements ModuleTabCompleter {

	@Override
	public String[] complete(CommandSender sender, String[] args) {
		return null; //We dont want tab completion
	}
}