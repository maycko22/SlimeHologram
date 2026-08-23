package me.a8kj.bukkitprojects.hologram.base.command.impl;

import me.a8kj.bukkitprojects.hologram.api.Hologram;
import me.a8kj.bukkitprojects.hologram.base.HologramManager;
import me.a8kj.bukkitprojects.hologram.base.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import java.util.Collections;
import java.util.List;

/**
 * Command implementation for aligning a hologram to another along specified axes.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class AlignCommand implements SubCommand {

    @Override
    public String getName() {
        return "align";
    }

    @Override
    public String getPermission() {
        return "holograms.admin";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /sh align <x|y|z|xz> <hologram_to_move> <reference_hologram>");
            return;
        }
        String axis = args[0].toLowerCase();
        Hologram toMove = HologramManager.getInstance().getPersistentHologram(args[1]);
        Hologram reference = HologramManager.getInstance().getPersistentHologram(args[2]);

        if (toMove == null || reference == null) {
            sender.sendMessage(ChatColor.RED + "One or both holograms not found.");
            return;
        }

        Location newLoc = toMove.getLocation().clone();
        Location refLoc = reference.getLocation();

        switch (axis) {
            case "x" -> newLoc.setX(refLoc.getX());
            case "y" -> newLoc.setY(refLoc.getY());
            case "z" -> newLoc.setZ(refLoc.getZ());
            case "xz" -> { newLoc.setX(refLoc.getX()); newLoc.setZ(refLoc.getZ()); }
            default -> {
                sender.sendMessage(ChatColor.RED + "Invalid axis. Use x, y, z, or xz.");
                return;
            }
        }
        HologramManager.getInstance().moveHologram(args[1], newLoc);
        sender.sendMessage(ChatColor.GREEN + "Aligned.");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}