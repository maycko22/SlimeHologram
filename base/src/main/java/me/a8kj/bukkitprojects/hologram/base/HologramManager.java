package me.a8kj.bukkitprojects.hologram.base;

import lombok.Getter;
import me.a8kj.bukkitprojects.hologram.api.Hologram;
import me.a8kj.bukkitprojects.hologram.api.HologramBuilder;
import me.a8kj.bukkitprojects.hologram.api.HologramContext;
import me.a8kj.bukkitprojects.hologram.api.HologramLine;
import me.a8kj.bukkitprojects.hologram.api.Holograms;
import me.a8kj.bukkitprojects.hologram.api.ItemDisplayType;
import me.a8kj.bukkitprojects.hologram.api.storage.HologramData;
import me.a8kj.bukkitprojects.hologram.api.storage.HologramStorage;
import me.a8kj.bukkitprojects.hologram.base.impl.HologramLineImpl;
import me.a8kj.bukkitprojects.hologram.base.storage.SimpleHologramData;
import me.a8kj.bukkitprojects.hologram.base.task.FocusTask;
import me.a8kj.bukkitprojects.hologram.base.task.ProximityTask;
import me.a8kj.bukkitprojects.hologram.base.task.TrackerTask;
import me.a8kj.bukkitprojects.hologram.base.task.UpdateTask;
import me.a8kj.bukkitprojects.hologram.util.markdown.MarkdownParser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * Core manager for holograms, implementing {@link HologramContext}.
 * Handles registration, tracking, persistence, and dynamic modification of holograms.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class HologramManager implements HologramContext {
    private static HologramManager instance;
    private final Map<UUID, Hologram> holograms = new ConcurrentHashMap<>();
    private final Map<Integer, HologramLine> lineMap = new ConcurrentHashMap<>();

    private HologramStorage storage;
    private MarkdownParser markdownParser;

    @Getter
    private final Map<String, Hologram> persistentHolograms = new ConcurrentHashMap<>();

    private HologramManager() {
    }

    /**
     * Gets the singleton instance of the HologramManager.
     *
     * @return the HologramManager instance
     */
    public static HologramManager getInstance() {
        if (instance == null) instance = new HologramManager();
        return instance;
    }

    /**
     * Initializes the manager with required dependencies and starts background tasks.
     *
     * @param storage        the storage implementation for persistence
     * @param markdownParser the markdown parser for text formatting
     */
    public void init(HologramStorage storage, MarkdownParser markdownParser) {
        this.storage = storage;
        this.markdownParser = markdownParser;

        JavaPlugin plugin = HologramPlugin.getInstance();
        new ProximityTask(plugin).runTaskTimer(plugin, 20L, 20L);
        new TrackerTask(plugin).runTaskTimer(plugin, 0L, 1L);
        new FocusTask(plugin).runTaskTimer(plugin, 0L, 4L);
        new UpdateTask(plugin).runTaskTimer(plugin, 0L, 20L);

        loadHolograms();
    }

    /**
     * Loads all holograms from persistent storage into memory.
     */
    public void loadHolograms() {
        for (String name : storage.getAllNames()) {
            storage.load(name).ifPresent(data ->
                    createPersistentHologram(data.getName(), data.getLocation(), data.getLines(), data.isPrivate(), data.getOwner().orElse(null))
            );
        }
    }

    /**
     * Creates a new persistent hologram, saves it, and spawns it for applicable players.
     *
     * @param name      the unique name of the hologram
     * @param loc       the base location
     * @param rawLines  the raw text/item lines
     * @param isPrivate whether the hologram is private to an owner
     * @param owner     the UUID of the owner if private
     */
    public void createPersistentHologram(String name, Location loc, List<String> rawLines, boolean isPrivate, UUID owner) {
        HologramBuilder builder = Holograms.builder();
        builder.location(loc);

        if (!isPrivate) {
            builder.global();
        } else if (owner != null) {
            Player ownerPlayer = Bukkit.getPlayer(owner);
            if (ownerPlayer != null) {
                builder.viewer(ownerPlayer);
            } else {
                return;
            }
        }

        builder.viewDistance(48);

        for (String rawLine : rawLines) {
            if (rawLine.startsWith("ICON_3D:")) {
                String matName = rawLine.substring(8).trim().toUpperCase();
                Material mat = Material.matchMaterial(matName);
                if (mat != null) {
                    builder.appendLine().item(new ItemStack(mat)).itemDisplayType(ItemDisplayType.FLOATING_3D).add();
                    continue;
                }
            } else if (rawLine.startsWith("ICON:")) {
                String matName = rawLine.substring(5).trim().toUpperCase();
                Material mat = Material.matchMaterial(matName);
                if (mat != null) {
                    builder.appendLine().item(new ItemStack(mat)).itemDisplayType(ItemDisplayType.HELMET).add();
                    continue;
                }
            }
            builder.appendLine().text(markdownParser.parse(rawLine)).add();
        }

        Hologram hologram = builder.build();
        persistentHolograms.put(name, hologram);

        if (!isPrivate) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                hologram.spawn(p);
            }
        } else if (owner != null) {
            Player ownerPlayer = Bukkit.getPlayer(owner);
            if (ownerPlayer != null) {
                hologram.spawn(ownerPlayer);
            }
        }
    }

    /**
     * Deletes a persistent hologram, destroys it in-game, and removes it from storage.
     *
     * @param name the name of the hologram to delete
     */
    public void deletePersistentHologram(String name) {
        Hologram hologram = persistentHolograms.remove(name);
        if (hologram != null) {
            hologram.destroy();
            storage.delete(name);
        }
    }

    /**
     * Modifies the lines of a hologram using a consumer, validates icon limits, and reloads it.
     *
     * @param name     the name of the hologram
     * @param modifier a consumer that modifies the list of raw lines
     * @return true if the modification was successful, false if it failed validation
     */
    public boolean modifyHologram(String name, Consumer<List<String>> modifier) {
        Optional<HologramData> dataOpt = storage.load(name);
        if (dataOpt.isEmpty()) return false;
        HologramData data = dataOpt.get();

        List<String> lines = new ArrayList<>(data.getLines());
        modifier.accept(lines);

        int iconCount = 0;
        for (String l : lines) {
            if (l.startsWith("ICON:") || l.startsWith("ICON_3D:")) {
                iconCount++;
            }
        }
        if (iconCount > 1) {
            return false;
        }

        HologramData newData = new SimpleHologramData(name, data.getLocation(), lines, data.isPrivate(), data.getOwner().orElse(null));
        storage.save(newData);

        Hologram oldHolo = persistentHolograms.get(name);
        if (oldHolo != null) oldHolo.destroy();

        createPersistentHologram(name, data.getLocation(), lines, data.isPrivate(), data.getOwner().orElse(null));
        return true;
    }

    /**
     * Moves a hologram to a new location and updates storage.
     *
     * @param name   the name of the hologram
     * @param newLoc the new location
     */
    public void moveHologram(String name, Location newLoc) {
        Optional<HologramData> dataOpt = storage.load(name);
        if (dataOpt.isEmpty()) return;
        HologramData data = dataOpt.get();

        HologramData newData = new SimpleHologramData(name, newLoc, data.getLines(), data.isPrivate(), data.getOwner().orElse(null));
        storage.save(newData);

        Hologram oldHolo = persistentHolograms.get(name);
        if (oldHolo != null) oldHolo.destroy();

        createPersistentHologram(name, newLoc, data.getLines(), data.isPrivate(), data.getOwner().orElse(null));
    }

    /**
     * Copies an existing hologram to a new name and location.
     *
     * @param sourceName the name of the hologram to copy
     * @param destName   the name of the new hologram
     * @param destLoc    the location for the new hologram
     */
    public void copyHologram(String sourceName, String destName, Location destLoc) {
        Optional<HologramData> dataOpt = storage.load(sourceName);
        if (dataOpt.isEmpty()) return;
        HologramData data = dataOpt.get();

        HologramData newData = new SimpleHologramData(destName, destLoc, data.getLines(), false, null);
        storage.save(newData);
        createPersistentHologram(destName, destLoc, data.getLines(), false, null);
    }

    @Override
    public HologramLine getLineByEntityId(int entityId) {
        return lineMap.get(entityId);
    }

    /**
     * Registers an active hologram and maps its entity IDs for packet interaction.
     *
     * @param hologram the hologram to register
     */
    public void registerHologram(Hologram hologram) {
        holograms.put(hologram.getId(), hologram);
        for (HologramLine line : hologram.getLines()) {
            HologramLineImpl impl = (HologramLineImpl) line;
            lineMap.put(impl.getEntityId(), line);
            if (impl.getItemId() != -1) lineMap.put(impl.getItemId(), line);
            if (impl.getHitboxEntityId() != -1) lineMap.put(impl.getHitboxEntityId(), line);
        }
    }

    /**
     * Unregisters a hologram and removes its entity ID mappings.
     *
     * @param hologram the hologram to unregister
     */
    public void unregisterHologram(Hologram hologram) {
        holograms.remove(hologram.getId());
        for (HologramLine line : hologram.getLines()) {
            HologramLineImpl impl = (HologramLineImpl) line;
            lineMap.remove(impl.getEntityId());
            if (impl.getItemId() != -1) lineMap.remove(impl.getItemId());
            if (impl.getHitboxEntityId() != -1) lineMap.remove(impl.getHitboxEntityId());
        }
    }

    /**
     * Gets all active holograms (persistent and temporary).
     *
     * @return a collection of all holograms
     */
    public Collection<Hologram> getHolograms() {
        return holograms.values();
    }

    /**
     * Gets all persistent holograms.
     *
     * @return a collection of persistent holograms
     */
    public Collection<Hologram> getPersistentHologramsCollection() {
        return persistentHolograms.values();
    }

    /**
     * Gets a persistent hologram by name.
     *
     * @param name the name of the hologram
     * @return the hologram, or null if not found
     */
    public Hologram getPersistentHologram(String name) {
        return persistentHolograms.get(name);
    }

    /**
     * Gets the names of all persistent holograms.
     *
     * @return a set of hologram names
     */
    public Set<String> getPersistentHologramNames() {
        return persistentHolograms.keySet();
    }

    @Override
    public JavaPlugin getPlugin() {
        return HologramPlugin.getInstance();
    }
}