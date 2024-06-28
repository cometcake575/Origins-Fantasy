package com.starshootercity.originsfantasy.abilities;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.OriginSwapper;
import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaterSensitive implements VisibleAbility, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:water_sensitive");
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You are hurt by water as its current drains your power.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Water Sensitive", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }


    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Bukkit.getCurrentTick() - lastWaterDamagedMap.getOrDefault(player, Bukkit.getCurrentTick() - 20) < 20) continue;
            AbilityRegister.runForAbility(player, getKey(), () -> {
                if (player.isInWaterOrRainOrBubbleColumn() || OriginsReborn.getNMSInvoker().wasTouchingWater(player)) {
                    OriginsReborn.getNMSInvoker().dealFreezeDamage(player, 1);
                    lastWaterDamagedMap.put(player, Bukkit.getCurrentTick());
                }
            });
        }
    }

    private final Map<Player, Integer> lastWaterDamagedMap = new HashMap<>();
}
