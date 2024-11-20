package com.starshootercity.originsfantasy.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.OriginsReborn;
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

public class OceanWish implements VisibleAbility, MultiAbility {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Your natural habitat is the ocean, so you're much weaker when you're not in the water.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Ocean Wish", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("fantasyorigins:ocean_wish");
    }

    @Override
    public List<Ability> getAbilities() {
        return List.of(LandSlowness.landSlowness, LandHealth.landHealth, LandWeakness.landWeakness);
    }

    public static class LandWeakness implements AttributeModifierAbility {
        public static LandWeakness landWeakness = new LandWeakness();

        @Override
        public @NotNull Attribute getAttribute() {
            return OriginsReborn.getNMSInvoker().getAttackDamageAttribute();
        }

        @Override
        public double getAmount() {
            return 0;
        }

        @Override
        public double getChangedAmount(Player player) {
            return -0.4;
        }

        @Override
        public AttributeModifier.@NotNull Operation getOperation() {
            return AttributeModifier.Operation.MULTIPLY_SCALAR_1;
        }

        @Override
        public @NotNull Key getKey() {
            return Key.key("fantasyorigins:land_weakness");
        }
    }

    public static class LandHealth implements AttributeModifierAbility {
        public static LandHealth landHealth = new LandHealth();

        @Override
        public @NotNull Key getKey() {
            return Key.key("fantasyorigins:land_health");
        }

        @Override
        public @NotNull Attribute getAttribute() {
            return OriginsReborn.getNMSInvoker().getMaxHealthAttribute();
        }

        @Override
        public double getAmount() {
            return 0;
        }

        @Override
        public double getChangedAmount(Player player) {
            if (player.isInWaterOrRainOrBubbleColumn()) {
                return 0;
            }
            return -12;
        }

        @Override
        public AttributeModifier.@NotNull Operation getOperation() {
            return AttributeModifier.Operation.ADD_NUMBER;
        }
    }

    public static class LandSlowness implements AttributeModifierAbility {
        public static LandSlowness landSlowness = new LandSlowness();

        @Override
        public @NotNull Key getKey() {
            return Key.key("fantasyorigins:land_slowness");
        }

        @Override
        public @NotNull Attribute getAttribute() {
            return OriginsReborn.getNMSInvoker().getMovementSpeedAttribute();
        }

        @Override
        public double getAmount() {
            return 0;
        }

        @Override
        public double getChangedAmount(Player player) {
            if (player.isInWaterOrRainOrBubbleColumn()) {
                return 0;
            }
            return -0.4;
        }

        @Override
        public AttributeModifier.@NotNull Operation getOperation() {
            return AttributeModifier.Operation.MULTIPLY_SCALAR_1;
        }
    }
}
