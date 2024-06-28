package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Chime implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You can absorb the chime of amethyst shards to regenerate health.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Chime", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:chime");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            if (!event.getAction().isRightClick()) return;
            if (event.getItem() == null) return;
            if (event.getItem().getType() == Material.AMETHYST_SHARD) {
                event.getItem().setAmount(event.getItem().getAmount() - 1);
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 900, 1));
                if (event.getHand() == null) return;
                if (event.getHand() == EquipmentSlot.HAND) event.getPlayer().swingMainHand();
                else event.getPlayer().swingOffHand();
            }
        });
    }
}
