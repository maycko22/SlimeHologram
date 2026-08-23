package me.a8kj.bukkitprojects.hologram.api.handler;

import me.a8kj.bukkitprojects.hologram.api.HologramLine;
import org.bukkit.entity.Player;

/**
 * Interface for handling focus (hover) events on a specific hologram line.
 * This allows developers to create interactive visual effects when a player
 * looks directly at a hologram line.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public interface FocusHandler {

    /**
     * Called once when the player's crosshair enters the hitbox of the hologram line.
     *
     * @param viewer the player who is focusing on the line
     * @param line   the hologram line that is being focused on
     */
    void onFocus(Player viewer, HologramLine line);

    /**
     * Called continuously (every few ticks) while the player remains focused on the line.
     * Ideal for updating dynamic effects like scaling or particle rotations.
     *
     * @param viewer the player who is focusing on the line
     * @param line   the hologram line being focused on
     * @param tick   the current server tick count, useful for animation timing
     */
    void onTickFocus(Player viewer, HologramLine line, int tick);

    /**
     * Called once when the player's crosshair leaves the hitbox of the hologram line.
     *
     * @param viewer the player who stopped focusing on the line
     * @param line   the hologram line that is no longer focused on
     */
    void onUnfocus(Player viewer, HologramLine line);
}