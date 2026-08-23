package me.a8kj.bukkitprojects.hologram.base.command.impl;

import me.a8kj.bukkitprojects.hologram.base.HologramManager;
import me.a8kj.bukkitprojects.hologram.base.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

/**
 * Command implementation that lists all existing holograms.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class ListCommand implements SubCommand {

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getPermission() {
        return "holograms.use";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (HologramManager.getInstance().getPersistentHologramNames().isEmpty()) {
            sender.sendMessage(ChatColor.RED + "No holograms found.");
            return;
        }
        sender.sendMessage(ChatColor.GOLD + "=== Holograms List ===");
        HologramManager.getInstance().getPersistentHologramNames().forEach(name ->
                sender.sendMessage(ChatColor.YELLOW + "- " + name)
        );
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}