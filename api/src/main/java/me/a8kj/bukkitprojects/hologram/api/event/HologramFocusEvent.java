package me.a8kj.bukkitprojects.hologram.api.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.a8kj.bukkitprojects.hologram.api.HologramLine;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Represents an event fired when a player's crosshair enters or leaves
 * the hitbox of a focusable hologram line. This is used for visual effects
 * like highlighting or scaling when a player looks at a specific line.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
@Getter
@RequiredArgsConstructor
public class HologramFocusEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    /**
     * The hologram line that the player is focusing on (or stopping focus on).
     */
    private final HologramLine line;

    /**
     * The player whose focus state changed.
     */
    private final Player player;

    /**
     * The new state of the focus.
     * True if the player just started looking at the line, false if they looked away.
     */
    private final boolean nowFocused;

    @Setter
    private boolean cancelled;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}