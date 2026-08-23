package me.a8kj.bukkitprojects.hologram.base.command.impl;

import me.a8kj.bukkitprojects.hologram.api.Hologram;
import me.a8kj.bukkitprojects.hologram.base.HologramManager;
import me.a8kj.bukkitprojects.hologram.base.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Collections;
import java.util.List;

/**
 * Command implementation for teleporting the player to a hologram's location.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class TeleportCommand implements SubCommand {

    @Override
    public String getName() {
        return "teleport";
    }

    @Override
    public String getPermission() {
        return "holograms.admin";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /sh teleport <name>");
            return;
        }
        Hologram holo = HologramManager.getInstance().getPersistentHologram(args[0]);
        if (holo == null) {
            sender.sendMessage(ChatColor.RED + "Hologram not found.");
            return;
        }
        ((Player) sender).teleport(holo.getLocation());
        sender.sendMessage(ChatColor.GREEN + "Teleported to hologram.");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}