package me.a8kj.bukkitprojects.hologram.base.command.impl;

import me.a8kj.bukkitprojects.hologram.base.HologramManager;
import me.a8kj.bukkitprojects.hologram.base.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

/**
 * Command implementation for copying an existing hologram to a new name and location.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class CopyCommand implements SubCommand {

    @Override
    public String getName() {
        return "copy";
    }

    @Override
    public String getPermission() {
        return "holograms.admin";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /hd copy <source> <destination>");
            return;
        }

        String sourceName = args[0];
        String destName = args[1];

        if (!HologramManager.getInstance().getPersistentHologramNames().contains(sourceName)) {
            sender.sendMessage(ChatColor.RED + "Source hologram not found!");
            return;
        }

        if (HologramManager.getInstance().getPersistentHologramNames().contains(destName)) {
            sender.sendMessage(ChatColor.RED + "A hologram with the name '" + destName + "' already exists!");
            return;
        }

        HologramManager.getInstance().copyHologram(sourceName, destName, ((Player) sender).getLocation());
        sender.sendMessage(ChatColor.GREEN + "Hologram copied successfully to '" + destName + "'.");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}