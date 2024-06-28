package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.Ability;
import com.starshootercity.abilities.AttributeModifierAbility;
import com.starshootercity.abilities.MultiAbility;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EndBoost implements VisibleAbility, MultiAbility {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Your natural habitat is the end, so you have more health and are stronger when you are there.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("End Inhabitant", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:end_boost");
    }

    @Override
    public List<Ability> getAbilities() {
        return List.of(EndStrength.endStrength, EndHealth.endHealth);
    }

    public static class EndStrength implements AttributeModifierAbility {
        public static EndStrength endStrength = new EndStrength();

        @Override
        public @NotNull Attribute getAttribute() {
            return Attribute.GENERIC_ATTACK_DAMAGE;
        }

        @Override
        public double getAmount() {
            return 0;
        }

        @Override
        public double getChangedAmount(Player player) {
            return 1.6;
        }

        @Override
        public AttributeModifier.@NotNull Operation getOperation() {
            return AttributeModifier.Operation.MULTIPLY_SCALAR_1;
        }

        @Override
        public @NotNull Key getKey() {
            return Key.key("fantasyorigins:end_strength");
        }
    }

    public static class EndHealth implements AttributeModifierAbility {
        public static EndHealth endHealth = new EndHealth();

        @Override
        public @NotNull Key getKey() {
            return Key.key("fantasyorigins:end_health");
        }

        @Override
        public @NotNull Attribute getAttribute() {
            return Attribute.GENERIC_MAX_HEALTH;
        }

        @Override
        public double getAmount() {
            return 0;
        }

        @Override
        public double getChangedAmount(Player player) {
            return 20;
        }

        @Override
        public AttributeModifier.@NotNull Operation getOperation() {
            return AttributeModifier.Operation.ADD_NUMBER;
        }
    }
}
