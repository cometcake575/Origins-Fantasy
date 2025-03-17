package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

public class StrongSkin implements VisibleAbility, Listener {

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:strong_skin");
    }
    
    @Override
    public String description() {
        return "You've got naturally thicker skin than regular humans, so arrows do a lot less damage.";
    }

    @Override
    public String title() {
        return "Thick Skin";
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            runForAbility(event.getEntity(), player -> event.setDamage(event.getDamage() / 4));
        }
    }
}
