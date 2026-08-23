package me.a8kj.bukkitprojects.hologram.packet.util;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.particle.Particle;
import com.github.retrooper.packetevents.protocol.particle.type.ParticleType;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.util.Vector3f;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerParticle;
import org.bukkit.entity.Player;

/**
 * Utility class for sending particle effects to players via packets.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class ParticleUtil {

    /**
     * Sends a particle effect to a specific player.
     *
     * @param player   the player to send the particle to
     * @param location the location to spawn the particle at
     * @param type     the type of particle
     * @param count    the number of particles
     * @param offsetX  the x-axis offset
     * @param offsetY  the y-axis offset
     * @param offsetZ  the z-axis offset
     * @param speed    the speed of the particles
     */
    public static void sendParticle(Player player, org.bukkit.Location location, ParticleType<?> type, int count, float offsetX, float offsetY, float offsetZ, float speed) {
        Vector3d position = new Vector3d(location.getX(), location.getY(), location.getZ());
        Vector3f offset = new Vector3f(offsetX, offsetY, offsetZ);

        Particle<?> particle = new Particle<>(type);

        WrapperPlayServerParticle packet = new WrapperPlayServerParticle(particle, false, position, offset, speed, count);
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, packet);
    }
}