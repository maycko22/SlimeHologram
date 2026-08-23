package me.a8kj.bukkitprojects.hologram.constants;

/**
 * Holds constant values used throughout the SlimeHologram plugin.
 * These values define default behaviors and physical spacing rules for holograms.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public final class HologramConstants {

    /**
     * The vertical spacing (in blocks) between text lines in a hologram.
     */
    public static final double TEXT_LINE_SPACING = 0.3;

    /**
     * The vertical spacing (in blocks) between item lines in a hologram.
     * Increased slightly to prevent visual overlap with larger 3D items.
     */
    public static final double ITEM_LINE_SPACING = 0.7;

    /**
     * The default view distance (in blocks) for a newly created hologram.
     */
    public static final int DEFAULT_VIEW_DISTANCE = 48;

    /**
     * The default size of the invisible slime entity used for click hitboxes.
     */
    public static final int SLIME_HITBOX_SIZE = 1;

    /**
     * Private constructor to prevent instantiation of this constants class.
     */
    private HologramConstants() {
    }
}