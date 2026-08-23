package me.a8kj.bukkitprojects.hologram.base.task;

import lombok.RequiredArgsConstructor;
import me.a8kj.bukkitprojects.hologram.api.Hologram;
import me.a8kj.bukkitprojects.hologram.base.HologramManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Task responsible for managing hologram visibility based on player proximity.
 * Spawns holograms for players who enter the view distance, and despawns them
 * for players who move too far away, optimizing network usage.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
@RequiredArgsConstructor
public class ProximityTask extends BukkitRunnable {
    private final JavaPlugin plugin;

    @Override
    public void run() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            for (Hologram hologram : HologramManager.getInstance().getHolograms()) {
                if (hologram.isDestroyed() || hologram.getTracker().isPresent()) continue;

                boolean isShown = hologram.getLocation().getWorld().equals(player.getWorld());
                double distance = isShown ? hologram.getLocation().distance(player.getLocation()) : Double.MAX_VALUE;

                if (distance > hologram.getViewDistance() && hologram.getShownTo().contains(player)) {
                    hologram.despawn(player);
                } else if (distance <= hologram.getViewDistance() && !hologram.getShownTo().contains(player)) {
                    hologram.spawn(player);
                }
            }
        }
    }
}