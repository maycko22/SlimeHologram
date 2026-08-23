package me.a8kj.bukkitprojects.hologram.api;

/**
 * Factory interface for creating new instances of {@link HologramBuilder}.
 * This abstracts the construction process away from the API module.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public interface HologramFactory {

    /**
     * Creates and returns a new HologramBuilder.
     *
     * @return a new HologramBuilder instance
     */
    HologramBuilder builder();
}