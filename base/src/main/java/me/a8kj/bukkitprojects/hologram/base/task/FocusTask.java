package me.a8kj.bukkitprojects.hologram.base.task;

import lombok.RequiredArgsConstructor;
import me.a8kj.bukkitprojects.hologram.api.Hologram;
import me.a8kj.bukkitprojects.hologram.api.HologramLine;
import me.a8kj.bukkitprojects.hologram.api.event.HologramFocusEvent;
import me.a8kj.bukkitprojects.hologram.base.HologramManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Task responsible for detecting when a player looks at (focuses on)
 * or looks away from (unfocuses) an interactive hologram line.
 * It uses vector math (raytracing) to determine the player's crosshair target.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
@RequiredArgsConstructor
public class FocusTask extends BukkitRunnable {
    private final JavaPlugin plugin;
    private final Map<UUID, HologramLine> focusedLines = new ConcurrentHashMap<>();
    private int tick = 0;

    @Override
    public void run() {
        tick++;

        for (Player player : Bukkit.getOnlinePlayers()) {
            for (Hologram hologram : HologramManager.getInstance().getHolograms()) {
                if (hologram.isDestroyed() || !hologram.getShownTo().contains(player)) continue;

                for (HologramLine line : hologram.getLines()) {
                    if (!line.isClickable() || line.getFocusHandler().isEmpty()) continue;

                    boolean isLooking = isLookingAtHologramLine(player, line);
                    boolean isFocused = focusedLines.get(player.getUniqueId()) == line;

                    if (isLooking && !isFocused) {
                        HologramFocusEvent event = new HologramFocusEvent(line, player, true);
                        Bukkit.getPluginManager().callEvent(event);
                        if (!event.isCancelled()) {
                            focusedLines.put(player.getUniqueId(), line);
                            line.getFocusHandler().get().onFocus(player, line);
                        }
                    } else if (!isLooking && isFocused) {
                        HologramFocusEvent event = new HologramFocusEvent(line, player, false);
                        Bukkit.getPluginManager().callEvent(event);
                        if (!event.isCancelled()) {
                            focusedLines.remove(player.getUniqueId());
                            line.getFocusHandler().get().onUnfocus(player, line);
                        }
                    } else if (isLooking && isFocused) {
                        line.getFocusHandler().get().onTickFocus(player, line, tick);
                    }
                }
            }
        }
    }

    /**
     * Calculates if the player's crosshair is pointing directly at the hologram line.
     *
     * @param player the player whose view is being checked
     * @param line   the hologram line to check against
     * @return true if the player is looking at the line within the distance and angle limits
     */
    private boolean isLookingAtHologramLine(Player player, HologramLine line) {
        if (!line.getLocation().getWorld().equals(player.getWorld())) return false;

        Location eye = player.getEyeLocation();
        Location targetLoc = line.getLocation().clone().add(0, 0.5, 0);
        Vector toHologram = targetLoc.toVector().subtract(eye.toVector());
        double distance = toHologram.length();

        if (distance > 5.0) return false;

        toHologram.normalize();
        Vector direction = eye.getDirection();

        double dot = toHologram.dot(direction);
        return dot > 0.98;
    }
}