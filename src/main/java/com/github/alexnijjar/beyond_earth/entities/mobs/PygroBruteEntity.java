package com.github.alexnijjar.beyond_earth.entities.mobs;

import com.github.alexnijjar.beyond_earth.registry.ModEntityTypes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PiglinBruteEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class PygroBruteEntity extends PiglinBruteEntity {

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.45).add(EntityAttributes.GENERIC_MAX_HEALTH, 60).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 9);
    }

    public PygroBruteEntity(EntityType<? extends PiglinBruteEntity> entityType, World world) {
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
