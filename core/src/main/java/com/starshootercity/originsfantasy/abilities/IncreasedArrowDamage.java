package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsfantasy.OriginsFantasy;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class IncreasedArrowDamage implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "All arrows you shoot deal increased damage.";
    }

    @Override
    public String title() {
        return "Piercing Shot";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:increased_arrow_damage");
    }

    private final NamespacedKey key = new NamespacedKey(OriginsFantasy.getInstance(), "increased-arrow-damage-key");

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        runForAbility(event.getEntity(), player -> event.getProjectile().getPersistentDataContainer().set(key, OriginSwapper.BooleanPDT.BOOLEAN, true));
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager().getPersistentDataContainer().has(key)) {
            double v = getConfigOption(OriginsFantasy.getInstance(), arrowDamageIncrease, ConfigManager.SettingType.DOUBLE);
            event.setDamage(event.getDamage() + v);
        }
    }

    private final String arrowDamageIncrease = "arrow_damage_increase";

    @Override
    public void initialize() {
        registerConfigOption(OriginsFantasy.getInstance(), arrowDamageIncrease, Collections.singletonList("Amount to add to arrow damage"), ConfigManager.SettingType.DOUBLE, 3d);
    }
}
