package me.a8kj.bukkitprojects.hologram.base.storage;

import me.a8kj.bukkitprojects.hologram.api.storage.HologramData;
import me.a8kj.bukkitprojects.hologram.api.storage.HologramStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * YAML-based implementation of {@link HologramStorage}.
 * Handles saving, loading, and deleting hologram data to and from a YAML file.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class YamlHologramStorage implements HologramStorage {

    /**
     * The YAML file where hologram data is stored.
     */
    private final File file;

    /**
     * The FileConfiguration object representing the loaded YAML file.
     */
    private FileConfiguration config;

    /**
     * Constructs a new YamlHologramStorage and loads the configuration from the specified file.
     * If the file does not exist, it will be created.
     *
     * @param file the YAML file to use for storage
     */
    public YamlHologramStorage(File file) {
        this.file = file;
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public void save(HologramData data) {
        config.set("holograms." + data.getName() + ".location", serializeLoc(data.getLocation()));
        config.set("holograms." + data.getName() + ".lines", data.getLines());
        config.set("holograms." + data.getName() + ".private", data.isPrivate());
        config.set("holograms." + data.getName() + ".owner", data.getOwner().map(UUID::toString).orElse(null));
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String name) {
        config.set("holograms." + name, null);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<HologramData> load(String name) {
        if (!config.contains("holograms." + name)) return Optional.empty();
        Location loc = deserializeLoc(config.getString("holograms." + name + ".location"));
        List<String> lines = config.getStringList("holograms." + name + ".lines");
        boolean isPrivate = config.getBoolean("holograms." + name + ".private", false);
        String ownerStr = config.getString("holograms." + name + ".owner");
        UUID owner = ownerStr != null ? UUID.fromString(ownerStr) : null;
        return Optional.of(new SimpleHologramData(name, loc, lines, isPrivate, owner));
    }

    @Override
    public Collection<String> getAllNames() {
        if (config.getConfigurationSection("holograms") == null) return Collections.emptyList();
        return config.getConfigurationSection("holograms").getKeys(false);
    }

    /**
     * Serializes a Location object into a string format for YAML storage.
     *
     * @param loc the Location to serialize
     * @return a comma-separated string representing the location (world,x,y,z)
     */
    private String serializeLoc(Location loc) {
        return loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ();
    }

    /**
     * Deserializes a string back into a Location object.
     *
     * @param s the comma-separated string representing the location
     * @return the reconstructed Location object
     */
    private Location deserializeLoc(String s) {
        String[] p = s.split(",");
        return new Location(Bukkit.getWorld(p[0]), Double.parseDouble(p[1]), Double.parseDouble(p[2]), Double.parseDouble(p[3]));
    }
}