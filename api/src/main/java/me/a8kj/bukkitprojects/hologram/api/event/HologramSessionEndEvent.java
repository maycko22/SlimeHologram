package me.a8kj.bukkitprojects.hologram.api.event;

import lombok.Getter;
import me.a8kj.bukkitprojects.hologram.api.Hologram;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Represents an event fired when a player's hologram session ends.
 * This typically occurs when the player disconnects, changes worlds, or dies.
 * All holograms attached to this session are about to be destroyed.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
@Getter
public class HologramSessionEndEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    /**
     * The UUID of the player whose session is ending.
     */
    private final UUID playerUuid;

    /**
     * An unmodifiable list of holograms that were attached to this session
     * before it was terminated.
     */
    private final List<Hologram> holograms;

    public HologramSessionEndEvent(UUID playerUuid, List<Hologram> holograms) {
        this.playerUuid = playerUuid;
        this.holograms = List.copyOf(holograms);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}