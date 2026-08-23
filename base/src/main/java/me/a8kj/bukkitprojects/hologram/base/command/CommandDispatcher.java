package me.a8kj.bukkitprojects.hologram.base.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Routes command execution and tab-completion to registered {@link SubCommand} instances.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class CommandDispatcher implements CommandExecutor, TabCompleter {
    private final Map<String, SubCommand> subCommands = new HashMap<>();

    /**
     * Registers a sub-command to be handled by this dispatcher.
     *
     * @param subCommand the sub-command to register
     */
    public void registerCommand(SubCommand subCommand) {
        subCommands.put(subCommand.getName().toLowerCase(), subCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            subCommands.get("help").execute(sender, args);
            return true;
        }
        SubCommand subCommand = subCommands.get(args[0].toLowerCase());
        if (subCommand != null) {
            if (!sender.hasPermission(subCommand.getPermission())) {
                sender.sendMessage("§cNo permission.");
                return true;
            }
            subCommand.execute(sender, Arrays.copyOfRange(args, 1, args.length));
        } else {
            sender.sendMessage("§cUnknown command. Type /hd help");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return subCommands.keySet().stream()
                    .filter(s -> s.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        SubCommand subCommand = subCommands.get(args[0].toLowerCase());
        if (subCommand != null) {
            return subCommand.tabComplete(sender, Arrays.copyOfRange(args, 1, args.length));
        }
        return Collections.emptyList();
    }
}