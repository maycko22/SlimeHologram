package me.a8kj.bukkitprojects.hologram.base.command.impl;

import me.a8kj.bukkitprojects.hologram.base.HologramManager;
import me.a8kj.bukkitprojects.hologram.base.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Collections;
import java.util.List;

/**
 * Command implementation for moving a hologram to the player's current location.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class MoveHereCommand implements SubCommand {

    @Override
    public String getName() {
        return "movehere";
    }

    @Override
    public String getPermission() {
        return "holograms.admin";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /sh movehere <name>");
            return;
        }
        HologramManager.getInstance().moveHologram(args[0], ((Player) sender).getLocation());
        sender.sendMessage(ChatColor.GREEN + "Hologram moved to your location.");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}