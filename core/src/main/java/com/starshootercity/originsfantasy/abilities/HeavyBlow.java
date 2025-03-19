package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.Ability;
import com.starshootercity.abilities.types.AttributeModifierAbility;
import com.starshootercity.abilities.types.MultiAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.originsfantasy.OriginsFantasy;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HeavyBlow implements VisibleAbility, MultiAbility {
    @Override
    public String description() {
        return "Your attacks are stronger than humans, but you have a longer attack cooldown.";
    }

    @Override
    public String title() {
        return "Heavy Blow";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:heavy_blow");
    }

    @Override
    public List<Ability> getAbilities() {
        return List.of(IncreasedDamage.increasedDamage, IncreasedCooldown.increasedCooldown);
    }


    public static class IncreasedDamage implements AttributeModifierAbility {
        public static IncreasedDamage increasedDamage = new IncreasedDamage();

        @Override
        public @NotNull Attribute getAttribute() {
            return OriginsReborn.getNMSInvoker().getAttackDamageAttribute();
        }

        @Override
        public double getAmount(Player player) {
            return 1.2;
        }

        @Override
        public AttributeModifier.@NotNull Operation getOperation() {
            return AttributeModifier.Operation.MULTIPLY_SCALAR_1;
        }

        @Override
        public @NotNull Key getKey() {
            return Key.key("fantasyorigins:increased_damage");
        }
    }

    public static class IncreasedCooldown implements AttributeModifierAbility {
        public static IncreasedCooldown increasedCooldown = new IncreasedCooldown();

        @Override
        public @NotNull Attribute getAttribute() {
            return OriginsFantasy.getNMSInvoker().getAttackSpeedAttribute();
        }

        @Override
        public double getAmount(Player player) {
            return -0.4;
        }

        @Override
        public AttributeModifier.@NotNull Operation getOperation() {
            return AttributeModifier.Operation.MULTIPLY_SCALAR_1;
        }

        @Override
        public @NotNull Key getKey() {
            return Key.key("fantasyorigins:increased_cooldown");
        }
    }
}
