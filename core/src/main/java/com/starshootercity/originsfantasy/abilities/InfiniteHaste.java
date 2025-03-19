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
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class InfiniteHaste implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You're well trained in mining, so are much faster than a regular human.";
    }

    @Override
    public String title() {
        return "Fast Miner";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:infinite_haste");
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        if (event.getTickNumber() % 20 != 0) return;
        for (Player p : Bukkit.getOnlinePlayers()) {
            runForAbility(p, player -> player.addPotionEffect(new PotionEffect(OriginsReborn.getNMSInvoker().getHasteEffect(), 30,
                    getConfigOption(OriginsFantasy.getInstance(), effectLevel, ConfigManager.SettingType.INTEGER))));
        }
    }

    private final String effectLevel = "effect_level";

    @Override
    public void initialize(JavaPlugin plugin) {
        registerConfigOption(OriginsFantasy.getInstance(), effectLevel, Collections.singletonList("Level of the effect"), ConfigManager.SettingType.INTEGER, 1);
    }
}
