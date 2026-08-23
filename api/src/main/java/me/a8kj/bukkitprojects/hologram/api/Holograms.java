package me.a8kj.bukkitprojects.hologram.api;

/**
 * Static entry point for the SlimeHologram API.
 * Provides a simplified way to access the {@link HologramBuilder}.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public final class Holograms {

    private static HologramFactory factory;

    /**
     * Internal method used during plugin initialization to set the factory implementation.
     *
     * @param factory the HologramFactory implementation
     */
    public static void setFactory(HologramFactory factory) {
        Holograms.factory = factory;
    }

    /**
     * Creates a new HologramBuilder instance to start constructing a hologram.
     *
     * @return a new HologramBuilder
     * @throws IllegalStateException if the API has not been initialized (plugin not enabled)
     */
    public static HologramBuilder builder() {
        if (factory == null) {
            throw new IllegalStateException("Hologram factory has not been initialized! Ensure the plugin is enabled.");
        }
        return factory.builder();
    }
}