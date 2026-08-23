package me.a8kj.bukkitprojects.hologram.api;

/**
 * Represents the type of click a player can perform on an interactive hologram line.
 * This is used in click event handling to determine the specific action taken by the player.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public enum ClickType {
    /**
     * Represents a left-click (attack action) performed by the player.
     */
    LEFT_CLICK,

    /**
     * Represents a right-click (interact action) performed by the player.
     */
    RIGHT_CLICK,

    /**
     * Represents a shift-click action performed by the player.
     */
    SHIFT_CLICK
}