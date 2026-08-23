package me.a8kj.bukkitprojects.hologram.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Utility class for creating custom player heads from Base64 texture strings.
 * Uses reflection to bypass the standard Bukkit API limitations in legacy versions.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class HeadUtil {

    /**
     * Creates a player head ItemStack with a custom texture applied from a Base64 string.
     *
     * @param base64 the Base64 encoded texture URL string
     * @return the configured ItemStack representing the custom player head
     * @throws RuntimeException if the reflection required to set the skull profile fails
     */
    public static ItemStack fromBase64(String base64) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", base64));

        try {
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set skull profile via reflection", e);
        }

        item.setItemMeta(meta);
        return item;
    }
}