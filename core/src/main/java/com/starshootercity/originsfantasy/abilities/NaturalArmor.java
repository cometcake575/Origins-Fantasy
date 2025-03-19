package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.AttributeModifierAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NaturalArmor implements VisibleAbility, AttributeModifierAbility {
    @Override
    public String description() {
        return "Your tough and strong body gives you some natural defense against attacks.";
    }

    @Override
    public String title() {
        return "Natural Armor";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:natural_armor");
    }

    @Override
    public @NotNull Attribute getAttribute() {
        return OriginsReborn.getNMSInvoker().getArmorAttribute();
    }

    @Override
    public double getAmount(Player player) {
        return 6;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_NUMBER;
    }
}
