package me.a8kj.bukkitprojects.hologram.api.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.bukkitprojects.hologram.api.Hologram;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Represents an event fired when a hologram is permanently destroyed and
 * removed from the registry. This occurs when the {@link Hologram#destroy()}
 * method is invoked.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
@Getter
@RequiredArgsConstructor
public class HologramDestroyEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    /**
     * The hologram instance that was destroyed.
     */
    private final Hologram hologram;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}