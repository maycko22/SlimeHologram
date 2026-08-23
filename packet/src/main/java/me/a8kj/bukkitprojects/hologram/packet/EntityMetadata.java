package me.a8kj.bukkitprojects.hologram.packet;

import lombok.Getter;

/**
 * Represents the data watcher indexes (metadata indices) for entities
 * in Minecraft 1.8.8. These indices map to specific entity properties
 * when sending packet metadata.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
@Getter
public enum EntityMetadata {

    /**
     * The status flag index (e.g., invisible, on fire).
     */
    ENTITY_STATUS(0),

    /**
     * The custom name index for the entity.
     */
    CUSTOM_NAME(2),

    /**
     * The custom name visibility flag index.
     */
    CUSTOM_NAME_VISIBLE(3),

    /**
     * The armor stand specific flags index (e.g., small, marker, no base plate).
     */
    ARMOR_STAND_FLAGS(10),

    /**
     * The slime size index.
     */
    SLIME_SIZE(16),

    /**
     * The ItemStack index for dropped item entities.
     */
    ITEM_STACK(10);

    /**
     * The numerical index used in the packet.
     */
    private final int index;

    /**
     * Constructs a new EntityMetadata enum constant.
     *
     * @param index the numerical index
     */
    EntityMetadata(int index) {
        this.index = index;
    }
}