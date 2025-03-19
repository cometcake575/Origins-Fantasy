package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.originsfantasy.OriginsFantasy;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.jetbrains.annotations.NotNull;

public class ArrowEffectBooster implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Your connection to your bow and arrow enhances any potion effects placed on your arrows.";
    }

    @Override
    public String title() {
        return "Arrow Lord";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:arrow_effect_booster");
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        runForAbility(event.getEntity(), player -> {
            if (event.getProjectile() instanceof Arrow arrow) {
                OriginsFantasy.getNMSInvoker().boostArrow(arrow);
            }
        });
    }
}
