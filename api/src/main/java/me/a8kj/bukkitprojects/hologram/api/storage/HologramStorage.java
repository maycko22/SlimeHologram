package me.a8kj.bukkitprojects.hologram.api.storage;

import java.util.Collection;
import java.util.Optional;

/**
 * Interface for the persistence layer responsible for saving, loading,
 * and deleting hologram data. Implementations of this interface handle
 * the actual file I/O or database queries.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public interface HologramStorage {

    /**
     * Saves or updates the given hologram data into the storage system.
     *
     * @param data the HologramData to save
     */
    void save(HologramData data);

    /**
     * Deletes a hologram from the storage system by its name.
     *
     * @param name the name of the hologram to delete
     */
    void delete(String name);

    /**
     * Loads a specific hologram's data from storage.
     *
     * @param name the name of the hologram to load
     * @return an Optional containing the HologramData if found, or empty otherwise
     */
    Optional<HologramData> load(String name);

    /**
     * Retrieves the names of all stored holograms.
     *
     * @return a collection of all hologram names
     */
    Collection<String> getAllNames();
}