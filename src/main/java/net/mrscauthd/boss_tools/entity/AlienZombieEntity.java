package net.mrscauthd.boss_tools.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.util.SoundEvent;
import net.mrscauthd.boss_tools.entity.alien.AlienEntity;
import net.mrscauthd.boss_tools.events.Config;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;

import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.network.IPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.monster.MonsterEntity;

public class AlienZombieEntity extends MonsterEntity implements IRangedAttackMob {
	public AlienZombieEntity(EntityType type, World world) {
		super(type, world);
		this.experienceValue = 5;
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.func_233666_p_()
		.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3)
		.createMutableAttribute(Attributes.MAX_HEALTH, 20)
		.createMutableAttribute(Attributes.ATTACK_DAMAGE, 3);
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(3, new RandomWalkingGoal(this, 0.8));
		this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, PlayerEntity.class, false, false));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, AlienEntity.class, false, false));
		this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.25, 20, 15) {
			@Override
			public boolean shouldContinueExecuting() {
				return this.shouldExecute();
			}
		});
	}

	@Override
	public CreatureAttribute getCreatureAttribute() {
		return CreatureAttribute.UNDEAD;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.pillager.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.pillager.death"));
	}

	@Override
	public void attackEntityWithRangedAttack(LivingEntity target, float flval) {
		AlienSpitEntity.shoot(this, target, 2);
	}

	@Override
	public void tick() {
		super.tick();
		if (!Config.AlienZombieSpawn) {
			if (!this.world.isRemote()) {
				this.remove();
			}
		}
	}
}