package com.github.alexnijjar.beyond_earth.entities.vehicles;

import java.util.List;

import com.github.alexnijjar.beyond_earth.blocks.launch_pad.RocketLaunchPad;
import com.github.alexnijjar.beyond_earth.gui.PlanetSelectionHandlerFactory;
import com.github.alexnijjar.beyond_earth.gui.screen_handlers.PlanetSelectionScreenHandler;
import com.github.alexnijjar.beyond_earth.registry.ModParticles;
import com.github.alexnijjar.beyond_earth.registry.ModSounds;
import com.github.alexnijjar.beyond_earth.util.ModDamageSource;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RocketEntity extends VehicleEntity {

    // 10 seconds.
    public static final int ATMOSPHERE_LEAVE = 600;
    public static final int MAX_COUNTDOWN_TICKS = 200;
    public static final float ROCKET_ACCELERATION = 0.01f;
    public static final float ROCKET_MAX_SPEED = 0.85f;
    private BlockPos homeLaunchPad = BlockPos.ORIGIN;
    private boolean flying;
    private int countdownTicks;
    private int tier;
    private int phase;

    public RocketEntity(EntityType<?> type, World world, int tier) {
        super(type, world);
        this.tier = tier;
    }

    public int getTier() {
        return this.tier;
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
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
        return switch (exitDirection) {
        case 0, 360 -> new Vec3d(pos.getX(), pos.getY() + 2, pos.getZ() + 1.5f);
        case 90 -> new Vec3d(pos.getX() - 1.5f, pos.getY() + 2, pos.getZ());
        case -90 -> new Vec3d(pos.getX() + 1.5f, pos.getY() + 2, pos.getZ());
        case -180, 180 -> new Vec3d(pos.getX(), pos.getY() + 2, pos.getZ() - 1.5f);
        default -> throw new IllegalArgumentException("Unexpected value: " + exitDirection);
        };
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.homeLaunchPad = NbtHelper.toBlockPos(nbt.getCompound("HomeLaunchPad"));
        this.flying = nbt.getBoolean("Flying");
        this.countdownTicks = nbt.getInt("CountdownTicks");
        this.phase = nbt.getInt("Phase");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.put("HomeLaunchPad", NbtHelper.fromBlockPos(this.homeLaunchPad));
        nbt.putBoolean("IsFlying", this.flying);
        nbt.putInt("CountdownTicks", this.countdownTicks);
        nbt.putInt("Phase", this.phase);
    }

    public void setHomeLaunchPad(BlockPos pos) {
        this.homeLaunchPad = pos;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getY() >= ATMOSPHERE_LEAVE || this.getFrozenTicks() > 1000) {
            this.flying = true;
        }
        if (!flying) {
            BlockState blockEntity = this.world.getBlockState(homeLaunchPad);
            if (blockEntity.getBlock() instanceof RocketLaunchPad) {
                if (blockEntity.get(RocketLaunchPad.STAGE).equals(false)) {
                    this.drop();
                }
            }
        } else {
            this.countdownTicks--;

            // Phase one: the rocket is counting down, about to launch.
            if (this.countdownTicks > 0) {
                this.spawnSmokeParticles();
                this.phase = 1;
                // Phase two: the rocket has launched.
            } else if (this.getY() < ATMOSPHERE_LEAVE) {
                this.spawnAfterburnerParticles();
                this.burnEntitiesUnderRocket();
                this.travel();
                if (this.countdownTicks < -30) {
                    this.explodeIfStopped();
                }
                this.phase = 2;
                // Phase three: the rocket has reached the required height.
            } else if (this.getY() >= ATMOSPHERE_LEAVE) {
                openPlanetSelectionGui();
                this.phase = 3;
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
                    player.playerScreenHandler.close(player);
                    player.openHandledScreen(new PlanetSelectionHandlerFactory(this.getTier()));
                }
                this.setInvisible(true);
                this.setBoundingBox(Box.from(Vec3d.ZERO));
            }
        });
    }

    // When the countdown is in progress and the rocket engine is warming up, releasing smoke.
    public void spawnSmokeParticles() {
        if (this.world instanceof ServerWorld serverWorld) {
            Vec3d pos = this.getPos();
            serverWorld.spawnParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.getX(), pos.getY(), pos.getZ(), 6, 0.1, 0.1, 0.1, 0.023);
        }
    }

    // When the rocket launches and is burning fuel.
    public void spawnAfterburnerParticles() {
        if (this.world instanceof ServerWorld serverWorld) {
            Vec3d pos = this.getPos();
            serverWorld.spawnParticles(ModParticles.LARGE_FLAME, pos.getX(), pos.getY(), pos.getZ(), 20, 0.1, 0.1, 0.1, 0.001);
            serverWorld.spawnParticles(ModParticles.LARGE_SMOKE, pos.getX(), pos.getY(), pos.getZ(), 10, 0.1, 0.1, 0.1, 0.04);
        }
    }

    // Burn entities under the rocket flames.
    private void burnEntitiesUnderRocket() {
        List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(2, 30, 2).offset(0, -37, 0), entity -> true);
        for (LivingEntity entity : entities) {
            if (entity.getVehicle() != null && entity.getVehicle().equals(this)) {
                continue;
            }
            if (!entity.isFireImmune()) {
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
        world.playSound(null, this.getBlockPos(), ModSounds.ROCKET_LAUNCH_SOUND_EVENT, SoundCategory.BLOCKS, 1, 1);
    }

    // Synonymous on client and server.
    public void initiateLaunchSequence() {

        this.flying = true;
        this.countdownTicks = MAX_COUNTDOWN_TICKS;
        // For shaking.
        this.setFrozenTicks(Integer.MAX_VALUE);
    }

    public boolean isFlying() {
        return this.flying;
    }

    public int getCountdownSeconds() {
        return (int) ((this.countdownTicks + 20) / 20);
    }

    public int getPhase() {
        return this.phase;
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

        if (phase == 0 || phase == 1) {
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
        if (!this.world.isClient) {
            // Increase explosion power the higher the tier.
            float tierMultiplier = 1 + tier * 0.25f;
            if (this.getVelocity().getY() > (ROCKET_MAX_SPEED / 1.25f)) {
                // Increase explosion power at a high speed.
                tierMultiplier *= 1.25f;
            }
            this.explode(tierMultiplier);
        }
    }
}