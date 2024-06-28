package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsfantasy.OriginsFantasy;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BowBurst implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("By casting a spell on any regular arrow, you can instantly shoot 3 arrows at once using only one, but this disables your bow for 7 seconds.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Bow Burst", OriginSwapper.LineData.LineComponent.LineType.TITLE);
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
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            if (event.getPlayer().getInventory().contains(Material.ARROW)) {
                for (ItemStack item : event.getPlayer().getInventory()) {
                    if (item == null) continue;
                    if (item.getType() == Material.ARROW) {
                        item.setAmount(item.getAmount() - 1);
                        break;
                    }
                }
                event.getPlayer().setCooldown(Material.BOW, 140);

                Arrow a1 = event.getPlayer().launchProjectile(Arrow.class);
                Arrow a2 = event.getPlayer().launchProjectile(Arrow.class);
                Arrow a3 = event.getPlayer().launchProjectile(Arrow.class);

                OriginsFantasy.getNMSInvoker().launchArrow(a1, event.getPlayer(), 0.0F, 3, 15);
                OriginsFantasy.getNMSInvoker().launchArrow(a1, event.getPlayer(), 0.0F, 3, 0);
                OriginsFantasy.getNMSInvoker().launchArrow(a1, event.getPlayer(), 0.0F, 3, 15);

                a1.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
                a2.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
                a3.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
            }
        });
    }
}
