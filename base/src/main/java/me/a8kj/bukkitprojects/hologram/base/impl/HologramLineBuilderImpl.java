package me.a8kj.bukkitprojects.hologram.base.impl;

import lombok.RequiredArgsConstructor;
import me.a8kj.bukkitprojects.hologram.api.HologramBuilder;
import me.a8kj.bukkitprojects.hologram.api.HologramLineBuilder;
import me.a8kj.bukkitprojects.hologram.api.ClickType;
import me.a8kj.bukkitprojects.hologram.api.ItemDisplayType;
import me.a8kj.bukkitprojects.hologram.api.handler.FocusHandler;
import me.a8kj.bukkitprojects.hologram.api.handler.LineEffectHandler;
import me.a8kj.bukkitprojects.hologram.constants.HologramConstants;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;

/**
 * Concrete implementation of {@link HologramLineBuilder}.
 * Configures text, items, click actions, and effects for a single hologram line.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
@RequiredArgsConstructor
public class HologramLineBuilderImpl implements HologramLineBuilder {
    private final HologramBuilderImpl parent;
    private final Location baseLocation;
    private final int index;

    private String text;
    private ItemStack item;
    private ItemDisplayType itemDisplayType = ItemDisplayType.FLOATING_3D;
    private boolean clickable = false;
    private BiConsumer<Player, ClickType> clickAction;
    private FocusHandler focusHandler;
    private LineEffectHandler effectHandler;
    private long effectInterval = 0;

    @Override
    public HologramLineBuilder text(String text) {
        this.text = text;
        return this;
    }

    @Override
    public HologramLineBuilder item(ItemStack item) {
        this.item = item;
        return this;
    }

    @Override
    public HologramLineBuilder itemDisplayType(ItemDisplayType type) {
        this.itemDisplayType = type;
        return this;
    }

    @Override
    public HologramLineBuilder clickable() {
        this.clickable = true;
        return this;
    }

    @Override
    public HologramLineBuilder onClick(BiConsumer<Player, ClickType> action) {
        this.clickAction = action;
        this.clickable = true;
        return this;
    }

    @Override
    public HologramLineBuilder focusHandler(FocusHandler handler) {
        this.focusHandler = handler;
        this.clickable = true;
        return this;
    }

    @Override
    public HologramLineBuilder effectHandler(LineEffectHandler handler) {
        this.effectHandler = handler;
        return this;
    }

    @Override
    public HologramLineBuilder effectInterval(long interval) {
        this.effectInterval = interval;
        return this;
    }

    @Override
    public HologramBuilder add() {
        double offset = index * (item != null ? HologramConstants.ITEM_LINE_SPACING : HologramConstants.TEXT_LINE_SPACING);
        Location lineLoc = baseLocation.clone().subtract(0, offset, 0);
        parent.addLine(new HologramLineImpl(text, item, itemDisplayType, clickable, clickAction, focusHandler, effectHandler, effectInterval, lineLoc));
        return parent;
    }
}