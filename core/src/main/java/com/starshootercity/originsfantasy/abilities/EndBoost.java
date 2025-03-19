package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.Ability;
import com.starshootercity.abilities.types.AttributeModifierAbility;
import com.starshootercity.abilities.types.MultiAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EndBoost implements VisibleAbility, MultiAbility {
    @Override
    public String description() {
        return "Your natural habitat is the end, so you have more health and are stronger when you are there.";
    }

    @Override
    public String title() {
        return "End Inhabitant";
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
            return OriginsReborn.getNMSInvoker().getAttackDamageAttribute();
        }

        @Override
        public double getAmount(Player player) {
            return player.getWorld().getEnvironment() == World.Environment.THE_END ? 1.6 : 0;
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
            return OriginsReborn.getNMSInvoker().getMaxHealthAttribute();
        }

        @Override
        public double getAmount(Player player) {
            return player.getWorld().getEnvironment() == World.Environment.THE_END ? 20 : 0;
        }

        @Override
        public AttributeModifier.@NotNull Operation getOperation() {
            return AttributeModifier.Operation.ADD_NUMBER;
        }
    }
}
