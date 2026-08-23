package me.a8kj.bukkitprojects.hologram.api;

import me.a8kj.bukkitprojects.hologram.api.handler.FocusHandler;
import me.a8kj.bukkitprojects.hologram.api.handler.LineEffectHandler;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * Represents a single line within a {@link Hologram}.
 * A line can contain either text, an item, or both (if clickable).
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public interface HologramLine {

    /**
     * Gets the primary packet entity ID associated with this line.
     *
     * @return the primary entity ID
     */
    int getEntityId();

    /**
     * Gets the entity ID of the invisible slime hitbox used for click detection,
     * if this line is clickable.
     *
     * @return the hitbox entity ID, or -1 if not clickable
     */
    int getHitboxEntityId();

    /**
     * Gets the text content of this line, if it is a text line.
     *
     * @return an Optional containing the text, or empty if it is an item line
     */
    Optional<String> getText();

    /**
     * Gets the item displayed by this line, if it is an item line.
     *
     * @return an Optional containing the ItemStack, or empty if it is a text line
     */
    Optional<ItemStack> getItem();

    /**
     * Checks if this line has click interactions enabled.
     *
     * @return true if clickable, false otherwise
     */
    boolean isClickable();

    /**
     * Gets the physical location where this specific line is rendered.
     *
     * @return the Location of this line
     */
    Location getLocation();

    /**
     * Gets the action to perform when this line is clicked, if applicable.
     *
     * @return an Optional containing the BiConsumer click action, or empty
     */
    Optional<BiConsumer<Player, ClickType>> getClickAction();

    /**
     * Gets the focus handler that defines behavior when a player looks at this line,
     * if applicable.
     *
     * @return an Optional containing the FocusHandler, or empty
     */
    Optional<FocusHandler> getFocusHandler();

    /**
     * Gets the effect handler that defines continuous tick-based effects (e.g., particles),
     * if applicable.
     *
     * @return an Optional containing the LineEffectHandler, or empty
     */
    Optional<LineEffectHandler> getEffectHandler();

    /**
     * Gets the interval (in ticks) at which the effect handler should fire.
     *
     * @return the effect interval in ticks
     */
    long getEffectInterval();

    /**
     * Sends the spawn packets for this line to a specific player.
     *
     * @param player the player to spawn the line for
     */
    void spawn(Player player);

    /**
     * Sends the destroy packets for this line to a specific player.
     *
     * @param player the player to despawn the line for
     */
    void despawn(Player player);
}