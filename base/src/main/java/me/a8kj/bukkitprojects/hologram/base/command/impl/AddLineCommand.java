package me.a8kj.bukkitprojects.hologram.base.command.impl;

import me.a8kj.bukkitprojects.hologram.base.HologramManager;
import me.a8kj.bukkitprojects.hologram.base.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Command implementation for adding a new line to an existing hologram.
 * Validates that only one icon/item line exists per hologram.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class AddLineCommand implements SubCommand {

    @Override
    public String getName() {
        return "addline";
    }

    @Override
    public String getPermission() {
        return "holograms.admin";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /sh addline <name> <text>");
            return;
        }
        String name = args[0];
        String text = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        boolean success = HologramManager.getInstance().modifyHologram(name, lines -> lines.add(text));

        if (success) {
            sender.sendMessage(ChatColor.GREEN + "Line added.");
        } else {
            sender.sendMessage(ChatColor.RED + "Cannot add this line! You can only have ONE Icon/3D Item per hologram.");
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}