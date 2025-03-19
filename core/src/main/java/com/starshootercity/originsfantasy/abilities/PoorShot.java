package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.originsfantasy.OriginsFantasy;
import net.kyori.adventure.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.jetbrains.annotations.NotNull;

public class PoorShot implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Your big hands are clumsy with a bow, so your arrows are slow and not very accurate.";
    }

    @Override
    public String title() {
        return "Clumsy Shot";
    }
    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:poor_shot");
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        runForAbility(event.getEntity(), player -> OriginsFantasy.getNMSInvoker().launchArrow(event.getProjectile(), player, 0, event.getForce(), 2.4f));
    }
}
