package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsfantasy.OriginsFantasy;
import net.kyori.adventure.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.jetbrains.annotations.NotNull;

public class PerfectShot implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You've trained with a bow for many years, and you shoot your arrows perfectly straight.";
    }

    @Override
    public String title() {
        return "Skilled Archer";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:perfect_shot");
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        runForAbility(event.getEntity(), player -> OriginsFantasy.getNMSInvoker().launchArrow(event.getProjectile(), player, 0, event.getForce(), 0));
    }
}
