package com.github.alexnijjar.ad_astra.entities.mobs;

import org.jetbrains.annotations.Nullable;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.registry.ModEntityTypes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class MoglerEntity extends HoglinEntity {

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 50.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4f).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6f).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0);
	}

	public MoglerEntity(EntityType<? extends HoglinEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public boolean canImmediatelyDespawn(double distanceSquared) {
		return false;
	}

	@Override
	@Nullable
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		MoglerEntity moglerEntity = ModEntityTypes.MOGLER.get().create(world);
		if (moglerEntity != null) {
			moglerEntity.setPersistent();
		}
		return moglerEntity;
	}

	@Override
	protected void zombify(ServerWorld world) {
		ZombifiedMoglerEntity zombifiedMoglerEntity = this.convertTo(ModEntityTypes.ZOMBIFIED_MOGLER.get(), true);
		if (zombifiedMoglerEntity != null) {
			zombifiedMoglerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0));
		}
	}

	@Override
	public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
		if (!AdAstra.CONFIG.general.spawnMoglers) {
			return false;
		}
		return super.canSpawn(world, spawnReason);
	}
}
