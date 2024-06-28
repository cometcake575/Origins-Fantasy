package com.starshootercity.originsfantasy;

import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

public abstract class FantasyNMSInvoker implements Listener {
    public abstract void launchArrow(Entity projectile, Entity entity, float roll, float force, float divergence);

    public abstract void transferDamageEvent(LivingEntity entity, EntityDamageEvent event);

    public abstract void boostArrow(Arrow arrow);

    public abstract Attribute getGenericScaleAttribute();

    public abstract @NotNull Attribute getGenericJumpStrengthAttribute();

    public abstract boolean duplicateAllay(Allay allay);

    public abstract @NotNull Enchantment getFortuneEnchantment();
}