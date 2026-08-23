package me.a8kj.bukkitprojects.hologram.base.command.impl;

import me.a8kj.bukkitprojects.hologram.base.HologramManager;
import me.a8kj.bukkitprojects.hologram.base.command.SubCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

/**
 * Command implementation that displays an interactive chat menu for editing a hologram.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class EditCommand implements SubCommand {

    @Override
    public String getName() {
        return "edit";
    }

    @Override
    public String getPermission() {
        return "holograms.admin";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;
        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Usage: /sh edit <name>");
            return;
        }
        String name = args[0];
        if (!HologramManager.getInstance().getPersistentHologramNames().contains(name)) {
            player.sendMessage(ChatColor.RED + "Hologram not found!");
            return;
        }

        player.sendMessage(ChatColor.GOLD + "=== Editing: " + name + " ===");

        TextComponent addLine = new TextComponent(ChatColor.GREEN + "[Add Line] ");
        addLine.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/sh addline " + name + " "));
        addLine.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to add a new line").create()));

        TextComponent removeLine = new TextComponent(ChatColor.RED + "[Remove Line] ");
        removeLine.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/sh removeline " + name + " "));
        removeLine.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to remove a line").create()));

        TextComponent moveHere = new TextComponent(ChatColor.AQUA + "[Move Here]");
        moveHere.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sh movehere " + name));
        moveHere.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Move hologram to your location").create()));

        player.spigot().sendMessage(addLine, removeLine, moveHere);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}