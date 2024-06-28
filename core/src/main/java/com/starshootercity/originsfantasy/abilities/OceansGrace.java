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

public class OceansGrace implements VisibleAbility, MultiAbility {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You are a part of the water, so you have extra health and deal extra damage when in water or rain.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Ocean's Grace", OriginSwapper.LineData.LineComponent.LineType.TITLE);
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
            return Attribute.GENERIC_MAX_HEALTH;
        }

        @Override
        public double getAmount() {
            return 0;
        }

        @Override
        public double getChangedAmount(Player player) {
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
            return Attribute.GENERIC_ATTACK_DAMAGE;
        }

        @Override
        public double getAmount() {
            return 0;
        }

        @Override
        public double getChangedAmount(Player player) {
            if (player.isInWaterOrRainOrBubbleColumn()) {
                return 2.4;
            }
            return 0;
        }

        @Override
        public AttributeModifier.@NotNull Operation getOperation() {
            return AttributeModifier.Operation.MULTIPLY_SCALAR_1;
        }
    }
}
