package me.a8kj.bukkitprojects.hologram.base.command;

import org.bukkit.command.CommandSender;
import java.util.List;

/**
 * Interface for modular sub-commands handled by the {@link CommandDispatcher}.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public interface SubCommand {

    /**
     * Gets the name of the sub-command.
     *
     * @return the command name
     */
    String getName();

    /**
     * Gets the permission required to execute this command.
     *
     * @return the permission string
     */
    String getPermission();

    /**
     * Executes the sub-command logic.
     *
     * @param sender the command sender
     * @param args   the arguments passed to the command
     */
    void execute(CommandSender sender, String[] args);

    /**
     * Provides tab-completion suggestions for the command.
     *
     * @param sender the command sender
     * @param args   the current arguments
     * @return a list of tab-completion suggestions
     */
    List<String> tabComplete(CommandSender sender, String[] args);
}