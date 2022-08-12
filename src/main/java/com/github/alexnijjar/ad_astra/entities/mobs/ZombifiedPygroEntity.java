package com.github.alexnijjar.ad_astra.entities.mobs;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.world.World;

public class ZombifiedPygroEntity extends ZombifiedPiglinEntity {

	public ZombifiedPygroEntity(EntityType<? extends ZombifiedPiglinEntity> entityType, World world) {
		super(entityType, world);
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return ZombieEntity.createZombieAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 28.0).add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS, 0.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.26f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0);
	}

	@Override
	public boolean canImmediatelyDespawn(double distanceSquared) {
		return false;
	}
}
