package me.a8kj.bukkitprojects.hologram.api.handler;

import me.a8kj.bukkitprojects.hologram.api.HologramLine;
import org.bukkit.entity.Player;

/**
 * Interface for handling continuous tick-based visual or logical effects
 * on a hologram line. Unlike focus handlers, these run continuously regardless
 * of player interaction, which is perfect for ambient animations like floating
 * particles or mathematical movement.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public interface LineEffectHandler {

    /**
     * Called at the configured interval to apply effects to the line.
     *
     * @param viewer the player viewing the hologram
     * @param line   the hologram line to apply the effect to
     * @param tick   the current server tick count, useful for animation timing
     */
    void onTick(Player viewer, HologramLine line, int tick);
}