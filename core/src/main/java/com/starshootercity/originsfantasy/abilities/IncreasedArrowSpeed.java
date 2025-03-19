package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.originsfantasy.OriginsFantasy;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class IncreasedArrowSpeed implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Arrows you shoot are twice as fast than ones shot by a regular human.";
    }

    @Override
    public String title() {
        return "Masterful Speed";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:increased_arrow_speed");
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        double v = getConfigOption(OriginsFantasy.getInstance(), arrowSpeedMultiplier, ConfigManager.SettingType.DOUBLE);
        runForAbility(event.getEntity(), player -> Bukkit.getScheduler().scheduleSyncDelayedTask(OriginsFantasy.getInstance(), () -> event.getProjectile().setVelocity(event.getProjectile().getVelocity().multiply(v))));
    }

    private final String arrowSpeedMultiplier = "arrow_speed_multiplier";

    @Override
    public void initialize(JavaPlugin plugin) {
        registerConfigOption(OriginsFantasy.getInstance(), arrowSpeedMultiplier, Collections.singletonList("Multiplier for the arrow's velocity"), ConfigManager.SettingType.DOUBLE, 2d);
    }
}
