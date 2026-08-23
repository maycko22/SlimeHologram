package me.a8kj.bukkitprojects.hologram.api.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.a8kj.bukkitprojects.hologram.api.Hologram;
import me.a8kj.bukkitprojects.hologram.api.HologramLine;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Represents an event fired when a new line is dynamically added to an
 * existing hologram. This allows other plugins to react to or prevent
 * line additions.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
@Getter
@RequiredArgsConstructor
public class HologramLineAddEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    /**
     * The hologram to which the line is being added.
     */
    private final Hologram hologram;

    /**
     * The line that is being added.
     */
    private final HologramLine line;

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