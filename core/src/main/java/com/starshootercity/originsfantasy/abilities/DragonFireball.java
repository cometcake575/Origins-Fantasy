package com.starshootercity.originsfantasy.abilities;

import com.destroystokyo.paper.MaterialTags;
import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsfantasy.OriginsFantasy;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DragonFireball implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You can right click whilst holding a sword to launch a dragon's fireball, with a cooldown of 30 seconds.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Dragon's Fireball", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:dragon_fireball");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            if (!event.getAction().isRightClick()) return;
            if (event.getClickedBlock() != null) return;
            if (event.getItem() == null) return;
            if (!MaterialTags.SWORDS.isTagged(event.getItem().getType())) return;
            if (event.getPlayer().getCooldown(event.getItem().getType()) > 0) return;
            for (Material material : MaterialTags.SWORDS.getValues()) {
                event.getPlayer().setCooldown(material, 600);
            }
            org.bukkit.entity.DragonFireball fireball = event.getPlayer().launchProjectile(org.bukkit.entity.DragonFireball.class);
            Bukkit.getScheduler().scheduleSyncDelayedTask(OriginsFantasy.getInstance(), () -> fireball.setVelocity(event.getPlayer().getLocation().getDirection().multiply(1.2)));
        });
    }
}
