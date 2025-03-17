package com.starshootercity.originsfantasy.abilities;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class InfiniteNightVision implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You can see in the dark after generations of evolution.";
    }

    @Override
    public String title() {
        return "Dark Eyes";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:infinite_night_vision");
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        if (event.getTickNumber() % 15 != 0) return;
        for (Player p : Bukkit.getOnlinePlayers()) {
            runForAbility(p, player -> player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 240, 0)));
        }
    }
}
