package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsfantasy.OriginsFantasy;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class FortuneIncreaser implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Your care and mastery in the art of extracting minerals results in a much higher yield from ores than other creatures.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Careful Miner", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:fortune_increaser");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockDropItem(BlockDropItemEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) return;
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            List<ItemStack> items = new ArrayList<>(blocks.getOrDefault(event.getPlayer(), List.of()));
            List<ItemStack> otherItems = new ArrayList<>();
            for (Item item : event.getItems()) {
                otherItems.add(item.getItemStack());
            }
            event.getItems().clear();
            for (ItemStack i : otherItems) {
                for (int itemIndex = 0; itemIndex < items.size(); itemIndex++) {
                    ItemStack item = items.get(itemIndex);
                    if (item.getAmount() == 0) continue;
                    if (i.getType().equals(item.getType()) && i.getItemMeta().equals(item.getItemMeta())) {
                        int amount = item.getAmount() - i.getAmount();
                        item.setAmount(Math.max(0, amount));
                    }
                    items.set(itemIndex, item);
                }
            }
            items.addAll(otherItems);
            items.removeIf(itemStack -> itemStack.getAmount() <= 0);
            for (ItemStack item : items) {
                event.getItems().add(event.getBlock().getWorld().dropItem(event.getBlock().getLocation().clone().add(0.5, 0, 0.5), item));
            }
        });
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        ItemStack i = event.getPlayer().getInventory().getItemInMainHand().clone();
        if (i.getItemMeta() == null) return;
        i.addUnsafeEnchantment(OriginsFantasy.getNMSInvoker().getFortuneEnchantment(), i.getEnchantmentLevel(OriginsFantasy.getNMSInvoker().getFortuneEnchantment()) + 2);
        blocks.put(event.getPlayer(), event.getBlock().getDrops(i, event.getPlayer()));
    }

    private final Map<Player, Collection<ItemStack>> blocks = new HashMap<>();
}
