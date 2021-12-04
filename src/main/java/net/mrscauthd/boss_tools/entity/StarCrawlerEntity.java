package net.mrscauthd.boss_tools.entity;

import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.util.SoundEvent;
import net.mrscauthd.boss_tools.events.Config;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;

import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.network.IPacket;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.EntityType;

public class StarCrawlerEntity extends MonsterEntity {
	public StarCrawlerEntity(EntityType type, World world) {
		super(type, world);
		this.experienceValue = 5;
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.func_233666_p_()
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.4)
				.createMutableAttribute(Attributes.MAX_HEALTH, 40)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 9);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.8, false));
		this.goalSelector.addGoal(2, new RandomWalkingGoal(this, 0.6));
		this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected boolean isDespawnPeaceful() {
		return false;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.turtle.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.turtle.death"));
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (source.getImmediateSource() instanceof SpectralArrowEntity)
			return false;
		if (source.getImmediateSource() instanceof ArrowEntity)
			return false;
		if (source == DamageSource.CACTUS)
			return false;
		return super.attackEntityFrom(source, amount);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		if (!Config.StarCrawlerSpawn) {
			if (!this.world.isRemote()) {
				this.remove();
			}
		}
	}
}
