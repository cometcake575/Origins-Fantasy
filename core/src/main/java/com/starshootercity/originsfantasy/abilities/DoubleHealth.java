package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.AttributeModifierAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DoubleHealth implements VisibleAbility, AttributeModifierAbility {
    @Override
    public String description() {
        return "As you're larger than humans, you have more health as your body protects you from damage.";
    }

    @Override
    public String title() {
        return "Double Health";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:double_health");
    }

    @Override
    public @NotNull Attribute getAttribute() {
        return OriginsReborn.getNMSInvoker().getMaxHealthAttribute();
    }

    @Override
    public double getAmount(Player player) {
        return 20;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_NUMBER;
    }
}
