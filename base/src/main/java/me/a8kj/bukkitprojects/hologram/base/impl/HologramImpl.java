package me.a8kj.bukkitprojects.hologram.base.impl;

import lombok.Getter;
import me.a8kj.bukkitprojects.hologram.api.Hologram;
import me.a8kj.bukkitprojects.hologram.api.HologramLine;
import me.a8kj.bukkitprojects.hologram.api.event.HologramDestroyEvent;
import me.a8kj.bukkitprojects.hologram.api.event.HologramSpawnEvent;
import me.a8kj.bukkitprojects.hologram.api.tracker.LocationTracker;
import me.a8kj.bukkitprojects.hologram.base.HologramManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Concrete implementation of {@link Hologram}.
 * Manages the state, spawning, and despawning of a hologram and its lines.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
@Getter
public class HologramImpl implements Hologram {
    private final UUID id;
    private Location location;
    private final LocationTracker tracker;
    private final Player viewer;
    private final boolean global;
    private final int viewDistance;
    private final List<HologramLine> lines;
    private final List<Player> shownTo;
    private boolean destroyed = false;

    /**
     * Constructs a new HologramImpl and registers it with the {@link HologramManager}.
     */
    public HologramImpl(Location location, LocationTracker tracker, Player viewer, boolean global, int viewDistance, List<HologramLineImpl> lines) {
        this.id = UUID.randomUUID();
        this.location = location;
        this.tracker = tracker;
        this.viewer = viewer;
        this.global = global;
        this.viewDistance = viewDistance;
        this.lines = Collections.unmodifiableList(new ArrayList<>(lines));
        this.shownTo = new ArrayList<>();

        HologramManager.getInstance().registerHologram(this);
    }

    @Override
    public Optional<Player> getViewer() {
        return Optional.ofNullable(viewer);
    }

    @Override
    public Optional<LocationTracker> getTracker() {
        return Optional.ofNullable(tracker);
    }

    @Override
    public void spawn(Player player) {
        if (destroyed) return;
        if (!global && viewer != null && !viewer.equals(player)) return;

        HologramSpawnEvent event = new HologramSpawnEvent(this, player);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;

        for (HologramLine line : lines) {
            ((HologramLineImpl) line).spawn(player);
        }
        if (!shownTo.contains(player)) {
            shownTo.add(player);
        }
    }

    @Override
    public void despawn(Player player) {
        for (HologramLine line : lines) {
            ((HologramLineImpl) line).despawn(player);
        }
        shownTo.remove(player);
    }

    @Override
    public void despawnAll() {
        for (Player player : new ArrayList<>(shownTo)) {
            despawn(player);
        }
    }

    @Override
    public void destroy() {
        if (destroyed) return;
        despawnAll();
        destroyed = true;
        Bukkit.getPluginManager().callEvent(new HologramDestroyEvent(this));
        HologramManager.getInstance().unregisterHologram(this);
    }
}