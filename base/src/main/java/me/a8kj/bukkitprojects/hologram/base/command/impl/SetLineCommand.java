package me.a8kj.bukkitprojects.hologram.base.command.impl;

import me.a8kj.bukkitprojects.hologram.base.HologramManager;
import me.a8kj.bukkitprojects.hologram.base.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Command implementation for setting (replacing) the text of an existing line in a hologram.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class SetLineCommand implements SubCommand {

    @Override
    public String getName() {
        return "setline";
    }

    @Override
    public String getPermission() {
        return "holograms.admin";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /sh setline <name> <line_number> <text>");
            return;
        }
        try {
            int lineNum = Integer.parseInt(args[1]) - 1;
            String text = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

            boolean success = HologramManager.getInstance().modifyHologram(args[0], lines -> {
                if (lineNum >= 0 && lineNum < lines.size()) lines.set(lineNum, text);
            });

            if (success) {
                sender.sendMessage(ChatColor.GREEN + "Line set.");
            } else {
                sender.sendMessage(ChatColor.RED + "Cannot set this line! You can only have ONE Icon/3D Item per hologram.");
            }
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Invalid line number.");
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}