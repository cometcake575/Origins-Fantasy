package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.originsfantasy.OriginsFantasy;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class Chime implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You can absorb the chime of amethyst shards to regenerate health.";
    }

    @Override
    public String title() {
        return "Chime";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:chime");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        runForAbility(event.getPlayer(), player -> {
            if (!event.getAction().isRightClick()) return;
            if (event.getItem() == null) return;
            if (event.getItem().getType() == Material.AMETHYST_SHARD) {
                event.getItem().setAmount(event.getItem().getAmount() - 1);
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, getConfigOption(OriginsFantasy.getInstance(), regenerationTime, ConfigManager.SettingType.INTEGER), 1));
                if (event.getHand() == null) return;
                if (event.getHand() == EquipmentSlot.HAND) player.swingMainHand();
                else player.swingOffHand();
            }
        });
    }

    private final String regenerationTime = "regeneration_time";

    @Override
    public void initialize(JavaPlugin plugin) {
        registerConfigOption(OriginsFantasy.getInstance(), regenerationTime, Collections.singletonList("Time in ticks the regeneration effect should last"), ConfigManager.SettingType.INTEGER, 900);
    }
}
