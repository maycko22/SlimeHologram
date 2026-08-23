package me.a8kj.bukkitprojects.hologram.api;

import me.a8kj.bukkitprojects.hologram.api.tracker.LocationTracker;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Builder pattern interface for constructing {@link Hologram} instances.
 * Provides a fluent API to configure location, viewers, and lines.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public interface HologramBuilder {

    /**
     * Sets the base location where the hologram will be spawned.
     *
     * @param location the base Location
     * @return this builder instance for chaining
     */
    HologramBuilder location(Location location);

    /**
     * Attaches a dynamic location tracker to the hologram.
     * If set, the hologram will ignore its static location and follow the tracker.
     *
     * @param tracker the LocationTracker to use
     * @return this builder instance for chaining
     */
    HologramBuilder tracker(LocationTracker tracker);

    /**
     * Restricts the visibility of this hologram to a single specific player.
     *
     * @param player the player who should see this hologram
     * @return this builder instance for chaining
     */
    HologramBuilder viewer(Player player);

    /**
     * Makes this hologram visible to all players on the server.
     *
     * @return this builder instance for chaining
     */
    HologramBuilder global();

    /**
     * Sets the maximum distance at which the hologram will be rendered for players.
     *
     * @param distance the view distance in blocks
     * @return this builder instance for chaining
     */
    HologramBuilder viewDistance(int distance);

    /**
     * Initiates the creation of a new line to be added to the hologram.
     *
     * @return a {@link HologramLineBuilder} to configure the new line
     */
    HologramLineBuilder appendLine();

    /**
     * Finalizes the builder process and creates the Hologram instance.
     *
     * @return the constructed Hologram
     */
    Hologram build();
}