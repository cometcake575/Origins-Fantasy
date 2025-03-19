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

public class OceansGrace implements VisibleAbility, MultiAbility {
    @Override
    public String description() {
        return "You are a part of the water, so you have extra health and deal extra damage when in water or rain.";
    }

    @Override
    public String title() {
        return "Ocean's Grace";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:oceans_grace");
    }

    @Override
    public List<Ability> getAbilities() {
        return List.of(WaterStrength.waterStrength, WaterHealth.waterHealth);
    }

    public static class WaterHealth implements AttributeModifierAbility {
        public static WaterHealth waterHealth = new WaterHealth();

        @Override
        public @NotNull Attribute getAttribute() {
            return OriginsReborn.getNMSInvoker().getMaxHealthAttribute();
        }

        @Override
        public double getAmount(Player player) {
            if (player.isInWaterOrRainOrBubbleColumn()) {
                return 4;
            }
            return 0;
        }

        @Override
        public AttributeModifier.@NotNull Operation getOperation() {
            return AttributeModifier.Operation.ADD_NUMBER;
        }

        @Override
        public @NotNull Key getKey() {
            return Key.key("fantasyorigins:water_health");
        }
    }

    public static class WaterStrength implements AttributeModifierAbility {
        public static WaterStrength waterStrength = new WaterStrength();

        @Override
        public @NotNull Key getKey() {
            return Key.key("fantasyorigins:water_strength");
        }

        @Override
        public @NotNull Attribute getAttribute() {
            return OriginsReborn.getNMSInvoker().getAttackDamageAttribute();
        }

        @Override
        public double getAmount(Player player) {
            if (player.isInWaterOrRainOrBubbleColumn()) {
                return 1.4;
            }
            return 0;
        }

        @Override
        public AttributeModifier.@NotNull Operation getOperation() {
            return AttributeModifier.Operation.MULTIPLY_SCALAR_1;
        }
    }
}
