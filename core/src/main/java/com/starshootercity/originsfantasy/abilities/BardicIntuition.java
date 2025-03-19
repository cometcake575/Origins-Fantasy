package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BardicIntuition implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Your musical energy will sometimes cause a creeper to drop a music disc, even without a skeleton.";
    }

    @Override
    public String title() {
        return "Bardic Intuition";
    }

    private final Random random = new Random();

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:bardic_intuition");
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getType() != EntityType.CREEPER) return;
        if (event.getEntity().getKiller() == null) return;

        if (random.nextDouble() > 0.25) return;

        Material disc = Tag.ITEMS_CREEPER_DROP_MUSIC_DISCS.getValues().stream().toList().get(random.nextInt(Tag.ITEMS_CREEPER_DROP_MUSIC_DISCS.getValues().size()));

        runForAbility(event.getEntity().getKiller(), player -> event.getDrops().add(new ItemStack(disc)));
    }
}
