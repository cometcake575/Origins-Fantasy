package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.abilities.AttributeModifierAbility;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsfantasy.OriginsFantasy;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.jetbrains.annotations.NotNull;

public class LargeBody implements VisibleAbility, AttributeModifierAbility {
    @Override
    public String description() {
        return "You're three blocks tall, taller than a regular human.";
    }

    @Override
    public String title() {
        return "Large Body";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:large_body");
    }

    @Override
    public @NotNull Attribute getAttribute() {
        return OriginsFantasy.getNMSInvoker().getGenericScaleAttribute();
    }

    @Override
    public double getAmount() {
        return 0.5;
    }

    @Override
    public AttributeModifier.Operation getOperation() {
        return AttributeModifier.Operation.MULTIPLY_SCALAR_1;
    }
}
