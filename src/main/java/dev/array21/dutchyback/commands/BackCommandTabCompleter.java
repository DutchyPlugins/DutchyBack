package dev.array21.dutchyback.commands;

import org.bukkit.command.CommandSender;

import dev.array21.dutchycore.module.commands.ModuleTabCompleter;

public class BackCommandTabCompleter implements ModuleTabCompleter {

	@Override
	public String[] complete(CommandSender sender, String[] args) {
		return null; //We dont want tab completion
	}
}