package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.AttributeModifierAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import io.papermc.paper.world.MoonPhase;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MoonStrength implements VisibleAbility, AttributeModifierAbility {
    @Override
    public String description() {
        return "You're a worshipper of the moon, so on nights with a full moon you're stronger than normal.";
    }

    @Override
    public String title() {
        return "Moon's Blessing";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:moon_strength");
    }

    @Override
    public @NotNull Attribute getAttribute() {
        return OriginsReborn.getNMSInvoker().getAttackDamageAttribute();
    }

    @Override
    public double getAmount(Player player) {
        if (!player.getWorld().isDayTime() && player.getWorld().getMoonPhase() == MoonPhase.FULL_MOON) {
            return 2.4;
        }
        return 0;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.MULTIPLY_SCALAR_1;
    }
}
