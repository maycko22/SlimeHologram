package me.a8kj.bukkitprojects.hologram.api.tracker;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Interface for dynamically tracking and updating a hologram's location.
 * This is used for cinematic effects where the hologram needs to follow
 * a player's eye level or a moving entity.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public interface LocationTracker {

    /**
     * Calculates and returns the current location for the hologram
     * based on the specific viewer's state.
     *
     * @param viewer the player viewing the hologram
     * @return the new Location for the hologram, or null if it should remain in place
     */
    Location getLocation(Player viewer);

    /**
     * Checks if the tracking lifecycle is complete.
     * If true, the tracker will stop updating and the hologram will freeze
     * at its last known location (or despawn, depending on implementation).
     *
     * @return true if tracking is complete, false to continue tracking
     */
    boolean isComplete();
}