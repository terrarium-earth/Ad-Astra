package com.github.alexnijjar.ad_astra.entities.mobs;

import com.github.alexnijjar.ad_astra.registry.ModEntityTypes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class PygroEntity extends PiglinEntity {

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.45).add(EntityAttributes.GENERIC_MAX_HEALTH, 24).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6);
    }

    public PygroEntity(EntityType<? extends AbstractPiglinEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    protected void zombify(ServerWorld world) {
        ZombifiedPygroEntity zombifiedPygroEntity = this.convertTo(ModEntityTypes.ZOMBIFIED_PYGRO, true);
        if (zombifiedPygroEntity != null) {
            zombifiedPygroEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0));
        }
    }
}
