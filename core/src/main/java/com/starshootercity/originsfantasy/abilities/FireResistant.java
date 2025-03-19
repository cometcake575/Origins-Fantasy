package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FireResistant implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Your stone skin makes you immune to fire.";
    }

    @Override
    public String title() {
        return "Heat Resistant";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:fire_resistant");
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        runForAbility(event.getEntity(), player -> {
            if (List.of(EntityDamageEvent.DamageCause.HOT_FLOOR, EntityDamageEvent.DamageCause.FIRE_TICK, EntityDamageEvent.DamageCause.FIRE, EntityDamageEvent.DamageCause.LAVA).contains(event.getCause())) event.setCancelled(true);
            player.setFireTicks(0);
        });
    }
}
