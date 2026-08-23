package me.a8kj.bukkitprojects.hologram.api;

/**
 * Defines the visual rendering behavior for item lines in a hologram.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public enum ItemDisplayType {

    /**
     * Renders the item as a helmet on an invisible armor stand.
     * The item remains static and does not rotate. Ideal for custom heads or fixed icons.
     */
    HELMET,

    /**
     * Renders the item as a dropped item entity mounted on an invisible armor stand.
     * The item will float and rotate naturally in 3D space. Ideal for diamonds and rewards.
     */
    FLOATING_3D
}