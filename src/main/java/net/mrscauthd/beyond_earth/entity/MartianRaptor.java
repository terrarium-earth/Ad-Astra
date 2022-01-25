package net.mrscauthd.beyond_earth.entity;

import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.mrscauthd.beyond_earth.events.Config;

public class MartianRaptor extends Monster {

    private float AttackAnim = 0;

    public MartianRaptor(EntityType type, Level world) {
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
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Player.class, false, false));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return SoundEvents.STRIDER_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.STRIDER_DEATH;
    }

    @Override
    public boolean doHurtTarget(Entity p_21372_) {
        this.AttackAnim = 10;
        this.level.broadcastEntityEvent(this, (byte)4);
        return super.doHurtTarget(p_21372_);
    }

    @Override
    public void handleEntityEvent(byte p_28844_) {
        if (p_28844_ == 4) {
            this.AttackAnim = 10;
        } else {
            super.handleEntityEvent(p_28844_);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.AttackAnim > 0) {
            --this.AttackAnim;
        }
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (!Config.MARTIAN_RAPTOR_SPAWN.get()) {
            if (!this.level.isClientSide) {
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    public float getAttackAnim() {
        return AttackAnim;
    }
}
