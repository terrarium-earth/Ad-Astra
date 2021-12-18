package net.mrscauthd.beyond_earth.entity;

import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.mrscauthd.beyond_earth.entity.alien.AlienEntity;
import net.mrscauthd.beyond_earth.events.Config;

import net.minecraftforge.registries.ForgeRegistries;

public class AlienZombieEntity extends Monster implements RangedAttackMob {
	public AlienZombieEntity(EntityType type, Level world) {
		super(type, world);
		this.xpReward = 5;
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Mob.createMobAttributes()
		.add(Attributes.MOVEMENT_SPEED, 0.3)
		.add(Attributes.MAX_HEALTH, 20)
		.add(Attributes.ATTACK_DAMAGE, 3);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Player.class, false, false));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, AlienEntity.class, false, false));
		this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.25, 20, 15) {
			@Override
			public boolean canContinueToUse() {
				return this.canUse();
			}
		});
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_33034_) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.pillager.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.pillager.death"));
	}

	@Override
	public void performRangedAttack(LivingEntity p_33317_, float p_33318_) {
		AlienSpitEntity.shoot(this, p_33317_, 2);
	}

	@Override
	public void tick() {
		super.tick();
		if (!Config.AlienZombieSpawn) {
			if (!this.level.isClientSide) {
				this.remove(RemovalReason.DISCARDED);
			}
		}
	}
}