package com.starshootercity.originsfantasy.abilities;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.Origin;
import com.starshootercity.OriginSwapper;
import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.events.PlayerSwapOriginEvent;
import com.starshootercity.originsfantasy.FantasyEntityDismountEvent;
import com.starshootercity.originsfantasy.FantasyEntityMountEvent;
import com.starshootercity.originsfantasy.OriginsFantasy;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PermanentHorse implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You are half horse, half human.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Half Horse", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:permanent_horse");
    }

    @EventHandler
    public void onEntityDismount(FantasyEntityDismountEvent event) {
        if (event.getEntity().isDead()) return;
        AbilityRegister.runForAbility(event.getEntity(), getKey(), () -> {
            event.setCancelled(true);
            Entity vehicle = event.getDismounted().getVehicle();
            if (vehicle != null) vehicle.removePassenger(event.getDismounted());
        });
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getPlayer().getPersistentDataContainer().has(teleportingKey)) return;
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            Entity e = event.getPlayer().getVehicle();
            if (e != null) {
                e.removePassenger(event.getPlayer());
                event.getPlayer().getPersistentDataContainer().set(teleportingKey, PersistentDataType.BOOLEAN, true);
                event.setCancelled(true);
                Bukkit.getScheduler().scheduleSyncDelayedTask(OriginsFantasy.getInstance(), () -> {
                    event.getPlayer().teleport(event.getTo(), event.getCause());
                    e.teleport(event.getTo(), event.getCause());
                    e.addPassenger(event.getPlayer());
                    event.getPlayer().getPersistentDataContainer().remove(teleportingKey);
                });
            }
        });
    }

    @EventHandler
    public void onEntityMount(FantasyEntityMountEvent event) {
        AbilityRegister.runForAbility(event.getEntity(), getKey(), () -> {
            if (!event.getMount().getPersistentDataContainer().getOrDefault(key, PersistentDataType.STRING, "").equals(event.getEntity().getUniqueId().toString())) {
                event.setCancelled(true);
                if (!(event.getMount() instanceof LivingEntity)) {
                    Entity vehicle = event.getEntity().getVehicle();
                    if (vehicle != null) event.getMount().addPassenger(vehicle);
                }
            }
        });
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        if (event.getTickNumber() % 20 != 0) return;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.isDead()) continue;
            AbilityRegister.runForAbility(player, getKey(), () -> {
                if (player.getPersistentDataContainer().has(teleportingKey)) return;
                if (player.getVehicle() != null) return;
                Horse horse = (Horse) player.getWorld().spawnEntity(player.getLocation(), EntityType.HORSE);
                AttributeInstance jump = horse.getAttribute(OriginsFantasy.getNMSInvoker().getGenericJumpStrengthAttribute());
                AttributeInstance speed = horse.getAttribute(OriginsReborn.getNMSInvoker().getMovementSpeedAttribute());
                Origin origin = OriginSwapper.getOrigin(player);
                if (origin != null) {
                    if (origin.hasAbility(Key.key("fantasyorigins:super_jump")) && jump != null) jump.setBaseValue(1);
                    if (origin.hasAbility(Key.key("fantasyorigins:increased_speed")) && speed != null)speed.setBaseValue(0.4);
                }
                horse.getPersistentDataContainer().set(key, PersistentDataType.STRING, player.getUniqueId().toString());
                horse.setTamed(true);
                horse.setStyle(Horse.Style.NONE);
                ItemStack saddle = new ItemStack(Material.SADDLE);
                ItemMeta meta = saddle.getItemMeta();
                meta.getPersistentDataContainer().set(key, OriginSwapper.BooleanPDT.BOOLEAN, true);
                saddle.setItemMeta(meta);
                horse.getInventory().setSaddle(saddle);
                horse.addPassenger(player);
            });
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getItemMeta() == null) return;
        if (event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(key)) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        AbilityRegister.runForAbility(event.getEntity(), getKey(), () -> {
            Entity vehicle = event.getEntity().getVehicle();
            if (vehicle != null && vehicle.getPersistentDataContainer().has(key)) vehicle.remove();
        });
    }

    @EventHandler
    public void onPlayerSwapOrigin(PlayerSwapOriginEvent event) {
        Entity vehicle = event.getPlayer().getVehicle();
        if (vehicle != null && vehicle.getPersistentDataContainer().has(key)) vehicle.remove();
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> event.setUseBed(Event.Result.DENY));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity().getPersistentDataContainer().has(key)) {
            for (Entity entity : event.getEntity().getPassengers()) {
                if (entity instanceof LivingEntity livingEntity) {
                    OriginsFantasy.getNMSInvoker().transferDamageEvent(livingEntity, event);
                }
            }
            event.setCancelled(true);
        }
    }

    private final NamespacedKey key = new NamespacedKey(OriginsFantasy.getInstance(), "mount-key");
    private final NamespacedKey teleportingKey = new NamespacedKey(OriginsFantasy.getInstance(), "teleporting");
}
