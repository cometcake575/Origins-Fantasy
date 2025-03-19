package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.jetbrains.annotations.NotNull;

public class PotionResistant implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Potions have no effect on you as your body is made of stone.";
    }

    @Override
    public String title() {
        return "Potion Resistant";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:potion_resistant");
    }

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent event) {
        if (event.getNewEffect() == null) return;
        runForAbility(event.getEntity(), player -> event.setCancelled(true));
    }
}
