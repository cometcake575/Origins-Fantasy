package com.starshootercity.originsfantasy.abilities;

import com.destroystokyo.paper.MaterialTags;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsfantasy.OriginsFantasy;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class DragonFireball implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You can right click whilst holding a sword to launch a dragon's fireball, with a cooldown of %s seconds.";
    }

    @Override
    public String modifyDescription(String description) {
        return description.formatted(30);
    }

    @Override
    public String title() {
        return "Dragon's Fireball";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:dragon_fireball");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        runForAbility(event.getPlayer(), player -> {
            if (!event.getAction().isRightClick()) return;
            if (event.getClickedBlock() != null) return;
            if (event.getItem() == null) return;
            if (!MaterialTags.SWORDS.isTagged(event.getItem().getType())) return;
            if (player.getCooldown(event.getItem().getType()) > 0) return;
            for (Material material : MaterialTags.SWORDS.getValues()) {
                player.setCooldown(material, getConfigOption(OriginsFantasy.getInstance(), cooldownTime, ConfigManager.SettingType.INTEGER));
            }
            org.bukkit.entity.DragonFireball fireball = player.launchProjectile(org.bukkit.entity.DragonFireball.class);
            Bukkit.getScheduler().scheduleSyncDelayedTask(OriginsFantasy.getInstance(), () -> fireball.setVelocity(player.getLocation().getDirection().multiply(getConfigOption(OriginsFantasy.getInstance(), fireballVelocity, ConfigManager.SettingType.FLOAT))));
        });
    }

    private final String cooldownTime = "cooldown_time";
    private final String fireballVelocity = "fireball_velocity";

    @Override
    public void initialize() {
        registerConfigOption(OriginsFantasy.getInstance(), cooldownTime, Collections.singletonList("The cooldown in seconds between each fireball"), ConfigManager.SettingType.INTEGER, 30);
        registerConfigOption(OriginsFantasy.getInstance(), fireballVelocity, Collections.singletonList("The velocity the fireball should be given"), ConfigManager.SettingType.FLOAT, 1.2f);
    }
}
