package me.a8kj.bukkitprojects.hologram.base.impl;

import lombok.Getter;
import me.a8kj.bukkitprojects.hologram.api.Hologram;
import me.a8kj.bukkitprojects.hologram.api.HologramSession;
import me.a8kj.bukkitprojects.hologram.packet.PacketEntityFactory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Concrete implementation of {@link HologramSession}.
 * Manages a set of holograms bound to a specific player for temporary viewing,
 * ensuring they are cleaned up properly when no longer needed.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
@Getter
public class HologramSessionImpl implements HologramSession {
    private final UUID playerUuid;
    private final java.util.Set<Hologram> holograms = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private boolean active = true;

    /**
     * Constructs a new session for the given player UUID.
     *
     * @param playerUuid the UUID of the player owning this session
     */
    public HologramSessionImpl(UUID playerUuid) {
        this.playerUuid = playerUuid;
    }

    @Override
    public List<Hologram> getHolograms() {
        return new ArrayList<>(holograms);
    }

    @Override
    public void addHologram(Hologram hologram) {
        holograms.add(hologram);
    }

    @Override
    public void removeHologram(Hologram hologram) {
        holograms.remove(hologram);
    }

    @Override
    public void destroyAll() {
        Player player = Bukkit.getPlayer(playerUuid);
        if (player == null || !player.isOnline()) {
            holograms.clear();
            active = false;
            return;
        }

        for (Hologram hologram : holograms) {
            if (!hologram.isDestroyed()) {
                hologram.despawn(player);
                hologram.destroy();
            }
        }
        holograms.clear();
        active = false;
    }
}