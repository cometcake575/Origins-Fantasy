package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Leeching implements VisibleAbility, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:leeching");
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Upon killing a mob or player, you sap a portion of its health, healing you.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Leeching", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        AbilityRegister.runForAbility(event.getEntity().getKiller(), getKey(), () -> {
            AttributeInstance mH = event.getEntity().getKiller().getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (mH == null) return;
            double maxHealth = mH.getValue();
            AttributeInstance mobMH = event.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (mobMH == null) return;
            double mobMaxHealth = mobMH.getValue();
            event.getEntity().getKiller().setHealth(Math.min(maxHealth, event.getEntity().getKiller().getHealth() + (mobMaxHealth / 5)));
        });
    }
}
