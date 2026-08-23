package me.a8kj.bukkitprojects.hologram.packet;

import lombok.Getter;

/**
 * Represents the bitmask flags used in entity metadata packets.
 * These flags determine the visual and physical state of entities like ArmorStands and Slimes.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
@Getter
public enum EntityFlag {

    /**
     * Makes the entity completely invisible (used for text lines and hitboxes).
     */
    INVISIBLE((byte) 0x20),

    /**
     * Makes the entity visible (used for item lines to ensure the item renders).
     */
    VISIBLE((byte) 0x00),

    /**
     * Makes the custom name always visible.
     */
    CUSTOM_NAME_VISIBLE((byte) 0x01),

    /**
     * Armor stand flag: Small + NoBasePlate + Marker.
     * Hides the body completely while keeping the custom name visible.
     */
    ARMOR_STAND_MARKER_SMALL((byte) 0x0D),

    /**
     * Armor stand flag: Small + NoBasePlate.
     * Hides the base plate but keeps the body, allowing passengers to ride it.
     */
    ARMOR_STAND_ITEM_SMALL((byte) 0x05),

    /**
     * Slime flag: Sets the slime size to 1 (small hitbox).
     */
    SLIME_SIZE_1((byte) 1);

    /**
     * The byte value of the flag.
     */
    private final byte value;

    /**
     * Constructs a new EntityFlag enum constant.
     *
     * @param value the byte value
     */
    EntityFlag(byte value) {
        this.value = value;
    }
}