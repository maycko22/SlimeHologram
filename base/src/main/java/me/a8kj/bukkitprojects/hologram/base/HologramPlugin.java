package me.a8kj.bukkitprojects.hologram.base;

import com.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import me.a8kj.bukkitprojects.hologram.api.Hologram;
import me.a8kj.bukkitprojects.hologram.api.Holograms;
import me.a8kj.bukkitprojects.hologram.api.storage.HologramStorage;
import me.a8kj.bukkitprojects.hologram.base.command.CommandDispatcher;
import me.a8kj.bukkitprojects.hologram.base.command.HologramTestCommand;
import me.a8kj.bukkitprojects.hologram.base.command.impl.CreateCommand;
import me.a8kj.bukkitprojects.hologram.base.command.impl.*;
import me.a8kj.bukkitprojects.hologram.base.command.impl.HelpCommand;
import me.a8kj.bukkitprojects.hologram.base.impl.HologramFactoryImpl;
import me.a8kj.bukkitprojects.hologram.base.storage.YamlHologramStorage;
import me.a8kj.bukkitprojects.hologram.packet.PacketListener;
import me.a8kj.bukkitprojects.hologram.packet.PacketManager;
import me.a8kj.bukkitprojects.hologram.util.markdown.MarkdownParser;
import me.a8kj.bukkitprojects.hologram.util.markdown.MarkdownRules;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Main plugin class for SlimeHologram.
 * Handles initialization of PacketEvents, dependency injection, core engine setup,
 * and command registration.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class HologramPlugin extends JavaPlugin {
    @Getter
    private static HologramPlugin instance;

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void onEnable() {
        instance = this;

        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
        PacketEvents.getAPI().getSettings().reEncodeByDefault(false).checkForUpdates(false);
        PacketEvents.getAPI().init();
        PacketEvents.getAPI().getEventManager().registerListener(new PacketListener());

        HologramStorage storage = new YamlHologramStorage(new File(getDataFolder(), "holograms.yml"));
        MarkdownParser markdownParser = new MarkdownParser(MarkdownRules.defaults());

        Holograms.setFactory(new HologramFactoryImpl());
        PacketManager.setContext(HologramManager.getInstance());
        HologramManager.getInstance().init(storage, markdownParser);

        CommandDispatcher dispatcher = new CommandDispatcher();
        dispatcher.registerCommand(new HelpCommand());
        dispatcher.registerCommand(new CreateCommand(storage, markdownParser));
        dispatcher.registerCommand(new EditCommand());
        dispatcher.registerCommand(new ListCommand());
        dispatcher.registerCommand(new DeleteCommand());
        dispatcher.registerCommand(new ReloadCommand());
        dispatcher.registerCommand(new AddLineCommand());
        dispatcher.registerCommand(new RemoveLineCommand());
        dispatcher.registerCommand(new SetLineCommand());
        dispatcher.registerCommand(new InsertLineCommand());
        dispatcher.registerCommand(new MoveHereCommand());
        dispatcher.registerCommand(new TeleportCommand());
        dispatcher.registerCommand(new AlignCommand());
        dispatcher.registerCommand(new CopyCommand());

        getCommand("sh").setExecutor(dispatcher);
        getCommand("sh").setTabCompleter(dispatcher);

        getCommand("holotest").setExecutor(new HologramTestCommand());
    }

    @Override
    public void onDisable() {
        HologramManager.getInstance().getHolograms().forEach(Hologram::destroy);
        if (PacketEvents.getAPI() != null) PacketEvents.getAPI().terminate();
    }
}