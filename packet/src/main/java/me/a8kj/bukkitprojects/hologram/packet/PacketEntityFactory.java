package me.a8kj.bukkitprojects.hologram.packet;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.player.Equipment;
import com.github.retrooper.packetevents.protocol.player.EquipmentSlot;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerAttachEntity;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityEquipment;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityVelocity;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnLivingEntity;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Factory class responsible for creating and sending packet-level entities
 * to players. This handles the spawning of invisible ArmorStands for text,
 * items, hitboxes, and their subsequent destruction.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class PacketEntityFactory {

    private static final AtomicInteger ENTITY_ID_PROVIDER = new AtomicInteger(30000);

    /**
     * Generates a unique entity ID for fake packet entities.
     *
     * @return the next available entity ID
     */
    public static int nextEntityId() {
        return ENTITY_ID_PROVIDER.getAndIncrement();
    }

    /**
     * Spawns an invisible ArmorStand to display a text line.
     *
     * @param player   the player to send the packet to
     * @param entityId the entity ID of the ArmorStand
     * @param location the location to spawn at
     * @param text     the text to display
     */
    public static void sendSpawnTextArmorStand(Player player, int entityId, Location location, String text) {
        List<EntityData<?>> metadata = Arrays.asList(
                new EntityData<>(EntityMetadata.ENTITY_STATUS.getIndex(), EntityDataTypes.BYTE, EntityFlag.INVISIBLE.getValue()),
                new EntityData<>(EntityMetadata.CUSTOM_NAME.getIndex(), EntityDataTypes.STRING, text),
                new EntityData<>(EntityMetadata.CUSTOM_NAME_VISIBLE.getIndex(), EntityDataTypes.BYTE, EntityFlag.CUSTOM_NAME_VISIBLE.getValue()),
                new EntityData<>(EntityMetadata.ARMOR_STAND_FLAGS.getIndex(), EntityDataTypes.BYTE, EntityFlag.ARMOR_STAND_MARKER_SMALL.getValue())
        );

        WrapperPlayServerSpawnLivingEntity spawnPacket = new WrapperPlayServerSpawnLivingEntity(
                entityId, UUID.randomUUID(), EntityTypes.ARMOR_STAND,
                new Vector3d(location.getX(), location.getY(), location.getZ()),
                0f, 0f, 0f, new Vector3d(0, 0, 0), metadata
        );
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, spawnPacket);
    }

    /**
     * Spawns an invisible ArmorStand with an item equipped as a helmet.
     * The item remains static and does not rotate.
     *
     * @param player    the player to send the packet to
     * @param entityId  the entity ID of the ArmorStand
     * @param location  the location to spawn at
     * @param itemStack the item to display
     */
    public static void sendSpawnItemHelmet(Player player, int entityId, Location location, org.bukkit.inventory.ItemStack itemStack) {
        List<EntityData<?>> metadata = Arrays.asList(
                new EntityData<>(EntityMetadata.ENTITY_STATUS.getIndex(), EntityDataTypes.BYTE, EntityFlag.INVISIBLE.getValue()),
                new EntityData<>(EntityMetadata.ARMOR_STAND_FLAGS.getIndex(), EntityDataTypes.BYTE, EntityFlag.ARMOR_STAND_ITEM_SMALL.getValue())
        );

        WrapperPlayServerSpawnLivingEntity spawnPacket = new WrapperPlayServerSpawnLivingEntity(
                entityId, UUID.randomUUID(), EntityTypes.ARMOR_STAND,
                new Vector3d(location.getX(), location.getY(), location.getZ()),
                0f, 0f, 0f, new Vector3d(0, 0, 0), metadata
        );
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, spawnPacket);

        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(PacketEntityFactory.class);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (player.isOnline()) {
                ItemStack peItem = SpigotConversionUtil.fromBukkitItemStack(itemStack);
                List<Equipment> equipmentList = Collections.singletonList(new Equipment(EquipmentSlot.HELMET, peItem));
                WrapperPlayServerEntityEquipment equipPacket = new WrapperPlayServerEntityEquipment(entityId, equipmentList);
                PacketEvents.getAPI().getPlayerManager().sendPacket(player, equipPacket);
            }
        }, 1L);
    }

    /**
     * Spawns a floating, rotating 3D item hologram.
     * Uses a dropped item entity mounted on an invisible ArmorStand.
     *
     * @param player    the player to send the packet to
     * @param standId   the entity ID of the mount ArmorStand
     * @param itemId    the entity ID of the dropped item
     * @param location  the location to spawn at
     * @param itemStack the item to display
     */
    public static void sendSpawn3DItemHologram(Player player, int standId, int itemId, Location location, org.bukkit.inventory.ItemStack itemStack) {
        List<EntityData<?>> standMeta = Arrays.asList(
                new EntityData<>(EntityMetadata.ENTITY_STATUS.getIndex(), EntityDataTypes.BYTE, EntityFlag.INVISIBLE.getValue()),
                new EntityData<>(EntityMetadata.ARMOR_STAND_FLAGS.getIndex(), EntityDataTypes.BYTE, EntityFlag.ARMOR_STAND_ITEM_SMALL.getValue())
        );
        WrapperPlayServerSpawnLivingEntity standSpawn = new WrapperPlayServerSpawnLivingEntity(
                standId, UUID.randomUUID(), EntityTypes.ARMOR_STAND,
                new Vector3d(location.getX(), location.getY(), location.getZ()),
                0f, 0f, 0f, new Vector3d(0, 0, 0), standMeta
        );
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, standSpawn);

        Location itemLoc = location.clone().add(0, 0.5, 0);
        WrapperPlayServerSpawnEntity itemSpawn = new WrapperPlayServerSpawnEntity(
                itemId, Optional.of(UUID.randomUUID()), EntityTypes.ITEM,
                new Vector3d(itemLoc.getX(), itemLoc.getY(), itemLoc.getZ()),
                0f, 0f, 0f, 0, Optional.empty()
        );
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, itemSpawn);

        WrapperPlayServerEntityVelocity velocityPacket = new WrapperPlayServerEntityVelocity(itemId, new Vector3d(0, 0, 0));
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, velocityPacket);

        ItemStack peItem = SpigotConversionUtil.fromBukkitItemStack(itemStack);
        List<EntityData<?>> itemMeta = Collections.singletonList(
                new EntityData<>(EntityMetadata.ITEM_STACK.getIndex(), EntityDataTypes.ITEMSTACK, peItem)
        );
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, new WrapperPlayServerEntityMetadata(itemId, itemMeta));

        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(PacketEntityFactory.class);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (player.isOnline()) {
                WrapperPlayServerAttachEntity attachPacket = new WrapperPlayServerAttachEntity(itemId, standId, false);
                PacketEvents.getAPI().getPlayerManager().sendPacket(player, attachPacket);
            }
        }, 1L);
    }

    /**
     * Spawns an invisible slime entity to act as a click hitbox for a hologram line.
     *
     * @param player   the player to send the packet to
     * @param entityId the entity ID of the slime
     * @param location the location to spawn at
     */
    public static void sendSpawnSlimeHitbox(Player player, int entityId, Location location) {
        List<EntityData<?>> metadata = Arrays.asList(
                new EntityData<>(EntityMetadata.ENTITY_STATUS.getIndex(), EntityDataTypes.BYTE, EntityFlag.INVISIBLE.getValue()),
                new EntityData<>(EntityMetadata.SLIME_SIZE.getIndex(), EntityDataTypes.BYTE, EntityFlag.SLIME_SIZE_1.getValue())
        );
        WrapperPlayServerSpawnLivingEntity spawnPacket = new WrapperPlayServerSpawnLivingEntity(
                entityId, UUID.randomUUID(), EntityTypes.SLIME,
                new Vector3d(location.getX(), location.getY(), location.getZ()),
                0f, 0f, 0f, new Vector3d(0, 0, 0), metadata
        );
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, spawnPacket);
    }

    /**
     * Destroys fake packet entities for a player.
     *
     * @param player    the player to send the packet to
     * @param entityIds the entity IDs to destroy
     */
    public static void sendDestroyEntities(Player player, int... entityIds) {
        WrapperPlayServerDestroyEntities destroyPacket = new WrapperPlayServerDestroyEntities(entityIds);
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, destroyPacket);
    }
}