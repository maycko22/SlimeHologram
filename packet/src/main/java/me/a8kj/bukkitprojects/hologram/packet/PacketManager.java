package me.a8kj.bukkitprojects.hologram.packet;

import lombok.Getter;
import me.a8kj.bukkitprojects.hologram.api.HologramContext;

/**
 * Manager class for holding the static {@link HologramContext} instance.
 * This allows packet listeners to access hologram data without direct plugin references.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class PacketManager {

    @Getter
    private static HologramContext context;

    /**
     * Sets the hologram context.
     *
     * @param context the context to set
     */
    public static void setContext(HologramContext context) {
        PacketManager.context = context;
    }
}