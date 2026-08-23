package me.a8kj.bukkitprojects.hologram.base.command.impl;

import lombok.RequiredArgsConstructor;
import me.a8kj.bukkitprojects.hologram.api.storage.HologramData;
import me.a8kj.bukkitprojects.hologram.api.storage.HologramStorage;
import me.a8kj.bukkitprojects.hologram.base.HologramManager;
import me.a8kj.bukkitprojects.hologram.base.command.SubCommand;
import me.a8kj.bukkitprojects.hologram.base.storage.SimpleHologramData;
import me.a8kj.bukkitprojects.hologram.util.markdown.MarkdownParser;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Command implementation for creating a new hologram.
 * Supports creating private (-p) holograms, parsing markdown/item icons,
 * and creating multiple lines using the '|' separator.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
@RequiredArgsConstructor
public class CreateCommand implements SubCommand {

    private final HologramStorage storage;
    private final MarkdownParser parser;

    @Override
    public String getName() {
        return "create";
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
            player.sendMessage(ChatColor.RED + "Usage: /sh create <name> [-p] [text|text2|...]");
            return;
        }

        List<String> argsList = new ArrayList<>(Arrays.asList(args));

        boolean isPrivate = argsList.remove("-p");

        String name = argsList.get(0);

        if (HologramManager.getInstance().getPersistentHologramNames().contains(name)) {
            player.sendMessage(ChatColor.RED + "A hologram with the name '" + name + "' already exists!");
            return;
        }

        List<String> lines = new ArrayList<>();
        if (argsList.size() > 1) {
            String fullText = String.join(" ", argsList.subList(1, argsList.size()));
            for (String part : fullText.split("\\|")) {
                lines.add(part.trim());
            }
        } else {
            lines.add(ChatColor.GRAY + "New Hologram");
        }

        List<String> parsedLines = new ArrayList<>();
        for (String l : lines) {
            if (l.startsWith("ICON:") || l.startsWith("ICON_3D:")) {
                parsedLines.add(l);
            } else {
                parsedLines.add(parser.parse(l));
            }
        }

        HologramData data = new SimpleHologramData(name, player.getLocation(), parsedLines, isPrivate, isPrivate ? player.getUniqueId() : null);
        HologramManager.getInstance().createPersistentHologram(name, data.getLocation(), data.getLines(), data.isPrivate(), data.getOwner().orElse(null));
        storage.save(data);

        player.sendMessage(ChatColor.GREEN + "Hologram '" + name + "' created!" + (isPrivate ? " (Private)" : ""));
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}