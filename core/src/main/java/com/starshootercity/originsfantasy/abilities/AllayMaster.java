package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsfantasy.OriginsFantasy;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.entity.Allay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class AllayMaster implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Your musical aura allows you to breed allays without playing music.";
    }

    @Override
    public String title() {
        return "Allay Master";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:allay_master");
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        runForAbility(event.getPlayer(), player -> {
            if (event.getRightClicked() instanceof Allay allay) {
                ItemStack item = player.getInventory().getItem(event.getHand());
                if (item.getType() != Material.AMETHYST_SHARD) return;
                if (!OriginsFantasy.getNMSInvoker().duplicateAllay(allay)) return;
                event.setCancelled(true);
                item.setAmount(item.getAmount() - 1);
                if (event.getHand() == EquipmentSlot.HAND) {
                    player.swingMainHand();
                } else player.swingOffHand();
                player.getInventory().setItem(event.getHand(), item);
            }
        });
    }
}
