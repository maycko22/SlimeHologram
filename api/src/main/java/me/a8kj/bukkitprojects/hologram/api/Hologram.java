package me.a8kj.bukkitprojects.hologram.api;

import me.a8kj.bukkitprojects.hologram.api.tracker.LocationTracker;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents a hologram object in the Minecraft world.
 * A hologram consists of one or more lines (text or items) and can be
 * either globally visible to all players or restricted to specific viewers.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public interface Hologram {

    /**
     * Gets the unique identifier for this hologram.
     *
     * @return the UUID of the hologram
     */
    UUID getId();

    /**
     * Gets the base location of the hologram in the world.
     * This is the top-center point from which all lines are drawn downwards.
     *
     * @return the base Location
     */
    Location getLocation();

    /**
     * Gets the specific viewer of this hologram, if it is not global.
     *
     * @return an Optional containing the player if they are the sole viewer, otherwise empty
     */
    Optional<Player> getViewer();

    /**
     * Gets the location tracker attached to this hologram, if any.
     * Trackers allow the hologram to move dynamically (e.g., cinematic tracking).
     *
     * @return an Optional containing the LocationTracker, otherwise empty
     */
    Optional<LocationTracker> getTracker();

    /**
     * Gets an unmodifiable list of all lines currently attached to this hologram.
     *
     * @return a list of HologramLine objects
     */
    List<HologramLine> getLines();

    /**
     * Gets the maximum view distance (in blocks) for this hologram.
     * Players beyond this distance will not have the hologram rendered.
     *
     * @return the view distance in blocks
     */
    int getViewDistance();

    /**
     * Checks if this hologram is visible to all players on the server.
     *
     * @return true if the hologram is global, false otherwise
     */
    boolean isGlobal();

    /**
     * Checks if this hologram has been destroyed and is no longer active.
     *
     * @return true if destroyed, false if active
     */
    boolean isDestroyed();

    /**
     * Gets a list of players who currently have this hologram rendered on their client.
     *
     * @return a list of players currently viewing the hologram
     */
    List<Player> getShownTo();

    /**
     * Spawns the hologram for a specific player, sending the necessary packets.
     *
     * @param player the player to spawn the hologram for
     */
    void spawn(Player player);

    /**
     * Despawns the hologram for a specific player, sending entity destroy packets.
     *
     * @param player the player to despawn the hologram for
     */
    void despawn(Player player);

    /**
     * Despawns the hologram for all players currently viewing it.
     */
    void despawnAll();

    /**
     * Permanently destroys this hologram, removing it from all viewers and
     * unregistering it from the internal HologramManager.
     */
    void destroy();
}