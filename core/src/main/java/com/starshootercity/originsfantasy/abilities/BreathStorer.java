package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BreathStorer implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "By right clicking using an empty bottle, you can store your own Dragon's Breath.";
    }

    @Override
    public String title() {
        return "Dragon's Breath";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:breath_storer");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (!event.getAction().isRightClick()) return;
        runForAbility(event.getPlayer(), player -> {
            if (event.getItem().getType() == Material.GLASS_BOTTLE) {
                event.getItem().setAmount(event.getItem().getAmount() - 1);
                for (ItemStack item : player.getInventory().addItem(new ItemStack(Material.DRAGON_BREATH)).values()) {
                    player.getWorld().dropItemNaturally(player.getLocation(), item);
                }
            }
        });
    }
}
