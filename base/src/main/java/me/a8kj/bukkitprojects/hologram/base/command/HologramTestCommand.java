package me.a8kj.bukkitprojects.hologram.base.command;

import me.a8kj.bukkitprojects.hologram.api.ClickType;
import me.a8kj.bukkitprojects.hologram.api.Hologram;
import me.a8kj.bukkitprojects.hologram.api.Holograms;
import me.a8kj.bukkitprojects.hologram.api.ItemDisplayType;
import me.a8kj.bukkitprojects.hologram.api.handler.FocusHandler;
import me.a8kj.bukkitprojects.hologram.api.handler.LineEffectHandler;
import me.a8kj.bukkitprojects.hologram.api.tracker.LocationTracker;
import me.a8kj.bukkitprojects.hologram.base.SessionManager;
import me.a8kj.bukkitprojects.hologram.util.HeadUtil;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

/**
 * Command executor for testing various hologram features.
 * Allows spawning test holograms for helmets, 3D items, cinematic tracking,
 * clickable buttons, and drag-and-drop mechanics.
 *
 * @author a8kj7sea
 * @version 1.5.7
 */
public class HologramTestCommand implements CommandExecutor {

    private final Map<String, Hologram> globalHolograms = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(ChatColor.YELLOW + "Usage: /holotest <helmet | 3d | cinematic | click | drag | cleanup>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "helmet" -> testHelmetItem(player);
            case "3d" -> test3DItem(player);
            case "cinematic" -> testCinematicHologram(player);
            case "click" -> testClickableHologram(player);
            case "drag" -> testDragAndDrop(player);
            case "cleanup" -> cleanup(player);
            default -> player.sendMessage(ChatColor.RED + "Unknown subcommand.");
        }
        return true;
    }

    /**
     * Spawns a static helmet item hologram.
     *
     * @param player the player executing the command
     */
    private void testHelmetItem(Player player) {
        Location loc = player.getLocation().add(0, 2, 0);
        ItemStack customHead = HeadUtil.fromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2MxNTBkZGU1ZmI4ZjEyYmM4MjY3MmMzZTM2NjU0ZTcyZmRiMTU2MzQ4ZjU2N2QyZjUzMmQyNTk1YjNkYmQ0MyJ9fX0=");

        Hologram hologram = Holograms.builder()
                .location(loc)
                .global()
                .viewDistance(30)
                .appendLine()
                .text(ChatColor.AQUA + "" + ChatColor.BOLD + "HELMET ITEM (Static)")
                .add()
                .appendLine()
                .item(customHead)
                .itemDisplayType(ItemDisplayType.FLOATING_3D)
                .add()
                .build();

        globalHolograms.put("helmet", hologram);
        hologram.spawn(player);
        player.sendMessage(ChatColor.GREEN + "Static Helmet hologram spawned!");
    }

    /**
     * Spawns a floating, rotating 3D item hologram.
     *
     * @param player the player executing the command
     */
    private void test3DItem(Player player) {
        Location loc = player.getLocation().add(0, 2, 0);

        Hologram hologram = Holograms.builder()
                .location(loc)
                .global()
                .viewDistance(30)
                .appendLine()
                .text(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "FLOATING 3D ITEM (Rotating)")
                .add()
                .appendLine()
                .item(new ItemStack(Material.DIAMOND))
                .itemDisplayType(ItemDisplayType.FLOATING_3D)
                .add()
                .build();

        globalHolograms.put("3d", hologram);
        hologram.spawn(player);
        player.sendMessage(ChatColor.GREEN + "Floating 3D Item hologram spawned!");
    }

    /**
     * Spawns a cinematic hologram that follows the player's eye level with particle effects.
     *
     * @param player the player executing the command
     */
    private void testCinematicHologram(Player player) {
        LocationTracker eyeTracker = new LocationTracker() {
            @Override
            public Location getLocation(Player viewer) {
                Location eye = viewer.getEyeLocation();
                Vector dir = eye.getDirection().multiply(2.0);
                return eye.add(dir);
            }

            @Override
            public boolean isComplete() {
                return false;
            }
        };

        LineEffectHandler circleEffect = new LineEffectHandler() {
            @Override
            public void onTick(Player viewer, me.a8kj.bukkitprojects.hologram.api.HologramLine line, int tick) {
                Location loc = line.getLocation();
                for (int i = 0; i < 20; i++) {
                    double angle = tick * 0.2 + (i * (Math.PI * 2 / 20));
                    double x = Math.cos(angle) * 0.5;
                    double z = Math.sin(angle) * 0.5;
                    Location pLoc = loc.clone().add(x, 0, z);
                    viewer.playEffect(pLoc, Effect.FLAME, 0);
                }
            }
        };

        Hologram hologram = Holograms.builder()
                .location(player.getLocation())
                .tracker(eyeTracker)
                .viewer(player)
                .viewDistance(64)
                .appendLine()
                .text(ChatColor.GOLD + "" + ChatColor.BOLD + "CINEMATIC UI")
                .effectHandler(circleEffect)
                .effectInterval(1L)
                .add()
                .build();

        SessionManager.getInstance().getOrCreateSession(player).addHologram(hologram);
        hologram.spawn(player);

        player.sendMessage(ChatColor.GREEN + "Cinematic hologram spawned! Look around to see it follow you.");
    }

    /**
     * Spawns a clickable hologram button with focus effects.
     *
     * @param player the player executing the command
     */
    private void testClickableHologram(Player player) {
        Location loc = player.getLocation().add(0, 2, 0);
        ItemStack customHead = HeadUtil.fromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2MxNTBkZGU1ZmI4ZjEyYmM4MjY3MmMzZTM2NjU0ZTcyZmRiMTU2MzQ4ZjU2N2QyZjUzMmQyNTk1YjNkYmQ0MyJ9fX0=");

        FocusHandler scaleFocus = new FocusHandler() {
            @Override
            public void onFocus(Player viewer, me.a8kj.bukkitprojects.hologram.api.HologramLine line) {
                viewer.playSound(viewer.getLocation(), Sound.CLICK, 1f, 1f);
                viewer.sendMessage(ChatColor.YELLOW + "» Focused on button!");
            }

            @Override
            public void onTickFocus(Player viewer, me.a8kj.bukkitprojects.hologram.api.HologramLine line, int tick) {
                Location loc = line.getLocation().clone().subtract(0, 0.5, 0);
                viewer.playEffect(loc, Effect.HAPPY_VILLAGER, 0);
            }

            @Override
            public void onUnfocus(Player viewer, me.a8kj.bukkitprojects.hologram.api.HologramLine line) {
                viewer.playSound(viewer.getLocation(), Sound.CLICK, 1f, 0.5f);
                viewer.sendMessage(ChatColor.RED + "« Unfocused.");
            }
        };

        Hologram hologram = Holograms.builder()
                .location(loc)
                .viewer(player)
                .viewDistance(10)
                .appendLine()
                .item(customHead)
                .itemDisplayType(ItemDisplayType.HELMET)
                .clickable()
                .onClick((p, clickType) -> {
                    if (clickType == ClickType.LEFT_CLICK) {
                        p.sendMessage(ChatColor.GOLD + "You LEFT clicked the head button!");
                    } else {
                        p.sendMessage(ChatColor.GOLD + "You RIGHT clicked the head button!");
                    }
                })
                .focusHandler(scaleFocus)
                .add()
                .build();

        SessionManager.getInstance().getOrCreateSession(player).addHologram(hologram);
        hologram.spawn(player);

        player.sendMessage(ChatColor.GREEN + "Clickable hologram spawned! Look at it and click it.");
    }

    /**
     * Spawns a drag-and-drop hologram that follows the player until right-clicked.
     *
     * @param player the player executing the command
     */
    private void testDragAndDrop(Player player) {
        class DragTracker implements LocationTracker {
            private boolean dragging = true;
            private Location lastLoc;

            public void drop(Location loc) {
                this.dragging = false;
                this.lastLoc = loc;
            }

            @Override
            public Location getLocation(Player viewer) {
                if (dragging) {
                    lastLoc = viewer.getEyeLocation().add(viewer.getLocation().multiply(1.5));
                }
                return lastLoc;
            }

            @Override
            public boolean isComplete() {
                return false;
            }
        }

        DragTracker dragTracker = new DragTracker();

        Hologram hologram = Holograms.builder()
                .location(player.getLocation())
                .tracker(dragTracker)
                .viewer(player)
                .viewDistance(10)
                .appendLine()
                .item(new ItemStack(Material.GOLD_BLOCK))
                .itemDisplayType(ItemDisplayType.HELMET)
                .clickable()
                .onClick((p, clickType) -> {
                    if (clickType == ClickType.RIGHT_CLICK) {
                        dragTracker.drop(p.getEyeLocation().add(p.getLocation().multiply(1.5)));
                        p.playSound(p.getLocation(), Sound.WOOD_CLICK, 1f, 1f);
                        p.sendMessage(ChatColor.GREEN + "Dropped the item!");
                    }
                })
                .add()
                .build();

        SessionManager.getInstance().getOrCreateSession(player).addHologram(hologram);
        hologram.spawn(player);
        player.sendMessage(ChatColor.GREEN + "Drag and Drop spawned! Right-click to drop it in place.");
    }

    /**
     * Cleans up all test holograms for the executing player.
     *
     * @param player the player executing the command
     */
    private void cleanup(Player player) {
        for (Hologram holo : globalHolograms.values()) {
            holo.destroy();
        }
        globalHolograms.clear();

        SessionManager.getInstance().endSession(player.getUniqueId());
        player.sendMessage(ChatColor.RED + "Holograms cleaned up!");
    }
}