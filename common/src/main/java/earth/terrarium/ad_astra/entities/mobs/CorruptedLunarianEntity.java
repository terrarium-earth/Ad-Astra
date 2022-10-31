package earth.terrarium.ad_astra.entities.mobs;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.entities.projectiles.IceSpitEntity;
import earth.terrarium.ad_astra.registry.ModEntityTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class CorruptedLunarianEntity extends Monster implements RangedAttackMob {

    public CorruptedLunarianEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.MAX_HEALTH, 20).add(Attributes.ATTACK_DAMAGE, 3);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, Villager.class, false));
        this.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(this, LunarianWanderingTraderEntity.class, false));
        this.targetSelector.addGoal(9, new NearestAttackableTargetGoal<>(this, AbstractGolem.class, false));
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.25f, 20, 15) {
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
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.PILLAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PILLAGER_DEATH;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float pullProgress) {
        IceSpitEntity projectile = new IceSpitEntity(ModEntityTypes.ICE_SPIT.get(), this, this.level);

        double targetX = target.getX() - this.getX();
        double targetY = target.getY(0.3333333333333333) - projectile.getY() - 1.1f;
        double targetZ = target.getZ() - this.getZ();
        double calculated = Math.sqrt(targetX * targetX + targetZ * targetZ);
        projectile.shoot(targetX, targetY + calculated * (double) 0.2f, targetZ, 1.6f, 14 - this.level.getDifficulty().getId() * 4);

        projectile.setSilent(true);
        this.level.addFreshEntity(projectile);
        this.level.playSound(null, this.blockPosition(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1, 1);
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor level, MobSpawnType spawnReason) {
        return AdAstra.CONFIG.general.spawnCorruptedLunarians;
    }
}
