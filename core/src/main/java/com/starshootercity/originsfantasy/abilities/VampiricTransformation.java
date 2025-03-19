package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.events.PlayerSwapOriginEvent;
import com.starshootercity.originsfantasy.OriginsFantasy;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Random;

public class VampiricTransformation implements VisibleAbility, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:vampiric_transformation");
    }

    @Override
    public String description() {
        return "You can transform other players into vampires by killing them.";
    }

    @Override
    public String title() {
        return "Vampiric Transformation";
    }

    private final Random random = new Random();

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (event.getEntity().getKiller() == null) return;
            runForAbility(event.getEntity().getKiller(), pl -> {
                if (random.nextDouble() <= getConfigOption(OriginsFantasy.getInstance(), transformChance, ConfigManager.SettingType.FLOAT)) {
                    OriginSwapper.setOrigin(player, OriginSwapper.getOrigin(pl, "origin"), PlayerSwapOriginEvent.SwapReason.DIED, false, "origin");
                    player.sendMessage(Component.text("You have transformed into a Vampire!").color(NamedTextColor.RED));
                }
            });
        }
    }

    private final String transformChance = "transformation_chance";

    @Override
    public void initialize(JavaPlugin plugin) {
        registerConfigOption(OriginsFantasy.getInstance(), transformChance, Collections.singletonList("Chance (between 0 and 1) a player will transform into a vampire when killed by one"), ConfigManager.SettingType.FLOAT, 1f);
    }
}
