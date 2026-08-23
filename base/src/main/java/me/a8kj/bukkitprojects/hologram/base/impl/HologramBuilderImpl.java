package me.a8kj.bukkitprojects.hologram.base.impl;

import me.a8kj.bukkitprojects.hologram.api.Hologram;
import me.a8kj.bukkitprojects.hologram.api.HologramBuilder;
import me.a8kj.bukkitprojects.hologram.api.HologramLineBuilder;
import me.a8kj.bukkitprojects.hologram.api.tracker.LocationTracker;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of {@link HologramBuilder}.
 * Collects configuration and lines to construct a {@link HologramImpl}.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class HologramBuilderImpl implements HologramBuilder {
    private Location location;
    private LocationTracker tracker;
    private Player viewer;
    private boolean global = false;
    private int viewDistance = 48;
    private final List<HologramLineImpl> lines = new ArrayList<>();

    @Override
    public HologramBuilder location(Location location) {
        this.location = location.clone();
        return this;
    }

    @Override
    public HologramBuilder tracker(LocationTracker tracker) {
        this.tracker = tracker;
        return this;
    }

    @Override
    public HologramBuilder viewer(Player player) {
        this.viewer = player;
        this.global = false;
        return this;
    }

    @Override
    public HologramBuilder global() {
        this.global = true;
        this.viewer = null;
        return this;
    }

    @Override
    public HologramBuilder viewDistance(int distance) {
        this.viewDistance = distance;
        return this;
    }

    @Override
    public HologramLineBuilder appendLine() {
        return new HologramLineBuilderImpl(this, location, lines.size());
    }

    /**
     * Internal method used by {@link HologramLineBuilderImpl} to add configured lines.
     *
     * @param line the configured HologramLineImpl
     */
    void addLine(HologramLineImpl line) {
        lines.add(line);
    }

    @Override
    public Hologram build() {
        return new HologramImpl(location, tracker, viewer, global, viewDistance, lines);
    }
}