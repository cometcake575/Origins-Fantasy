package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsfantasy.OriginsFantasy;
import com.starshootercity.util.config.ConfigManager;
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
    public String description() {
        return "Your care and mastery in the art of extracting minerals results in a much higher yield from ores than other creatures.";
    }

    @Override
    public String title() {
        return "Careful Miner";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:fortune_increaser");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockDropItem(BlockDropItemEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) return;
        runForAbility(event.getPlayer(), player -> {
            List<ItemStack> items = new ArrayList<>(blocks.getOrDefault(player, List.of()));
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
        i.addUnsafeEnchantment(OriginsFantasy.getNMSInvoker().getFortuneEnchantment(), i.getEnchantmentLevel(OriginsFantasy.getNMSInvoker().getFortuneEnchantment()) + getConfigOption(OriginsFantasy.getInstance(), fortuneIncrease, ConfigManager.SettingType.INTEGER));
        blocks.put(event.getPlayer(), event.getBlock().getDrops(i, event.getPlayer()));
    }

    private final Map<Player, Collection<ItemStack>> blocks = new HashMap<>();

    private final String fortuneIncrease = "fortune_increase";

    @Override
    public void initialize() {
        registerConfigOption(OriginsFantasy.getInstance(), fortuneIncrease, Collections.singletonList("Level to increase Fortune by"), ConfigManager.SettingType.INTEGER, 2);
    }
}
