package me.a8kj.bukkitprojects.hologram.base.storage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.bukkitprojects.hologram.api.storage.HologramData;
import org.bukkit.Location;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Simple implementation of {@link HologramData} used as a data transfer object (DTO).
 * Holds the necessary properties for a hologram's state.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
@Getter
@RequiredArgsConstructor
public class SimpleHologramData implements HologramData {

    /**
     * The unique name identifier of the hologram.
     */
    private final String name;

    /**
     * The base spawn location of the hologram in the world.
     */
    private final Location location;

    /**
     * The raw text lines configured for this hologram.
     */
    private final List<String> lines;

    /**
     * Whether this hologram is private (client-side).
     */
    private final boolean isPrivate;

    /**
     * The UUID of the owner if the hologram is private.
     */
    private final UUID owner;

    @Override
    public Optional<UUID> getOwner() {
        return Optional.ofNullable(owner);
    }
}