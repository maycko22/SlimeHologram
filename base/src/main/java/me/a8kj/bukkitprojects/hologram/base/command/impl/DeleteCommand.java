package me.a8kj.bukkitprojects.hologram.base.command.impl;

import me.a8kj.bukkitprojects.hologram.base.HologramManager;
import me.a8kj.bukkitprojects.hologram.base.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import java.util.Collections;
import java.util.List;

/**
 * Command implementation for deleting an existing hologram.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class DeleteCommand implements SubCommand {

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getPermission() {
        return "holograms.admin";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /sh delete <name>");
            return;
        }
        if (!HologramManager.getInstance().getPersistentHologramNames().contains(args[0])) {
            sender.sendMessage(ChatColor.RED + "Hologram not found!");
            return;
        }
        HologramManager.getInstance().deletePersistentHologram(args[0]);
        sender.sendMessage(ChatColor.GREEN + "Hologram deleted.");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}