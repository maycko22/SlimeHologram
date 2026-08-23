package me.a8kj.bukkitprojects.hologram.api;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * Represents a session of holograms bound to a specific player.
 * Sessions are useful for temporary, client-side holograms (like lootbox previews)
 * and ensure automatic cleanup when the player disconnects or changes worlds.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public interface HologramSession {

    /**
     * Gets the UUID of the player who owns this session.
     *
     * @return the player's UUID
     */
    UUID getPlayerUuid();

    /**
     * Gets all holograms currently attached to this session.
     *
     * @return a list of session-bound Holograms
     */
    List<Hologram> getHolograms();

    /**
     * Attaches a hologram to this session for management and automatic cleanup.
     *
     * @param hologram the hologram to add
     */
    void addHologram(Hologram hologram);

    /**
     * Removes a hologram from this session.
     *
     * @param hologram the hologram to remove
     */
    void removeHologram(Hologram hologram);

    /**
     * Destroys all holograms attached to this session.
     */
    void destroyAll();

    /**
     * Checks if the session is currently active.
     *
     * @return true if active, false otherwise
     */
    boolean isActive();
}