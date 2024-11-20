package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsfantasy.OriginsFantasy;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IncreasedArrowSpeed implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Arrows you shoot are twice as fast than ones shot by a regular human.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Masterful Speed", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:increased_arrow_speed");
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        int v = OriginsFantasy.getInstance().getConfig().getInt("arrow-speed-multiplier", 2);
        AbilityRegister.runForAbility(event.getEntity(), getKey(), () -> Bukkit.getScheduler().scheduleSyncDelayedTask(OriginsFantasy.getInstance(), () -> event.getProjectile().setVelocity(event.getProjectile().getVelocity().multiply(v))));
    }
}
