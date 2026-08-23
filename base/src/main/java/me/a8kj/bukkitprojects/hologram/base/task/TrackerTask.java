package me.a8kj.bukkitprojects.hologram.base.task;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityTeleport;
import lombok.RequiredArgsConstructor;
import me.a8kj.bukkitprojects.hologram.api.Hologram;
import me.a8kj.bukkitprojects.hologram.api.HologramLine;
import me.a8kj.bukkitprojects.hologram.base.HologramManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Task responsible for dynamically moving holograms that have an active {@link me.a8kj.bukkitprojects.hologram.api.tracker.LocationTracker}.
 * It runs every tick to ensure smooth, cinematic movement by sending teleport packets
 * to all viewers currently seeing the tracked hologram.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
@RequiredArgsConstructor
public class TrackerTask extends BukkitRunnable {
    private final JavaPlugin plugin;

    @Override
    public void run() {
        for (Hologram hologram : HologramManager.getInstance().getHolograms()) {
            if (hologram.isDestroyed() || hologram.getTracker().isEmpty()) continue;

            for (Player player : hologram.getShownTo()) {
                Location newLoc = hologram.getTracker().get().getLocation(player);
                if (newLoc == null) continue;

                for (HologramLine line : hologram.getLines()) {
                    Location lineLoc = newLoc.clone().add(0, line.getLocation().getY() - hologram.getLocation().getY(), 0);
                    WrapperPlayServerEntityTeleport teleportPacket = new WrapperPlayServerEntityTeleport(
                            line.getEntityId(),
                            new Vector3d(lineLoc.getX(), lineLoc.getY(), lineLoc.getZ()),
                            0, 0, false
                    );
                    PacketEvents.getAPI().getPlayerManager().sendPacket(player, teleportPacket);

                    if (line.getHitboxEntityId() != -1) {
                        WrapperPlayServerEntityTeleport hitboxTeleport = new WrapperPlayServerEntityTeleport(
                                line.getHitboxEntityId(),
                                new Vector3d(lineLoc.getX(), lineLoc.getY(), lineLoc.getZ()),
                                0, 0, false
                        );
                        PacketEvents.getAPI().getPlayerManager().sendPacket(player, hitboxTeleport);
                    }
                }
            }
        }
    }
}