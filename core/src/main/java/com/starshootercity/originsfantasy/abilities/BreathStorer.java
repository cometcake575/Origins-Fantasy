package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BreathStorer implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("By right clicking using an empty bottle, you can store your own Dragon's Breath.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Dragon's Breath", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:breath_storer");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (!event.getAction().isRightClick()) return;
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            if (event.getItem().getType() == Material.GLASS_BOTTLE) {
                event.getItem().setAmount(event.getItem().getAmount() - 1);
                for (ItemStack item : event.getPlayer().getInventory().addItem(new ItemStack(Material.DRAGON_BREATH)).values()) {
                    event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), item);
                }
            }
        });
    }
}
