package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.Ability;
import com.starshootercity.abilities.types.AttributeModifierAbility;
import com.starshootercity.abilities.types.MultiAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SolidStance implements VisibleAbility, MultiAbility, AttributeModifierAbility {

    @Override
    public String description() {
        return "You are immune to knockback.";
    }

    @Override
    public String title() {
        return "Solid Stance";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:solid_stance");
    }

    @Override
    public List<Ability> getAbilities() {
        return List.of();
    }

    @Override
    public @NotNull Attribute getAttribute() {
        return OriginsReborn.getNMSInvoker().getKnockbackResistanceAttribute();
    }

    @Override
    public double getAmount(Player player) {
        return 1000;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_NUMBER;
    }
}
