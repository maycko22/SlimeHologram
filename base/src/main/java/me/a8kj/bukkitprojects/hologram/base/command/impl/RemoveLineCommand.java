package me.a8kj.bukkitprojects.hologram.base.command.impl;

import me.a8kj.bukkitprojects.hologram.base.HologramManager;
import me.a8kj.bukkitprojects.hologram.base.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import java.util.Collections;
import java.util.List;

/**
 * Command implementation for removing a specific line from a hologram by its index.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class RemoveLineCommand implements SubCommand {

    @Override
    public String getName() {
        return "removeline";
    }

    @Override
    public String getPermission() {
        return "holograms.admin";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /sh removeline <name> <line_number>");
            return;
        }
        try {
            int lineNum = Integer.parseInt(args[1]) - 1;
            HologramManager.getInstance().modifyHologram(args[0], lines -> {
                if (lineNum >= 0 && lineNum < lines.size()) lines.remove(lineNum);
            });
            sender.sendMessage(ChatColor.GREEN + "Line removed.");
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Invalid line number.");
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}