package com.starshootercity.originsfantasy.abilities;

import com.destroystokyo.paper.MaterialTags;
import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DaylightSensitive implements VisibleAbility, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:daylight_sensitive");
    }

    @Override
    public String description() {
        return "Your greatest weakness is daylight, which causes you to burst into flames.";
    }

    @Override
    public String title() {
        return "Daylight Sensitive";
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent ignored) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            runForAbility(p, player -> {
                        Block block = player.getWorld().getHighestBlockAt(player.getLocation());
                        while ((MaterialTags.GLASS.isTagged(block) || (MaterialTags.GLASS_PANES.isTagged(block)) && block.getY() >= player.getLocation().getY())) {
                            block = block.getRelative(BlockFace.DOWN);
                        }
                        boolean height = block.getY() < player.getLocation().getY();
                        boolean isInOverworld = player.getWorld().getEnvironment().equals(World.Environment.NORMAL);
                        boolean day = player.getWorld().isDayTime();

                        if (!getConfigOption(OriginsReborn.getInstance(), burnWithHelmet, ConfigManager.SettingType.BOOLEAN)) {
                            ItemStack helm = player.getInventory().getHelmet();
                            if (helm != null) {
                                if (!helm.getType().isAir()) return;
                            }
                        }

                        if (height && isInOverworld && day && !player.isInWaterOrRainOrBubbleColumn()) {
                            player.setFireTicks(Math.max(player.getFireTicks(), 60));
                        }
                    });
        }
    }

    private final String burnWithHelmet = "burn_with_helmet";

    @Override
    public void initialize(JavaPlugin plugin) {
        registerConfigOption(OriginsReborn.getInstance(), burnWithHelmet, List.of("Whether the player should burn even when wearing a helmet"), ConfigManager.SettingType.BOOLEAN, true);
    }
}
