package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.Ability;
import com.starshootercity.abilities.AttributeModifierAbility;
import com.starshootercity.abilities.MultiAbility;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HeavyBlow implements VisibleAbility, MultiAbility {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Your attacks are stronger than humans, but you have a longer attack cooldown.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Heavy Blow", OriginSwapper.LineData.LineComponent.LineType.TITLE);
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
            return Attribute.GENERIC_ATTACK_DAMAGE;
        }

        @Override
        public double getAmount() {
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
            return Attribute.GENERIC_ATTACK_SPEED;
        }

        @Override
        public double getAmount() {
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
