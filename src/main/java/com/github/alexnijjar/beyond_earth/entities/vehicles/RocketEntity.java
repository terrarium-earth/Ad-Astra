package com.github.alexnijjar.beyond_earth.entities.vehicles;

import java.util.List;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.blocks.launch_pad.RocketLaunchPad;
import com.github.alexnijjar.beyond_earth.gui.PlanetSelectionScreenHandlerFactory;
import com.github.alexnijjar.beyond_earth.gui.screen_handlers.PlanetSelectionScreenHandler;
import com.github.alexnijjar.beyond_earth.registry.ModParticleTypes;
import com.github.alexnijjar.beyond_earth.registry.ModSounds;
import com.github.alexnijjar.beyond_earth.util.ModDamageSource;
import com.github.alexnijjar.beyond_earth.util.ModKeyBindings;
import com.github.alexnijjar.beyond_earth.util.ModUtils;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RocketEntity extends VehicleEntity {

    // 10 seconds.
    public static final int ATMOSPHERE_LEAVE = BeyondEarth.CONFIG.mainConfig.rocketAtmosphereLeave;
    public static final int MAX_COUNTDOWN_TICKS = BeyondEarth.CONFIG.mainConfig.rocketCountDownTicks;
    public static final double ROCKET_ACCELERATION = BeyondEarth.CONFIG.mainConfig.rocketAcceleration;
    public static final double ROCKET_MAX_SPEED = BeyondEarth.CONFIG.mainConfig.rocketMaxSpeed;

    protected static final TrackedData<Boolean> HAS_LAUNCH_PAD = DataTracker.registerData(RocketEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected static final TrackedData<Boolean> FLYING = DataTracker.registerData(RocketEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected static final TrackedData<Integer> COUNTDOWN_TICKS = DataTracker.registerData(RocketEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> TIER = DataTracker.registerData(RocketEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> PHASE = DataTracker.registerData(RocketEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public RocketEntity(EntityType<?> type, World world, int tier) {
        super(type, world);
        this.setTier(tier);
    }

    @Override
    public int getInventorySize() {
        return 10;
    }

    @Override
    public long getTankSize() {
        return BeyondEarth.CONFIG.mainConfig.rocketTankBuckets;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        super.interact(player, hand);
        this.openInventory(player);
        return ActionResult.SUCCESS;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HAS_LAUNCH_PAD, false);
        this.dataTracker.startTracking(FLYING, false);
        this.dataTracker.startTracking(COUNTDOWN_TICKS, 0);
        this.dataTracker.startTracking(TIER, 0);
        this.dataTracker.startTracking(PHASE, 0);
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        super.updatePassengerForDismount(passenger);
        int exitDirection = Math.round(passenger.getYaw() / 90) * 90;
        Vec3d pos = passenger.getPos();

        // Exit velocity.
        double velocityY = this.getVelocity().getY() * 2.5;
        passenger.setVelocity(switch (exitDirection) {
        case 0, 360 -> new Vec3d(0, velocityY * 1.5, velocityY);
        case 90 -> new Vec3d(-velocityY, velocityY * 1.5, 0);
        case -90 -> new Vec3d(velocityY, velocityY * 1.5, 0);
        case -180, 180 -> new Vec3d(0, velocityY * 1.5, -velocityY);
        default -> throw new IllegalArgumentException("Unexpected value: " + exitDirection);
        });

        // Set the position to teleport out of the rocket.
        Vec3d exitPos = switch (exitDirection) {
        case 0, 360 -> new Vec3d(pos.getX(), pos.getY(), pos.getZ() + 1.5f);
        case 90 -> new Vec3d(pos.getX() - 1.5f, pos.getY(), pos.getZ());
        case -90 -> new Vec3d(pos.getX() + 1.5f, pos.getY(), pos.getZ());
        case -180, 180 -> new Vec3d(pos.getX(), pos.getY(), pos.getZ() - 1.5f);
        default -> throw new IllegalArgumentException("Unexpected value: " + exitDirection);
        };

        // Place the rider up to 3 blocks below the rocket.
        int checks = 3;
        BlockPos exitBlockPos = new BlockPos(exitPos);
        while (this.world.getBlockState(exitBlockPos).isAir() && checks > 0) {
            exitBlockPos = exitBlockPos.down();
            checks--;
        }

        return new Vec3d(exitBlockPos.getX(), exitBlockPos.up().getY(), exitBlockPos.getZ());
    }

    public void assignLaunchPad(boolean value) {
        this.setLaunchPad(value);
    }

    @Override
    public void tick() {
        super.tick();

        // Rotate the rocket when the player strafes left or right.
        if (this.getFirstPassenger() instanceof PlayerEntity player) {
            if (ModKeyBindings.leftKeyDown(player)) {
                ModUtils.rotateVehicleYaw(this, this.getYaw() - 1);
            }
            if (ModKeyBindings.rightKeyDown(player)) {
                ModUtils.rotateVehicleYaw(this, this.getYaw() + 1);
            }
        }

        if (this.getY() >= ATMOSPHERE_LEAVE || this.getFrozenTicks() > 1000) {
            this.setFlying(true);
        }
        if (!this.isFlying()) {
            if (this.hasLaunchPad()) {
                BlockState below = world.getBlockState(this.getBlockPos());
                if (!(below.getBlock() instanceof RocketLaunchPad)) {
                    this.drop();
                } else if (below.getBlock() instanceof RocketLaunchPad pad) {
                    if (!below.get(RocketLaunchPad.STAGE)) {
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
            } else if (this.getY() < ATMOSPHERE_LEAVE) {
                this.spawnAfterburnerParticles();
                this.burnEntitiesUnderRocket();
                this.travel();
                if (this.getCountdownTicks() < -30) {
                    this.explodeIfStopped();
                }
                this.setPhase(2);
                // Phase three: the rocket has reached the required height.
            } else if (this.getY() >= ATMOSPHERE_LEAVE) {
                openPlanetSelectionGui();
                this.setPhase(3);
            }
        }
        if (this.isInLava()) {
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
        if (!this.hasPassengers()) {
            this.explode();
            return;
        }
        this.getPassengerList().forEach(passenger -> {
            if (passenger instanceof PlayerEntity player) {
                if (!(player.currentScreenHandler instanceof PlanetSelectionScreenHandler)) {
                    player.openHandledScreen(new PlanetSelectionScreenHandlerFactory(this.getTier()));

                    if (this.world instanceof ServerWorld serverWorld) {
                        stopRocketSoundForRider((ServerPlayerEntity) player);
                    }
                }
            }
        });
    }

    // When the countdown is in progress and the rocket engine is warming up, releasing smoke.
    public void spawnSmokeParticles() {
        if (this.world instanceof ServerWorld serverWorld) {
            Vec3d pos = this.getPos();
            ModUtils.spawnForcedParticles(serverWorld, ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.getX(), pos.getY(), pos.getZ(), 6, 0.1, 0.1, 0.1, 0.023);
        }
    }

    // When the rocket launches and is burning fuel.
    public void spawnAfterburnerParticles() {
        if (this.world instanceof ServerWorld serverWorld) {
            Vec3d pos = this.getPos();

            ModUtils.spawnForcedParticles(serverWorld, ModParticleTypes.LARGE_FLAME, pos.getX(), pos.getY(), pos.getZ(), 20, 0.1, 0.1, 0.1, 0.001);
            ModUtils.spawnForcedParticles(serverWorld, ModParticleTypes.LARGE_SMOKE, pos.getX(), pos.getY(), pos.getZ(), 10, 0.1, 0.1, 0.1, 0.001);
        }
    }

    // Burn entities under the rocket flames.
    private void burnEntitiesUnderRocket() {
        List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(2, 30, 2).offset(0, -37, 0), entity -> true);
        for (LivingEntity entity : entities) {
            if (ModUtils.hasFullNetheriteSpaceSet(entity) || (entity.getVehicle() != null && entity.getVehicle().equals(this))) {
                continue;
            }
            if (BeyondEarth.CONFIG.mainConfig.entitiesBurnUnderRocket && !entity.isFireImmune()) {
                entity.setOnFireFor(10);
                entity.damage(ModDamageSource.ROCKET_FLAMES, 10);
                BlockState belowBlock = this.world.getBlockState(entity.getBlockPos().down());
                if (belowBlock.isFullCube(world, entity.getBlockPos().down()) && belowBlock.isAir()) {
                    this.world.setBlockState(entity.getBlockPos(), Blocks.FIRE.getDefaultState());
                }
            }
        }
    }

    private void travel() {
        if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, ROCKET_ACCELERATION, 0.0));
        }

        if (this.getVelocity().getY() > ROCKET_MAX_SPEED) {
            this.setVelocity(0.0, ROCKET_MAX_SPEED, 0.0);
        }

        this.move(MovementType.SELF, this.getVelocity());
    }

    public void initiateLaunchSequenceFromServer() {
        initiateLaunchSequence();
        world.playSoundFromEntity(null, this, ModSounds.ROCKET_LAUNCH_SOUND_EVENT, SoundCategory.AMBIENT, 1, 1);
    }

    // Synonymous on client and server.
    public void initiateLaunchSequence() {

        this.setFlying(true);
        this.setCountdownTicks(MAX_COUNTDOWN_TICKS);
        // For shaking.
        this.setFrozenTicks(Integer.MAX_VALUE);
    }

    public boolean hasLaunchPad() {
        return this.dataTracker.get(HAS_LAUNCH_PAD);
    }

    public void setLaunchPad(boolean value) {
        this.dataTracker.set(HAS_LAUNCH_PAD, value);
    }

    public boolean isFlying() {
        return this.dataTracker.get(FLYING);
    }

    public void setFlying(boolean value) {
        this.dataTracker.set(FLYING, value);
    }

    public int getCountdownTicks() {
        return this.dataTracker.get(COUNTDOWN_TICKS);
    }

    public void setCountdownTicks(int value) {
        this.dataTracker.set(COUNTDOWN_TICKS, value);
    }

    public int getPhase() {
        return this.dataTracker.get(PHASE);
    }

    public void setPhase(int value) {
        this.dataTracker.set(PHASE, value);
    }

    public int getTier() {
        return this.dataTracker.get(TIER);
    }

    public void setTier(int value) {
        this.dataTracker.set(TIER, value);
    }

    public int getCountdownSeconds() {
        return (int) ((this.getCountdownTicks() + 20) / 20);
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
        BlockState belowBlock = this.world.getBlockState(this.getBlockPos());

        if (belowBlock.getBlock() instanceof RocketLaunchPad && belowBlock.get(RocketLaunchPad.STAGE).equals(true)) {
            return;
        }

        if (this.getPhase() == 0 || this.getPhase() == 1) {
            super.doGravity();
        }
    }

    // Explode on block collision.
    private void explodeIfStopped() {
        if (this.getVelocity().getY() < ROCKET_MAX_SPEED / 10 && this.getVelocity().getY() > -0.000001) {
            this.explode();
        }
    }

    public void explode() {
        if (this.world instanceof ServerWorld serverWorld) {
            // Increase explosion power the higher the tier.
            float tierMultiplier = 1 + this.getTier() * 0.25f;
            if (this.getVelocity().getY() > (ROCKET_MAX_SPEED / 1.25f)) {
                // Increase explosion power at a high speed.
                tierMultiplier *= 1.25f;
            }
            this.explode(tierMultiplier);
        }
    }

    public static void stopRocketSoundForRider(ServerPlayerEntity rider) {
        StopSoundS2CPacket stopSoundS2CPacket = new StopSoundS2CPacket(ModSounds.ROCKET_LAUNCH_SOUND_ID, SoundCategory.AMBIENT);
        rider.networkHandler.sendPacket(stopSoundS2CPacket);
    }
}