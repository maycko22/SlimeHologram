package me.a8kj.bukkitprojects.hologram.api.event;

import lombok.RequiredArgsConstructor;
import me.a8kj.bukkitprojects.hologram.api.Hologram;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an event fired when a hologram is about to be spawned and
 * rendered for a specific player. This can be cancelled to prevent the
 * hologram from appearing for that particular player.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
@Getter
@RequiredArgsConstructor
public class HologramSpawnEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    /**
     * The hologram that is being spawned.
     */
    private final Hologram hologram;

    /**
     * The player for whom the hologram is being rendered.
     */
    private final Player viewer;

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