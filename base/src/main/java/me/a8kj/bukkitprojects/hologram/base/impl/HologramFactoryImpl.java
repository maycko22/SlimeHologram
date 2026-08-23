package me.a8kj.bukkitprojects.hologram.base.impl;

import me.a8kj.bukkitprojects.hologram.api.HologramBuilder;
import me.a8kj.bukkitprojects.hologram.api.HologramFactory;

/**
 * Concrete implementation of {@link HologramFactory}.
 * Provides new instances of {@link HologramBuilderImpl}.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class HologramFactoryImpl implements HologramFactory {

    @Override
    public HologramBuilder builder() {
        return new HologramBuilderImpl();
    }
}