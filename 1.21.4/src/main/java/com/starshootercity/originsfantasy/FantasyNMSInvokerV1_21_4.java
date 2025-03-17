package com.starshootercity.originsfantasy;

import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.AbstractProjectile;
import org.bukkit.craftbukkit.entity.CraftAllay;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.entity.EntityMountEvent;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FantasyNMSInvokerV1_21_4 extends FantasyNMSInvoker {
    @Override
    public void launchArrow(Entity projectile, Entity entity, float roll, float force, float divergence) {
        ((AbstractProjectile) projectile).getHandle().shootFromRotation(((CraftEntity) entity).getHandle(), entity.getPitch(), entity.getYaw(), roll, force, divergence);
    }

    @Override
    public @NotNull Attribute getAttackSpeedAttribute() {
        return Attribute.ATTACK_SPEED;
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void transferDamageEvent(LivingEntity entity, EntityDamageEvent event) {
        entity.damage(event.getDamage(), event.getDamageSource());
    }

    @Override
    public void boostArrow(Arrow arrow) {
        if (arrow.getBasePotionType() == null) return;
        for (PotionEffect effect : arrow.getBasePotionType().getPotionEffects()) {
            arrow.addCustomEffect(effect.withDuration(effect.getDuration()).withAmplifier(effect.getAmplifier()+1), true);
        }
    }

    @Override
    public @Nullable Attribute getGenericScaleAttribute() {
        return Attribute.SCALE;
    }

    @Override
    public @NotNull Attribute getGenericJumpStrengthAttribute() {
        return Attribute.JUMP_STRENGTH;
    }

    @Override
    public boolean duplicateAllay(Allay allay) {
        if (allay.getDuplicationCooldown() > 0) return false;
        allay.duplicateAllay();
        ((CraftWorld) allay.getWorld()).getHandle().broadcastEntityEvent(((CraftAllay) allay).getHandle(), (byte) 18);
        return true;
    }

    @Override
    public @NotNull Enchantment getFortuneEnchantment() {
        return Enchantment.FORTUNE;
    }

    @EventHandler
    public void onEntityDismount(EntityDismountEvent event) {
        event.setCancelled(!new FantasyEntityDismountEvent(event.getEntity(), event.getDismounted(), event.isCancellable()).callEvent());
    }

    @EventHandler
    public void onEntityMount(EntityMountEvent event) {
        event.setCancelled(!new FantasyEntityMountEvent(event.getEntity(), event.getMount()).callEvent());
    }
}