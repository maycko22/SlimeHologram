package me.a8kj.bukkitprojects.hologram.base.command.impl;

import me.a8kj.bukkitprojects.hologram.base.command.SubCommand;
import org.bukkit.command.CommandSender;
import java.util.Collections;
import java.util.List;

/**
 * Command implementation that displays the help menu with all available commands.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class HelpCommand implements SubCommand {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getPermission() {
        return "holograms.use";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("§6=== Holograms Help ===");
        sender.sendMessage("§e/sh create <name> [text] §7- Create a new hologram.");
        sender.sendMessage("§e/sh delete <name> §7- Delete a hologram.");
        sender.sendMessage("§e/sh list §7- List all holograms.");
        sender.sendMessage("§e/sh addline <name> <text> §7- Add a line.");
        sender.sendMessage("§e/sh edit <name> §7- Open interactive edit menu.");
        sender.sendMessage("§e/sh reload §7- Reload holograms from config.");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}