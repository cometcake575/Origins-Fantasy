package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsfantasy.OriginsFantasy;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class BowBurst implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "By casting a spell on any regular arrow, you can instantly shoot %s arrows at once using only one, but this disables your bow for %s seconds.";
    }

    @Override
    public String modifyDescription(String description) {
        return description.formatted(getConfigOption(OriginsFantasy.getInstance(), arrowCount, ConfigManager.SettingType.INTEGER), getConfigOption(OriginsFantasy.getInstance(), cooldownTime, ConfigManager.SettingType.INTEGER));
    }

    private final String arrowCount = "arrow_count";
    private final String cooldownTime = "cooldown_time";

    @Override
    public void initialize() {
        registerConfigOption(OriginsFantasy.getInstance(), arrowCount, Collections.singletonList("The number of arrows to fire"), ConfigManager.SettingType.INTEGER, 3);
        registerConfigOption(OriginsFantasy.getInstance(), cooldownTime, Collections.singletonList("The time in seconds to disable the bow for"), ConfigManager.SettingType.INTEGER, 7);
    }

    @Override
    public String title() {
        return "Bow Burst";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:bow_burst");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.getAction().isLeftClick()) return;
        if (event.getItem() == null || event.getItem().getType() != Material.BOW) return;
        if (event.getPlayer().getCooldown(Material.BOW) > 0) return;
        runForAbility(event.getPlayer(), player -> {
            if (player.getInventory().contains(Material.ARROW)) {
                for (ItemStack item : player.getInventory()) {
                    if (item == null) continue;
                    if (item.getType() == Material.ARROW) {
                        item.setAmount(item.getAmount() - 1);
                        break;
                    }
                }
                player.setCooldown(Material.BOW, getConfigOption(OriginsFantasy.getInstance(), cooldownTime, ConfigManager.SettingType.INTEGER) * 20);

                int count = getConfigOption(OriginsFantasy.getInstance(), arrowCount, ConfigManager.SettingType.INTEGER);
                int divergence = 0;
                Arrow arrow = null;
                for (int i = 0; i < count; i++) {
                    if (arrow != null) arrow.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
                    arrow = player.launchProjectile(Arrow.class);
                    OriginsFantasy.getNMSInvoker().launchArrow(arrow, player, 0.0F, 3, divergence);
                    divergence = 15;
                }
            }
        });
    }
}
