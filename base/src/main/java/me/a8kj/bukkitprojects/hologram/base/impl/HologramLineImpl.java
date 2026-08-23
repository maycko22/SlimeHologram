package me.a8kj.bukkitprojects.hologram.base.impl;

import lombok.Getter;
import me.a8kj.bukkitprojects.hologram.api.ClickType;
import me.a8kj.bukkitprojects.hologram.api.HologramLine;
import me.a8kj.bukkitprojects.hologram.api.ItemDisplayType;
import me.a8kj.bukkitprojects.hologram.api.handler.FocusHandler;
import me.a8kj.bukkitprojects.hologram.api.handler.LineEffectHandler;
import me.a8kj.bukkitprojects.hologram.packet.PacketEntityFactory;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * Concrete implementation of {@link HologramLine}.
 * Represents a single visual line (text or item) in a hologram and manages its packet-level entity IDs.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
@Getter
public class HologramLineImpl implements HologramLine {
    private final int entityId;
    private final int itemId;
    private final int hitboxEntityId;
    private final String text;
    private final ItemStack item;
    private final ItemDisplayType itemDisplayType;
    private final boolean clickable;
    private final BiConsumer<Player, ClickType> clickAction;
    private final FocusHandler focusHandler;
    private final LineEffectHandler effectHandler;
    private final long effectInterval;
    private final Location location;

    /**
     * Constructs a new line, allocating packet entity IDs as needed based on configuration.
     */
    public HologramLineImpl(String text, ItemStack item, ItemDisplayType itemDisplayType, boolean clickable, BiConsumer<Player, ClickType> clickAction,
                            FocusHandler focusHandler, LineEffectHandler effectHandler, long effectInterval, Location location) {
        this.entityId = PacketEntityFactory.nextEntityId();
        this.itemId = (item != null && itemDisplayType == ItemDisplayType.FLOATING_3D) ? PacketEntityFactory.nextEntityId() : -1;
        this.hitboxEntityId = clickable ? PacketEntityFactory.nextEntityId() : -1;
        this.text = text;
        this.item = item;
        this.itemDisplayType = itemDisplayType;
        this.clickable = clickable;
        this.clickAction = clickAction;
        this.focusHandler = focusHandler;
        this.effectHandler = effectHandler;
        this.effectInterval = effectInterval;
        this.location = location;
    }

    @Override
    public Optional<String> getText() { return Optional.ofNullable(text); }

    @Override
    public Optional<ItemStack> getItem() { return Optional.ofNullable(item); }

    @Override
    public Optional<BiConsumer<Player, ClickType>> getClickAction() { return Optional.ofNullable(clickAction); }

    @Override
    public Optional<FocusHandler> getFocusHandler() { return Optional.ofNullable(focusHandler); }

    @Override
    public Optional<LineEffectHandler> getEffectHandler() { return Optional.ofNullable(effectHandler); }

    @Override
    public void spawn(Player player) {
        if (text != null) {
            PacketEntityFactory.sendSpawnTextArmorStand(player, entityId, location, text);
        } else if (item != null) {
            if (itemDisplayType == ItemDisplayType.HELMET) {
                PacketEntityFactory.sendSpawnItemHelmet(player, entityId, location, item);
            } else {
                PacketEntityFactory.sendSpawn3DItemHologram(player, entityId, itemId, location, item);
            }
        }
        if (clickable) {
            PacketEntityFactory.sendSpawnSlimeHitbox(player, hitboxEntityId, location);
        }
    }

    @Override
    public void despawn(Player player) {
        if (clickable && itemId != -1) {
            PacketEntityFactory.sendDestroyEntities(player, entityId, itemId, hitboxEntityId);
        } else if (clickable) {
            PacketEntityFactory.sendDestroyEntities(player, entityId, hitboxEntityId);
        } else if (itemId != -1) {
            PacketEntityFactory.sendDestroyEntities(player, entityId, itemId);
        } else {
            PacketEntityFactory.sendDestroyEntities(player, entityId);
        }
    }
}