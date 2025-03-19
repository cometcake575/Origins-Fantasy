package com.starshootercity.originsfantasy.abilities;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.originsfantasy.OriginsFantasy;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WaterSensitive implements VisibleAbility, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:water_sensitive");
    }

    @Override
    public String description() {
        return "You are hurt by water as its current drains your power.";
    }

    @Override
    public String title() {
        return "Water Sensitive";
    }


    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (Bukkit.getCurrentTick() - lastWaterDamagedMap.getOrDefault(p, Bukkit.getCurrentTick() - 20) < 20) continue;
            runForAbility(p, player -> {
                if (player.isInWaterOrRainOrBubbleColumn() || OriginsReborn.getNMSInvoker().wasTouchingWater(player)) {
                    OriginsReborn.getNMSInvoker().dealFreezeDamage(player, getConfigOption(OriginsFantasy.getInstance(), damageAmount, ConfigManager.SettingType.INTEGER));
                    lastWaterDamagedMap.put(player, Bukkit.getCurrentTick());
                }
            });
        }
    }

    private final String damageAmount = "damage_amount";

    @Override
    public void initialize(JavaPlugin plugin) {
        registerConfigOption(OriginsFantasy.getInstance(), damageAmount, Collections.singletonList("Amount to damage the player by"), ConfigManager.SettingType.INTEGER, 1);
    }

    private final Map<Player, Integer> lastWaterDamagedMap = new HashMap<>();
}
