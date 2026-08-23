package me.a8kj.bukkitprojects.hologram.api;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Provides core context and lookup functionality for the hologram system.
 * This interface bridges the gap between packet-level interactions and the logical
 * hologram objects, allowing packet listeners to resolve entities.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public interface HologramContext {

    /**
     * Resolves a packet-level entity ID back to the logical {@link HologramLine} it belongs to.
     * This is essential for handling click and interaction events on holograms.
     *
     * @param entityId the entity ID received from the client packet
     * @return the corresponding HologramLine, or null if not found
     */
    HologramLine getLineByEntityId(int entityId);

    /**
     * Gets the main JavaPlugin instance managing this hologram context.
     *
     * @return the JavaPlugin instance
     */
    JavaPlugin getPlugin();
}