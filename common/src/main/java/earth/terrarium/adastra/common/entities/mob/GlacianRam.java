package earth.terrarium.adastra.common.entities.mob;

import earth.terrarium.adastra.common.entities.mob.goal.EatPermafrostGoal;
import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.adastra.common.registry.ModEntityTypes;
import earth.terrarium.adastra.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

// LEGACY ENTITY. WILL BE REPLACED IN THE FUTURE.
public class GlacianRam extends Animal implements Shearable {

    private static final EntityDataAccessor<Boolean> SHEARED = SynchedEntityData.defineId(GlacianRam.class, EntityDataSerializers.BOOLEAN);
    private int eatPermafrostTimer;
    private EatPermafrostGoal eatPermafrostGoal;

    public GlacianRam(EntityType<? extends GlacianRam> entityType, Level level) {
        super(entityType, level);
        this.getNavigation().setCanFloat(true);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0f);
        this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0f);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 16.0)
            .add(Attributes.MOVEMENT_SPEED, 0.20f);
    }

    @Override
    protected void registerGoals() {
        this.eatPermafrostGoal = new EatPermafrostGoal(this);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, Ingredient.of(ModItems.ICE_SHARD.get()), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, this.eatPermafrostGoal);
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHEARED, false);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ModItems.ICE_SHARD.get());
    }

    @Override
    public void ate() {
        this.setSheared(false);
        if (this.isBaby()) {
            this.ageUp(60);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.BUCKET) && !this.isBaby()) {
            player.playSound(this.getMilkingSound(), 1.0f, 1.0f);
            ItemStack itemStack2 = ItemUtils.createFilledResult(itemStack, player, Items.MILK_BUCKET.getDefaultInstance());
            player.setItemInHand(hand, itemStack2);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            InteractionResult actionResult = super.mobInteract(player, hand);
            if (actionResult.consumesAction() && this.isFood(itemStack)) {
                this.level().playSound(null, this, this.getEatingSound(itemStack), SoundSource.NEUTRAL, 1.0f, Mth.randomBetween(this.level().random, 0.8f, 1.2f));
            }

            return this.shear(player, hand);
        }
    }

    @Override
    protected void customServerAiStep() {
        this.eatPermafrostTimer = this.eatPermafrostGoal.getTimer();
        super.customServerAiStep();
    }

    @Override
    public void aiStep() {
        if (this.level().isClientSide) {
            this.eatPermafrostTimer = Math.max(0, this.eatPermafrostTimer - 1);
        }

        super.aiStep();
    }

    public InteractionResult shear(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.SHEARS)) {
            if (!this.level().isClientSide && this.readyForShearing()) {
                this.shear(player, SoundSource.PLAYERS);
                itemStack.hurtAndBreak(1, player, playerx -> playerx.broadcastBreakEvent(hand));
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.CONSUME;
            }
        } else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    public void shear(SoundSource shearedSoundCategory) {
        this.shear(null, shearedSoundCategory);
    }

    public void shear(@Nullable Player player, SoundSource shearedSoundCategory) {
        for (ItemStack item : this.onSheared(player, shearedSoundCategory)) {
            ItemEntity itemEntity = this.spawnAtLocation(item);
            if (itemEntity != null) {
                itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F));
            }
        }
    }

    public List<ItemStack> onSheared(@Nullable Player player, SoundSource shearedSoundCategory) {
        this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
        if (player != null) this.gameEvent(GameEvent.SHEAR, player);
        this.setSheared(true);
        int i = 1 + this.random.nextInt(3);
        List<ItemStack> items = new ArrayList<>();
        for (int j = 0; j < i; ++j) {
            items.add(new ItemStack(ModBlocks.GLACIAN_FUR.get()));
        }
        return items;
    }

    @Override
    public boolean readyForShearing() {
        return this.isAlive() && !this.isSheared() && !this.isBaby();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("Sheared", this.isSheared());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setSheared(nbt.getBoolean("Sheared"));
    }

    @Override
    public GlacianRam getBreedOffspring(ServerLevel serverWorld, AgeableMob passiveEntity) {
        return ModEntityTypes.GLACIAN_RAM.get().create(serverWorld);
    }

    @Override
    public SoundEvent getEatingSound(ItemStack stack) {
        return SoundEvents.GOAT_EAT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.GOAT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.GOAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.GOAT_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.GOAT_STEP, 0.15F, 1.0F);
    }

    public SoundEvent getMilkingSound() {
        return SoundEvents.GOAT_MILK;
    }

    public boolean isSheared() {
        return this.entityData.get(SHEARED);
    }

    public void setSheared(boolean shear) {
        this.entityData.set(SHEARED, shear);
    }

    @Override
    public void handleEntityEvent(byte status) {
        if (status == 10) {
            this.eatPermafrostTimer = 40;
        } else {
            super.handleEntityEvent(status);
        }

    }

    public float getNeckAngle(float delta) {
        if (this.eatPermafrostTimer <= 0) {
            return 0.0F;
        } else if (this.eatPermafrostTimer >= 4 && this.eatPermafrostTimer <= 36) {
            return 1.0F;
        } else {
            return this.eatPermafrostTimer < 4 ? ((float) this.eatPermafrostTimer - delta) / 4.0F : -((float) (this.eatPermafrostTimer - 40) - delta) / 4.0F;
        }
    }

    public float getHeadAngle(float delta) {
        if (this.eatPermafrostTimer > 4 && this.eatPermafrostTimer <= 36) {
            float f = ((float) (this.eatPermafrostTimer - 4) - delta) / 32.0F;
            return (float) (Math.PI / 5) + 0.21991149F * Mth.sin(f * 28.7F);
        } else {
            return this.eatPermafrostTimer > 0 ? (float) (Math.PI / 5) : this.getXRot() * (float) (Math.PI / 180.0);
        }
    }
}