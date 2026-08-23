package me.a8kj.bukkitprojects.hologram.base.task;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import lombok.RequiredArgsConstructor;
import me.a8kj.bukkitprojects.hologram.api.Hologram;
import me.a8kj.bukkitprojects.hologram.api.HologramLine;
import me.a8kj.bukkitprojects.hologram.base.HologramManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.regex.Pattern;

/**
 * Task responsible for dynamically updating hologram text lines.
 * It primarily handles the parsing and updating of PlaceholderAPI placeholders
 * so that dynamic text (e.g., scores, online player counts) stays up-to-date
 * for each individual viewer without causing flickering.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
@RequiredArgsConstructor
public class UpdateTask extends BukkitRunnable {
    private final JavaPlugin plugin;

    /**
     * Cached boolean indicating if PlaceholderAPI is installed and enabled.
     * This ensures soft-dependency compatibility without throwing class not found errors.
     */
    private boolean papiEnabled = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;

    /**
     * Regex pattern to intelligently detect valid PlaceholderAPI formats (e.g., %placeholder%).
     * It avoids false positives like "50% off".
     */
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("%[^%]+%");

    @Override
    public void run() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            for (Hologram hologram : HologramManager.getInstance().getHolograms()) {
                if (hologram.isDestroyed() || !hologram.getShownTo().contains(player)) continue;

                for (HologramLine line : hologram.getLines()) {
                    if (line.getText().isPresent()) {
                        String rawText = line.getText().get();

                        if (PLACEHOLDER_PATTERN.matcher(rawText).find()) {
                            rawText = parsePlaceholders(player, rawText);
                        }

                        WrapperPlayServerEntityMetadata metaPacket = new WrapperPlayServerEntityMetadata(
                                line.getEntityId(),
                                Collections.singletonList(new EntityData<>(2, EntityDataTypes.STRING, rawText))
                        );
                        PacketEvents.getAPI().getPlayerManager().sendPacket(player, metaPacket);
                    }
                }
            }
        }
    }

    /**
     * Parses placeholders in the given text using PlaceholderAPI.
     * If PlaceholderAPI is not installed, it returns the original text.
     *
     * @param player the player context for placeholder parsing
     * @param text   the text containing potential placeholders
     * @return the parsed text with placeholders replaced, or the original text if PAPI is unavailable
     */
    private String parsePlaceholders(Player player, String text) {
        if (!papiEnabled) {
            return text;
        }
        try {
            return me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, text);
        } catch (NoClassDefFoundError e) {
            papiEnabled = false;
            return text;
        }
    }
}