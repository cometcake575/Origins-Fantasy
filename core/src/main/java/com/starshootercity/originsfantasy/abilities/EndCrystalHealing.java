package com.starshootercity.originsfantasy.abilities;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsfantasy.OriginsFantasy;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class EndCrystalHealing implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You can regenerate health from nearby End Crystals.";
    }

    @Override
    public String title() {
        return "Crystal Healer";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:end_crystal_healing");
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        if (event.getTickNumber() % 5 != 0) return;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.isDead()) continue;
            runForAbility(p, player -> {
                int range = getConfigOption(OriginsFantasy.getInstance(), crystalRange, ConfigManager.SettingType.INTEGER);
                for (Entity entity : player.getNearbyEntities(range, range, range)) {
                    if (entity instanceof EnderCrystal crystal) {
                        if (entity.getLocation().distance(player.getLocation()) > 12) {
                            if (crystal.getBeamTarget() != null && crystal.getBeamTarget().distance(player.getLocation()) < 12) {
                                crystal.setBeamTarget(null);
                            }
                        }
                        else {
                            crystal.setBeamTarget(player.getLocation().clone().subtract(0, 1, 0));
                            player.setHealth(Math.min(20, player.getHealth() + 1));
                        }
                    }
                }
            });
        }
    }

    private final String crystalRange = "crystal_range";

    @Override
    public void initialize() {
        registerConfigOption(OriginsFantasy.getInstance(), crystalRange, Collections.singletonList("Range the end crystal effect should be"), ConfigManager.SettingType.INTEGER, 48);
    }
}
