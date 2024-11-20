package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.events.PlayerSwapOriginEvent;
import com.starshootercity.originsfantasy.OriginsFantasy;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class VampiricTransformation implements VisibleAbility, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:vampiric_transformation");
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor(OriginsFantasy.getInstance().getConfig().getDouble("vampire-transform-chance", 1) >= 1 ? "You can transform other players into vampires by killing them." : "You sometimes transform other players into vampires by killing them.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Vampiric Transformation", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    private final Random random = new Random();

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (event.getEntity().getKiller() == null) return;
            AbilityRegister.runForAbility(event.getEntity().getKiller(), getKey(), () -> {
                if (random.nextDouble() <= OriginsFantasy.getInstance().getConfig().getDouble("vampire-transform-chance", 1)) {
                    OriginSwapper.setOrigin(player, OriginSwapper.getOrigin(player), PlayerSwapOriginEvent.SwapReason.DIED, false);
                    player.sendMessage(Component.text("You have transformed into a Vampire!").color(NamedTextColor.RED));
                }
            });
        }
    }
}
