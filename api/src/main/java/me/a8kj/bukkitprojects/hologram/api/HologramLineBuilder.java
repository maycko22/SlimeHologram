package me.a8kj.bukkitprojects.hologram.api;

import me.a8kj.bukkitprojects.hologram.api.handler.FocusHandler;
import me.a8kj.bukkitprojects.hologram.api.handler.LineEffectHandler;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;

/**
 * Builder pattern interface for constructing {@link HologramLine} instances.
 * Allows configuration of text, items, click actions, and visual effects.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public interface HologramLineBuilder {

    /**
     * Sets the text content for this line.
     *
     * @param text the text to display
     * @return this builder instance for chaining
     */
    HologramLineBuilder text(String text);

    /**
     * Sets the item to be displayed by this line.
     *
     * @param item the ItemStack to display
     * @return this builder instance for chaining
     */
    HologramLineBuilder item(ItemStack item);

    /**
     * Specifies how the item should be rendered in the world.
     *
     * @param type the ItemDisplayType (HELMET or FLOATING_3D)
     * @return this builder instance for chaining
     */
    HologramLineBuilder itemDisplayType(ItemDisplayType type);

    /**
     * Enables click interactions for this line.
     *
     * @return this builder instance for chaining
     */
    HologramLineBuilder clickable();

    /**
     * Defines the action to execute when the line is clicked.
     *
     * @param action a BiConsumer accepting the clicking player and the ClickType
     * @return this builder instance for chaining
     */
    HologramLineBuilder onClick(BiConsumer<Player, ClickType> action);

    /**
     * Attaches a focus handler to trigger effects when a player looks at the line.
     *
     * @param handler the FocusHandler to use
     * @return this builder instance for chaining
     */
    HologramLineBuilder focusHandler(FocusHandler handler);

    /**
     * Attaches an effect handler to run continuous animations (e.g., particles).
     *
     * @param handler the LineEffectHandler to use
     * @return this builder instance for chaining
     */
    HologramLineBuilder effectHandler(LineEffectHandler handler);

    /**
     * Sets the tick interval for the effect handler.
     *
     * @param interval the interval in ticks
     * @return this builder instance for chaining
     */
    HologramLineBuilder effectInterval(long interval);

    /**
     * Finalizes the line configuration and adds it to the parent hologram builder.
     *
     * @return the parent HologramBuilder instance
     */
    HologramBuilder add();
}