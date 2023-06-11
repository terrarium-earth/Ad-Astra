package earth.terrarium.ad_astra.common.entity.mob;

import earth.terrarium.ad_astra.common.config.SpawnConfig;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class MartianRaptor extends Monster {

    private int movementCooldownTicks;

    public MartianRaptor(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.MAX_HEALTH, 26).add(Attributes.ATTACK_DAMAGE, 8);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LeapAtTargetGoal(this, 0.2f));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.STRIDER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.STRIDER_DEATH;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        this.movementCooldownTicks = 10;
        this.level().broadcastEntityEvent(this, (byte) 4);
        return super.doHurtTarget(target);
    }

    @Override
    public void handleEntityEvent(byte status) {
        if (status == 4) {
            this.movementCooldownTicks = 10;
        } else {
            super.handleEntityEvent(status);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (movementCooldownTicks > 0) {
            movementCooldownTicks--;
        }
    }

    public int getMovementCooldownTicks() {
        return this.movementCooldownTicks;
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor level, MobSpawnType spawnReason) {
        if (!SpawnConfig.spawnMartianRaptors) {
            return false;
        }
        return super.checkSpawnRules(level, spawnReason);
    }
}
