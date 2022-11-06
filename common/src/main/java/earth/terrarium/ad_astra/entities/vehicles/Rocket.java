package earth.terrarium.ad_astra.entities.vehicles;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.blocks.door.LocationState;
import earth.terrarium.ad_astra.blocks.launchpad.LaunchPad;
import earth.terrarium.ad_astra.items.armour.NetheriteSpaceSuit;
import earth.terrarium.ad_astra.registry.*;
import earth.terrarium.ad_astra.screen.PlanetSelectionMenuProvider;
import earth.terrarium.ad_astra.screen.menu.PlanetSelectionScreenHandler;
import earth.terrarium.ad_astra.util.ModKeyBindings;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import earth.terrarium.ad_astra.util.ModUtils;
import earth.terrarium.botarium.api.menu.MenuHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class Rocket extends Vehicle {

    protected static final EntityDataAccessor<Boolean> HAS_LAUNCH_PAD = SynchedEntityData.defineId(Rocket.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(Rocket.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> COUNTDOWN_TICKS = SynchedEntityData.defineId(Rocket.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> TIER = SynchedEntityData.defineId(Rocket.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(Rocket.class, EntityDataSerializers.INT);

    private Player lastRider;

    public Rocket(EntityType<?> type, Level level, int tier) {
        super(type, level);
        this.setTier(tier);
    }

    public static void stopRocketSoundForRider(ServerPlayer rider) {
        ClientboundStopSoundPacket stopSoundS2CPacket = new ClientboundStopSoundPacket(new ModResourceLocation("rocket_fly"), SoundSource.AMBIENT);
        rider.connection.send(stopSoundS2CPacket);
    }

    @SuppressWarnings("deprecation")
    public static long getRequiredAmountForLaunch(Fluid fluid) {
        if (fluid.is(ModTags.EFFICIENT_FUELS)) {
            return AdAstra.CONFIG.rocket.efficientFuelLaunchCost;
        } else {
            return AdAstra.CONFIG.rocket.fuelLaunchCost;
        }
    }

    @Override
    public int getInventorySize() {
        return 10;
    }

    @Override
    public long getTankSize() {
        return AdAstra.CONFIG.rocket.tankSize;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        super.interact(player, hand);
        this.openInventory(player);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_LAUNCH_PAD, false);
        this.entityData.define(FLYING, false);
        this.entityData.define(COUNTDOWN_TICKS, 0);
        this.entityData.define(TIER, 0);
        this.entityData.define(PHASE, 0);
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        super.getDismountLocationForPassenger(passenger);
        int exitDirection = Math.round(passenger.getYRot() / 90) * 90;
        Vec3 pos = passenger.position();

        // Set the position to teleport out of the rocket
        Vec3 exitPos = switch (exitDirection) {
            case 0, 360 -> new Vec3(pos.x(), pos.y(), pos.z() + 1.5f);
            case 90 -> new Vec3(pos.x() - 1.5f, pos.y(), pos.z());
            case -90 -> new Vec3(pos.x() + 1.5f, pos.y(), pos.z());
            case -180, 180 -> new Vec3(pos.x(), pos.y(), pos.z() - 1.5f);
            default -> throw new IllegalArgumentException("Unexpected value: " + exitDirection);
        };

        // Place the rider up to 3 blocks below the rocket
        int checks = 3;
        BlockPos exitBlockPos = new BlockPos(exitPos);
        while (!this.level.getBlockState(exitBlockPos).isRedstoneConductor(this.level, exitBlockPos) && checks > 0) {
            exitBlockPos = exitBlockPos.below();
            checks--;
        }

        BlockState exitBlockState = this.level.getBlockState(exitBlockPos.above());
        VoxelShape collisionShape = exitBlockState.getCollisionShape(this.level, exitBlockPos);
        double yOffset = 0.0;
        if (!collisionShape.isEmpty()) {
            yOffset = exitBlockState.getCollisionShape(this.level, exitBlockPos).bounds().getYsize();
        }
        return new Vec3(exitBlockPos.getX(), exitBlockPos.above().getY() + yOffset, exitBlockPos.getZ());
    }

    public void assignLaunchPad(boolean value) {
        this.setLaunchPad(value);
    }

    @Override
    public void tick() {
        super.tick();

        // Rotate the rocket when the player strafes left or right.
        if (this.getFirstPassenger() instanceof Player player) {
            this.lastRider = player;
            if (ModKeyBindings.leftKeyDown(player)) {
                this.rotateRocketAndPassengers(-1.0f);
            }
            if (ModKeyBindings.rightKeyDown(player)) {
                this.rotateRocketAndPassengers(1.0f);
            }
        }

        if (this.getY() >= AdAstra.CONFIG.rocket.atmosphereLeave || this.getTicksFrozen() > 1000) {
            this.setFlying(true);
        }
        if (!this.isFlying()) {
            if (this.hasLaunchPad()) {
                BlockState below = level.getBlockState(this.blockPosition());
                if (!(below.getBlock() instanceof LaunchPad)) {
                    this.drop();
                } else if (below.getBlock() instanceof LaunchPad pad) {
                    if (!below.getValue(LaunchPad.LOCATION).equals(LocationState.CENTER)) {
                        this.drop();
                    }
                }
            }
        } else {
            this.setCountdownTicks(this.getCountdownTicks() - 1);

            // Phase one: the rocket is counting down, about to launch.
            if (this.getCountdownTicks() > 0) {
                this.spawnSmokeParticles();
                this.setPhase(1);
                // Phase two: the rocket has launched.
            } else if (this.getY() < AdAstra.CONFIG.rocket.atmosphereLeave) {
                this.spawnAfterburnerParticles();
                this.burnEntitiesUnderRocket();
                this.travel();
                if (this.getCountdownTicks() < -30) {
                    this.explodeIfStopped();
                }
                this.setPhase(2);
                // Phase three: the rocket has reached the required height.
            } else if (this.getY() >= AdAstra.CONFIG.rocket.atmosphereLeave) {
                openPlanetSelectionGui();
                this.setPhase(3);
            }
        }
        if (this.isEyeInFluid(FluidTags.LAVA)) {
            this.explode(0.45f);
        }
    }

    @Override
    public void drop() {
        if (!this.isFlying()) {
            super.drop();
        }
    }

    public void openPlanetSelectionGui() {
        if (!this.isVehicle()) {
            if (!this.level.isClientSide) {
                if (this.lastRider != null) {
                    ModCriteria.ROCKET_DESTROYED.trigger((ServerPlayer) this.lastRider);
                }
            }
            this.explode();
            return;
        }
        this.getPassengers().forEach(passenger -> {
            if (passenger instanceof Player player) {
                if (!(player.containerMenu instanceof PlanetSelectionScreenHandler)) {
                    if (!this.level.isClientSide) {
                        MenuHooks.openMenu((ServerPlayer) player, new PlanetSelectionMenuProvider(this.getTier()));
                        stopRocketSoundForRider((ServerPlayer) player);
                    }
                }
            }
        });
    }

    // When the countdown is in progress and the rocket engine is warming up, releasing smoke.
    public void spawnSmokeParticles() {
        if (this.level instanceof ServerLevel serverWorld) {
            Vec3 pos = this.position();
            ModUtils.spawnForcedParticles(serverWorld, ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.x(), pos.y(), pos.z(), 6, 0.1, 0.1, 0.1, 0.023);
        }
    }

    // When the rocket launches and is burning fuel.
    public void spawnAfterburnerParticles() {
        if (this.level instanceof ServerLevel serverWorld) {
            Vec3 pos = this.position();

            ModUtils.spawnForcedParticles(serverWorld, ModParticleTypes.LARGE_FLAME.get(), pos.x(), pos.y(), pos.z(), 20, 0.1, 0.1, 0.1, 0.001);
            ModUtils.spawnForcedParticles(serverWorld, ModParticleTypes.LARGE_SMOKE.get(), pos.x(), pos.y(), pos.z(), 10, 0.1, 0.1, 0.1, 0.001);
        }
    }

    // Burn entities under the rocket flames.
    private void burnEntitiesUnderRocket() {
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2, 30, 2).move(0, -37, 0), entity -> true);
        for (LivingEntity entity : entities) {
            if (NetheriteSpaceSuit.hasFullSet(entity) || (entity.getVehicle() != null && entity.getVehicle().equals(this))) {
                continue;
            }
            if (AdAstra.CONFIG.rocket.entitiesBurnUnderRocket && !entity.fireImmune()) {
                entity.setSecondsOnFire(10);
                entity.hurt(ModDamageSource.ROCKET_FLAMES, 10);
                BlockState belowBlock = this.level.getBlockState(entity.blockPosition().below());
                if (belowBlock.isCollisionShapeFullBlock(level, entity.blockPosition().below()) && belowBlock.isAir()) {
                    this.level.setBlockAndUpdate(entity.blockPosition(), Blocks.FIRE.defaultBlockState());
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void travel() {
        double multiplier = (getTankHolder().getFluid().is(ModTags.EFFICIENT_FUELS) ? 2.5 : 1.0);
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, AdAstra.CONFIG.rocket.acceleration * multiplier, 0.0));
        }

        if (this.getDeltaMovement().y() > AdAstra.CONFIG.rocket.maxSpeed * multiplier) {
            this.setDeltaMovement(0.0, AdAstra.CONFIG.rocket.maxSpeed, 0.0);
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    public void initiateLaunchSequenceFromServer() {
        initiateLaunchSequence();
        level.playSound(null, this.blockPosition(), ModSoundEvents.ROCKET_LAUNCH_SOUND_EVENT.get(), SoundSource.AMBIENT, 1, 1);
    }

    // Synonymous on client and server.
    public void initiateLaunchSequence() {

        this.setFlying(true);
        this.setCountdownTicks(AdAstra.CONFIG.rocket.countDownTicks);
        // For shaking
        this.setTicksFrozen(Integer.MAX_VALUE);
        getTankHolder().setAmount(getRequiredAmountForLaunch(getTankHolder().getFluid()));
    }

    public boolean hasLaunchPad() {
        return this.entityData.get(HAS_LAUNCH_PAD);
    }

    public void setLaunchPad(boolean value) {
        this.entityData.set(HAS_LAUNCH_PAD, value);
    }

    public boolean isFlying() {
        return this.entityData.get(FLYING);
    }

    public void setFlying(boolean value) {
        this.entityData.set(FLYING, value);
    }

    public int getCountdownTicks() {
        return this.entityData.get(COUNTDOWN_TICKS);
    }

    public void setCountdownTicks(int value) {
        this.entityData.set(COUNTDOWN_TICKS, value);
    }

    public int getPhase() {
        return this.entityData.get(PHASE);
    }

    public void setPhase(int value) {
        this.entityData.set(PHASE, value);
    }

    public int getTier() {
        return this.entityData.get(TIER);
    }

    public void setTier(int value) {
        this.entityData.set(TIER, value);
    }

    public int getCountdownSeconds() {
        return (this.getCountdownTicks() + 20) / 20;
    }

    @Override
    public boolean doHighFov() {
        return true;
    }

    @Override
    public boolean fullyConcealsRider() {
        return true;
    }

    @Override
    public boolean canRiderTakeFallDamage() {
        return false;
    }

    @Override
    public boolean renderPlanetBar() {
        return true;
    }

    @Override
    public void doGravity() {
        BlockState belowBlock = this.level.getBlockState(this.blockPosition());

        if (belowBlock.getBlock() instanceof LaunchPad && belowBlock.getValue(LaunchPad.LOCATION).equals(LocationState.CENTER)) {
            return;
        }

        if (this.getPhase() == 0 || this.getPhase() == 1) {
            super.doGravity();
        }
    }

    // Explode on block collision.
    private void explodeIfStopped() {
        if (this.getDeltaMovement().y() < 0.05 && this.getDeltaMovement().y() > -0.000001) {
            this.explode();
        }
    }

    public void explode() {
        if (this.level instanceof ServerLevel serverWorld) {
            // Increase explosion power the higher the tier.
            float tierMultiplier = 1 + this.getTier() * 0.25f;
            if (this.getDeltaMovement().y() > 4) {
                // Increase explosion power at a high speed.
                tierMultiplier *= 1.25f;
            }
            if (this.getY() <= AdAstra.CONFIG.rocket.atmosphereLeave) {
                serverWorld.players().forEach(Rocket::stopRocketSoundForRider);
            }
            this.explode(tierMultiplier);
        }
    }

    public void rotateRocketAndPassengers(float rotation) {
        ModUtils.rotateVehicleYaw(this, this.getYRot() + rotation);
        for (Entity passenger : this.getPassengers()) {
            passenger.setYRot(passenger.getYRot() + rotation);
        }
    }
}