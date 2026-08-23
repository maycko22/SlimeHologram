package me.a8kj.bukkitprojects.hologram.api.storage;

import org.bukkit.Location;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents the persistent data structure of a hologram.
 * This interface abstracts the underlying storage format (like YAML or MySQL),
 * allowing the plugin to interact with hologram data without needing to know
 * how or where it is saved.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public interface HologramData {

    /**
     * Gets the unique name identifier of the hologram.
     *
     * @return the hologram's name
     */
    String getName();

    /**
     * Gets the base spawn location of the hologram in the world.
     *
     * @return the Location object
     */
    Location getLocation();

    /**
     * Gets the raw text lines configured for this hologram.
     * These lines may contain plain text, color codes, or special item prefixes (e.g., ICON:).
     *
     * @return a list of raw string lines
     */
    List<String> getLines();

    /**
     * Checks if this hologram is private (client-side).
     * A private hologram is only visible to its owner.
     *
     * @return true if the hologram is private, false otherwise
     */
    boolean isPrivate();

    /**
     * Gets the owner of this hologram if it is private.
     *
     * @return an Optional containing the owner's UUID, or empty if it is global
     */
    Optional<UUID> getOwner();
}