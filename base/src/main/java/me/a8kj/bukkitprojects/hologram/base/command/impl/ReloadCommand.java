package me.a8kj.bukkitprojects.hologram.base.command.impl;

import me.a8kj.bukkitprojects.hologram.base.HologramManager;
import me.a8kj.bukkitprojects.hologram.base.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

/**
 * Command implementation for reloading all holograms from storage.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class ReloadCommand implements SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getPermission() {
        return "holograms.admin";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        HologramManager.getInstance().getPersistentHologramsCollection().forEach(h -> h.destroy());
        HologramManager.getInstance().getPersistentHolograms().clear();
        HologramManager.getInstance().loadHolograms();
        sender.sendMessage(ChatColor.GREEN + "Holograms reloaded.");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}