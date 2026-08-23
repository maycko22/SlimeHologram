package me.a8kj.bukkitprojects.hologram.base;

import me.a8kj.bukkitprojects.hologram.api.Hologram;
import me.a8kj.bukkitprojects.hologram.api.HologramSession;
import me.a8kj.bukkitprojects.hologram.api.event.HologramSessionEndEvent;
import me.a8kj.bukkitprojects.hologram.base.impl.HologramSessionImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages player-bound hologram sessions to ensure temporary holograms
 * are cleaned up when a player disconnects, dies, or changes worlds.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class SessionManager implements Listener {
    private static SessionManager instance;
    private final Map<UUID, HologramSession> sessions = new ConcurrentHashMap<>();

    private SessionManager() {}

    /**
     * Gets the singleton instance of the SessionManager, registering events if necessary.
     *
     * @return the SessionManager instance
     */
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
            Bukkit.getPluginManager().registerEvents(instance, HologramPlugin.getInstance());
        }
        return instance;
    }

    /**
     * Gets or creates a hologram session for a specific player.
     *
     * @param player the player
     * @return the player's HologramSession
     */
    public HologramSession getOrCreateSession(Player player) {
        return sessions.computeIfAbsent(player.getUniqueId(), uuid -> new HologramSessionImpl(uuid));
    }

    /**
     * Gets the hologram session for a player, if it exists.
     *
     * @param player the player
     * @return an Optional containing the session, or empty
     */
    public Optional<HologramSession> getSession(Player player) {
        return Optional.ofNullable(sessions.get(player.getUniqueId()));
    }

    /**
     * Ends a player's session, firing an event and destroying all attached holograms.
     *
     * @param uuid the UUID of the player whose session is ending
     */
    public void endSession(UUID uuid) {
        HologramSession session = sessions.remove(uuid);
        if (session != null && session.isActive()) {
            Bukkit.getPluginManager().callEvent(new HologramSessionEndEvent(uuid, session.getHolograms()));
            session.destroyAll();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        endSession(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent event) {
        endSession(event.getEntity().getUniqueId());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldChange(PlayerChangedWorldEvent event) {
        endSession(event.getPlayer().getUniqueId());
    }
}