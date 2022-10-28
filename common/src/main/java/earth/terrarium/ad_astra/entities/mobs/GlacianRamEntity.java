package earth.terrarium.ad_astra.entities.mobs;

import earth.terrarium.ad_astra.entities.mobs.goal.EatPermafrostGoal;
import earth.terrarium.ad_astra.registry.ModBlocks;
import earth.terrarium.ad_astra.registry.ModEntityTypes;
import earth.terrarium.ad_astra.registry.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class GlacianRamEntity extends AnimalEntity implements Shearable {

    private static final TrackedData<Boolean> SHEARED = DataTracker.registerData(GlacianRamEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private int eatPermafrostTimer;
    private EatPermafrostGoal eatPermafrostGoal;

    public GlacianRamEntity(EntityType<? extends GlacianRamEntity> entityType, World world) {
        super(entityType, world);
        this.getNavigation().setCanSwim(true);
        this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, -1.0f);
        this.setPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0f);
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.20f);
    }

    @Override
    protected void initGoals() {
        this.eatPermafrostGoal = new EatPermafrostGoal(this);
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(3, new TemptGoal(this, 1.1, Ingredient.ofItems(ModItems.ICE_SHARD.get()), false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(5, this.eatPermafrostGoal);
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SHEARED, false);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(ModItems.ICE_SHARD.get());
    }

    @Override
    public void onEatingGrass() {
        this.setSheared(false);
        if (this.isBaby()) {
            this.growUp(60);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.BUCKET) && !this.isBaby()) {
            player.playSound(this.getMilkingSound(), 1.0f, 1.0f);
            ItemStack itemStack2 = ItemUsage.exchangeStack(itemStack, player, Items.MILK_BUCKET.getDefaultStack());
            player.setStackInHand(hand, itemStack2);
            return ActionResult.success(this.world.isClient);
        } else {
            ActionResult actionResult = super.interactMob(player, hand);
            if (actionResult.isAccepted() && this.isBreedingItem(itemStack)) {
                this.world.playSoundFromEntity(null, this, this.getEatSound(itemStack), SoundCategory.NEUTRAL, 1.0f, MathHelper.nextBetween(this.world.random, 0.8f, 1.2f));
            }

            return this.shear(player, hand);
        }
    }

    @Override
    protected void mobTick() {
        this.eatPermafrostTimer = this.eatPermafrostGoal.getTimer();
        super.mobTick();
    }

    @Override
    public void tickMovement() {
        if (this.world.isClient) {
            this.eatPermafrostTimer = Math.max(0, this.eatPermafrostTimer - 1);
        }

        super.tickMovement();
    }

    public ActionResult shear(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.SHEARS)) {
            if (!this.world.isClient && this.isShearable()) {
                this.sheared(SoundCategory.PLAYERS);
                this.emitGameEvent(GameEvent.SHEAR, player);
                itemStack.damage(1, player, playerx -> playerx.sendToolBreakStatus(hand));
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.CONSUME;
            }
        } else {
            return super.interactMob(player, hand);
        }
    }

    @Override
    public void sheared(SoundCategory shearedSoundCategory) {
        this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
        this.setSheared(true);
        int i = 1 + this.random.nextInt(3);

        for (int j = 0; j < i; ++j) {
            ItemEntity itemEntity = this.dropItem((ItemConvertible) ModBlocks.GLACIAN_FUR);
            if (itemEntity != null) {
                itemEntity.setVelocity(itemEntity.getVelocity().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F));
            }
        }

    }

    @Override
    public boolean isShearable() {
        return this.isAlive() && !this.isSheared() && !this.isBaby();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Sheared", this.isSheared());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setSheared(nbt.getBoolean("Sheared"));
    }

    @Override
    public GlacianRamEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        return ModEntityTypes.GLACIAN_RAM.get().create(serverWorld);
    }

    @Override
    public SoundEvent getEatSound(ItemStack stack) {
        return SoundEvents.ENTITY_GOAT_EAT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_GOAT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_GOAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GOAT_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_GOAT_STEP, 0.15F, 1.0F);
    }

    public SoundEvent getMilkingSound() {
        return SoundEvents.ENTITY_GOAT_MILK;
    }

    public boolean isSheared() {
        return this.dataTracker.get(SHEARED);
    }

    public void setSheared(boolean shear) {
        this.dataTracker.set(SHEARED, shear);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 10) {
            this.eatPermafrostTimer = 40;
        } else {
            super.handleStatus(status);
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
            return (float) (Math.PI / 5) + 0.21991149F * MathHelper.sin(f * 28.7F);
        } else {
            return this.eatPermafrostTimer > 0 ? (float) (Math.PI / 5) : this.getPitch() * (float) (Math.PI / 180.0);
        }
    }
}