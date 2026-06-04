package earth.terrarium.adastra.common.entities.vehicles;

import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.client.utils.SoundUtils;
import earth.terrarium.adastra.common.blocks.LaunchPadBlock;
import earth.terrarium.adastra.common.config.AdAstraConfig;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.menus.PlanetsMenu;
import earth.terrarium.adastra.common.menus.base.PlanetsMenuProvider;
import earth.terrarium.adastra.common.menus.vehicles.RocketMenu;
import earth.terrarium.adastra.common.planets.AdAstraData;
import earth.terrarium.adastra.common.registry.*;
import earth.terrarium.adastra.common.tags.ModFluidTags;
import earth.terrarium.adastra.common.utils.FluidUtils;
import earth.terrarium.common_storage_lib.context.impl.ModifyOnlyContext;
import earth.terrarium.common_storage_lib.fluid.FluidApi;
import earth.terrarium.common_storage_lib.fluid.impl.SimpleFluidStorage;
import earth.terrarium.common_storage_lib.fluid.util.FluidProvider;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.resources.fluid.util.FluidAmounts;
import earth.terrarium.common_storage_lib.storage.base.CommonStorage;
import earth.terrarium.common_storage_lib.storage.util.TransferUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Rocket extends Vehicle implements FluidProvider.Entity {

    private static final RocketProperties TIER_1_PROPERTIES = new RocketProperties(1, ModItems.TIER_1_ROCKET.get(), 1.0f, ModFluidTags.TIER_1_ROCKET_FUEL);
    private static final RocketProperties TIER_2_PROPERTIES = new RocketProperties(2, ModItems.TIER_2_ROCKET.get(), 1.0f, ModFluidTags.TIER_2_ROCKET_FUEL);
    private static final RocketProperties TIER_3_PROPERTIES = new RocketProperties(3, ModItems.TIER_3_ROCKET.get(), 1.0f, ModFluidTags.TIER_3_ROCKET_FUEL);
    private static final RocketProperties TIER_4_PROPERTIES = new RocketProperties(4, ModItems.TIER_4_ROCKET.get(), 1.7f, ModFluidTags.TIER_4_ROCKET_FUEL);

    public static final Map<EntityType<?>, RocketProperties> ROCKET_TO_PROPERTIES = Map.of(
        ModEntityTypes.TIER_1_ROCKET.get(), TIER_1_PROPERTIES,
        ModEntityTypes.TIER_2_ROCKET.get(), TIER_2_PROPERTIES,
        ModEntityTypes.TIER_3_ROCKET.get(), TIER_3_PROPERTIES,
        ModEntityTypes.TIER_4_ROCKET.get(), TIER_4_PROPERTIES);

    public static final int COUNTDOWN_LENGTH = 20 * 10; // 10 seconds
    public static final EntityDataAccessor<Boolean> IS_LAUNCHING = SynchedEntityData.defineId(Rocket.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> LAUNCH_TICKS = SynchedEntityData.defineId(Rocket.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> HAS_LAUNCHED = SynchedEntityData.defineId(Rocket.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> IS_IN_VALID_DIMENSION = SynchedEntityData.defineId(Rocket.class, EntityDataSerializers.BOOLEAN);

    public static final EntityDataAccessor<Long> FUEL = SynchedEntityData.defineId(Rocket.class, EntityDataSerializers.LONG);
    public static final EntityDataAccessor<String> FUEL_TYPE = SynchedEntityData.defineId(Rocket.class, EntityDataSerializers.STRING);

    private final RocketProperties properties;
    private final SimpleFluidStorage fluid;

    private boolean launchpadBound;
    private float speed = 0.05f;
    private float angle;

    public boolean startedRocketSound;
    private boolean showFuelMessage = true;

    public Rocket(EntityType<?> type, Level level) {
        this(type, level, ROCKET_TO_PROPERTIES.get(type));
    }

    public Rocket(EntityType<?> type, Level level, RocketProperties properties) {
        super(type, level);
        this.properties = properties;
        this.fluid = new SimpleFluidStorage(this, ModDataManagers.FLUID_CONTENTS, 1, FluidAmounts.toPlatformAmount(3000))
            .filter(0, f -> f.is(properties.fuel));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(IS_LAUNCHING, false);
        builder.define(LAUNCH_TICKS, -1);
        builder.define(HAS_LAUNCHED, false);
        builder.define(IS_IN_VALID_DIMENSION, AdAstraConfig.launchFromAnywhere || AdAstraData.canLaunchFrom(this.level().dimension()));
        builder.define(FUEL, 0L);
        builder.define(FUEL_TYPE, "air");
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        entityData.set(IS_LAUNCHING, compound.getBoolean("Launching"));
        entityData.set(LAUNCH_TICKS, compound.getInt("launchTicks"));
        entityData.set(HAS_LAUNCHED, compound.getBoolean("HasLaunched"));
        speed = compound.getFloat("Speed");
        angle = compound.getFloat("Angle");
        fluid.readSnapshot(ModDataManagers.FLUID_CONTENTS.get(this));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Launching", isLaunching());
        compound.putInt("LaunchTicks", launchTicks());
        compound.putBoolean("HasLaunched", hasLaunched());
        compound.putFloat("Speed", speed);
        compound.putFloat("Angle", angle);
    }

    @Override
    public CommonStorage<FluidResource> getFluids(Direction direction) {
        return fluid;
    }

    public CommonStorage<FluidResource> fluidContainer() {
        return getFluids(Direction.NORTH);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!isLaunching() && !hasLaunched()) {
            super.hurt(source, amount);
        }
        return false;
    }

    @Override
    public ItemStack getDropStack() {
        ModifyOnlyContext context = new ModifyOnlyContext(properties.item.getDefaultInstance());
        var container = context.find(FluidApi.ITEM);
        if (container == null) return context.stack();
        TransferUtil.move(fluidContainer(), container, fluidContainer().getResource(0), fluidContainer().getAmount(0), false);
        return context.stack();
    }

    public int tier() {
        return this.properties.tier;
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float scale) {
        return new Vec3(0, this.properties.ridingOffset + 0.3f, 0);
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        Vec3 location = super.getDismountLocationForPassenger(passenger)
            .add(passenger.getLookAngle().multiply(2, 0, 2).normalize());
        for (int i = 0; i < 6; i++) {
            if (level().getBlockState(BlockPos.containing(location)).isAir()) {
                location = location.subtract(0, 1, 0);
            } else break;
        }
        return location;
    }

    @Override
    public boolean isSafeToDismount(Player player) {
        return !isLaunching() && !hasLaunched();
    }

    @Override
    public boolean shouldSit() {
        return false;
    }

    @Override
    public boolean zoomOutCameraInThirdPerson() {
        return true;
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        passenger.setYRot(getYRot());
        passenger.setYHeadRot(getYHeadRot());
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction callback) {
        super.positionRider(passenger, callback);
        passenger.setYRot(passenger.getYRot() + angle);
        passenger.setYHeadRot(passenger.getYHeadRot() + angle);
    }

    @Override
    public @NotNull InteractionResult interact(Player player, InteractionHand hand) {
        if (player.equals(getControllingPassenger())) return InteractionResult.PASS;
        return super.interact(player, hand);
    }

    @Override
    public void tick() {
        super.tick();
        launchPadTick();
        if (canLaunch()) {
            initiateLaunchSequence();
            showFuelMessage = false;
        } else if (showFuelMessage && !level().isClientSide() && passengerHasSpaceDown() && getControllingPassenger() instanceof Player player) {
            player.displayClientMessage(ConstantComponents.NOT_ENOUGH_FUEL, true);
        }

        if (isLaunching()) {
            entityData.set(LAUNCH_TICKS, launchTicks() - 1);
            if (launchTicks() <= 0) launch();
            spawnSmokeParticles();
        } else if (hasLaunched()) flightTick();
        if (!level().isClientSide()) {
            FluidUtils.moveItemToContainer(inventory, fluidContainer(), 0, 1, 0);
            FluidUtils.moveContainerToItem(inventory, fluidContainer(), 0, 1, 0);

            var resourceStack = fluidContainer().getContents(0);
            entityData.set(FUEL, resourceStack.amount());
            entityData.set(FUEL_TYPE, BuiltInRegistries.FLUID.getKey(resourceStack.resource().getType()).toString());
        }
    }

    private void launchPadTick() {
        if (level().isClientSide() || tickCount % 5 != 0) return;
        if (isLaunching() || hasLaunched()) return;
        var state = level().getBlockState(blockPosition());
        if (!state.hasProperty(LaunchPadBlock.PART)) {
            if (launchpadBound) {
                drop();
                playSound(SoundEvents.NETHERITE_BLOCK_BREAK);
                discard();
            }
        } else {
            launchpadBound = true;
            if (state.getValue(LaunchPadBlock.POWERED)) {
                if (hasEnoughFuel()) initiateLaunchSequence();
            }
        }
    }

    private void flightTick() {
        if (!level().isClientSide() && getY() >= AdAstraConfig.atmosphereLeave) {
            if (getControllingPassenger() instanceof ServerPlayer player) {
                if (!(player.containerMenu instanceof PlanetsMenu)) {
                    openPlanetsScreen(player);
                }
            } else explode();
            return;
        }

        float xxa = -xxa(); // right/left

        if (xxa != 0) {
            angle += xxa * 1;
        } else {
            angle *= 0.9f;
        }

        if (speed < 1) {
            speed += 0.005f;
        }

        // clamp turning
        angle = Mth.clamp(angle, -3, 3);

        setYRot(getYRot() + angle);

        var delta = getDeltaMovement();
        setDeltaMovement(
            delta.x(),
            speed,
            delta.z()
        );

        if (level().isClientSide() && !startedRocketSound) {
            startedRocketSound = true;
            SoundUtils.playRocketSound(this);
        }

        spawnRocketParticles();
        burnEntitiesUnderRocket();
        if (isObstructed()) explode();
    }


    public boolean canLaunch() {
        if (isLaunching() || hasLaunched()) return false;
        if (!AdAstraConfig.launchFromAnywhere && !entityData.get(IS_IN_VALID_DIMENSION)) {
            if (getControllingPassenger() instanceof ServerPlayer player) {
                player.displayClientMessage(ConstantComponents.INVALID_LAUNCHING_DIMENSION, true);
            }
            this.showFuelMessage = false;
            return false;
        }
        if (!hasEnoughFuel()) return false;
        return passengerHasSpaceDown();
    }

    public void initiateLaunchSequence() {
        entityData.set(IS_LAUNCHING, true);
        entityData.set(LAUNCH_TICKS, COUNTDOWN_LENGTH);
        level().playSound(null, blockPosition(), ModSoundEvents.ROCKET_LAUNCH.get(), SoundSource.AMBIENT, 10, 1);
        consumeFuel(false);
    }

    public void launch() {
        entityData.set(HAS_LAUNCHED, true);
        entityData.set(IS_LAUNCHING, false);
        entityData.set(LAUNCH_TICKS, -1);
    }

    public boolean isLaunching() {
        return this.entityData.get(IS_LAUNCHING);
    }

    public int launchTicks() {
        return this.entityData.get(LAUNCH_TICKS);
    }

    public boolean hasLaunched() {
        return this.entityData.get(HAS_LAUNCHED);
    }

    public void spawnSmokeParticles() {
        if (!level().isClientSide()) return;
        for (int i = 0; i < 6; i++) {
            level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                getX(), getY(), getZ(),
                Mth.nextDouble(level().random, -0.05, 0.05),
                Mth.nextDouble(level().random, -0.05, 0.05),
                Mth.nextDouble(level().random, -0.05, 0.05));
        }
    }

    public void spawnRocketParticles() {
        if (!level().isClientSide()) return;
        for (int i = 0; i < 20; i++) {
            level().addParticle(ModParticleTypes.LARGE_FLAME.get(),
                getX(), getY() - 0.75, getZ(),
                Mth.nextDouble(level().random, -0.05, 0.05),
                Mth.nextDouble(level().random, -0.05, 0.05),
                Mth.nextDouble(level().random, -0.05, 0.05));
        }

        for (int i = 0; i < 5; i++) {
            level().addParticle(ModParticleTypes.LARGE_SMOKE.get(),
                getX(), getY() - 0.75, getZ(),
                Mth.nextDouble(level().random, -0.05, 0.05),
                Mth.nextDouble(level().random, -0.05, 0.05),
                Mth.nextDouble(level().random, -0.05, 0.05));
        }
    }

    public void burnEntitiesUnderRocket() {
        if (level().isClientSide()) return;
        for (var entity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox()
            .inflate(2, 30, 2)
            .move(0, -37, 0), e -> true)) {
            if (entity.equals(getControllingPassenger())) continue;
            entity.setRemainingFireTicks(10 * 20);
            entity.hurt(ModDamageSources.create(level(), ModDamageSources.ROCKET_FLAMES), 10);
        }
    }

    public boolean isObstructed() {
        return false;
    }

    public void explode() {
        level().explode(
            this,
            getX(), getY(), getZ(),
            7 + tier() * 2,
            OxygenApi.API.hasOxygen(this.level()),
            Level.ExplosionInteraction.TNT);
        discard();
    }

    public boolean consumeFuel(boolean simulate) {
        if (level().isClientSide()) return false;
        long buckets = FluidAmounts.toPlatformAmount(fluidContainer().getResource(0).is(ModFluidTags.EFFICIENT_FUEL) ? AdAstraConfig.launchEfficientFuelCost : AdAstraConfig.launchFuelCost);
        return fluidContainer().extract(fluidContainer().getResource(0), buckets, simulate) >= buckets;
    }

    public boolean hasEnoughFuel() {
        return consumeFuel(true);
    }

    @Override
    public int getInventorySize() {
        return 10;
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new RocketMenu(containerId, inventory, this);
    }

    public ResourceStack<FluidResource> fluid() {
        return new ResourceStack<>(
            FluidResource.of(BuiltInRegistries.FLUID.get(ResourceLocation.tryParse(entityData.get(FUEL_TYPE)))),
            entityData.get(FUEL));
    }

    public void openPlanetsScreen(ServerPlayer player) {
        player.openMenu(new PlanetsMenuProvider());
        var packet = new ClientboundStopSoundPacket(BuiltInRegistries.SOUND_EVENT
            .getKey(ModSoundEvents.ROCKET.get()), SoundSource.AMBIENT);
        player.connection.send(packet);
    }

    public record RocketProperties(int tier, Item item, float ridingOffset, TagKey<Fluid> fuel) {}
}
