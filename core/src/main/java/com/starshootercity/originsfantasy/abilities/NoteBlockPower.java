package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class NoteBlockPower implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You gain strength and speed when a nearby Note Block is played.";
    }

    @Override
    public String title() {
        return "Musically Attuned";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:note_block_power");
    }

    @EventHandler
    public void onNotePlay(NotePlayEvent event) {
        for (Entity entity : event.getBlock().getLocation().getNearbyEntities(32, 32, 32)) {
            if (entity instanceof Player p) {
                runForAbility(p, player -> {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, 1));
                    player.addPotionEffect(new PotionEffect(OriginsReborn.getNMSInvoker().getStrengthEffect(), 600, 1));
                });
            }
        }
    }
}
