package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsfantasy.OriginsFantasy;
import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IncreasedArrowDamage implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("All arrows you shoot deal increased damage.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Piercing Shot", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:increased_arrow_damage");
    }

    private final NamespacedKey key = new NamespacedKey(OriginsFantasy.getInstance(), "increased-arrow-damage-key");

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        AbilityRegister.runForAbility(event.getEntity(), getKey(), () -> event.getProjectile().getPersistentDataContainer().set(key, OriginSwapper.BooleanPDT.BOOLEAN, true));
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager().getPersistentDataContainer().has(key)) {
            int v = OriginsFantasy.getInstance().getConfig().getInt("arrow-damage-increase", 3);
            event.setDamage(event.getDamage() + v);
        }
    }
}
