package me.a8kj.bukkitprojects.hologram.packet;

import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;
import me.a8kj.bukkitprojects.hologram.api.ClickType;
import me.a8kj.bukkitprojects.hologram.api.HologramContext;
import me.a8kj.bukkitprojects.hologram.api.HologramLine;
import me.a8kj.bukkitprojects.hologram.api.event.HologramInteractEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Packet listener responsible for intercepting entity interaction packets.
 * This handles clicks on clickable hologram lines and prevents double-click actions.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class PacketListener extends PacketListenerAbstract {

    /**
     * Constructs a new PacketListener with normal priority.
     */
    public PacketListener() {
        super(PacketListenerPriority.NORMAL);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() != PacketType.Play.Client.INTERACT_ENTITY) return;

        WrapperPlayClientInteractEntity useEntity = new WrapperPlayClientInteractEntity(event);

        if (useEntity.getAction() == WrapperPlayClientInteractEntity.InteractAction.INTERACT) return;

        int entityId = useEntity.getEntityId();

        HologramContext context = PacketManager.getContext();
        if (context == null) return;

        HologramLine line = context.getLineByEntityId(entityId);
        if (line == null || !line.isClickable()) return;

        Player player = event.getPlayer();

        ClickType clickType;
        switch (useEntity.getAction()) {
            case ATTACK -> clickType = ClickType.LEFT_CLICK;
            case INTERACT_AT -> clickType = ClickType.RIGHT_CLICK;
            default -> clickType = ClickType.RIGHT_CLICK;
        }

        Bukkit.getScheduler().runTask(context.getPlugin(), () -> {
            HologramInteractEvent interactEvent = new HologramInteractEvent(line, player, clickType);
            Bukkit.getPluginManager().callEvent(interactEvent);
            if (!interactEvent.isCancelled()) {
                line.getClickAction().ifPresent(action -> action.accept(player, clickType));
            }
        });

        event.setCancelled(true);
    }
}