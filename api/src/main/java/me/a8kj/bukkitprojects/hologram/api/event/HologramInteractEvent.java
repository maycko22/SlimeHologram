package me.a8kj.bukkitprojects.hologram.api.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.a8kj.bukkitprojects.hologram.api.ClickType;
import me.a8kj.bukkitprojects.hologram.api.HologramLine;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Represents an event fired when a player clicks (interacts with) a
 * clickable hologram line. This event can be cancelled to prevent the
 * associated click action from executing.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
@Getter
@RequiredArgsConstructor
public class HologramInteractEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    /**
     * The hologram line that was clicked.
     */
    private final HologramLine line;

    /**
     * The player who performed the click.
     */
    private final Player player;

    /**
     * The type of click performed (LEFT, RIGHT, SHIFT).
     */
    private final ClickType clickType;

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