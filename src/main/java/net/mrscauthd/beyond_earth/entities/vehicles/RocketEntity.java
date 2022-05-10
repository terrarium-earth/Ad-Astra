package net.mrscauthd.beyond_earth.entities.vehicles;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.blocks.launch_pad.RocketLaunchPad;
import net.mrscauthd.beyond_earth.registry.ModParticles;
import net.mrscauthd.beyond_earth.registry.ModSounds;

public class RocketEntity extends VehicleEntity {

    // 10 seconds.
    public static int MAX_COUNTDOWN_TICKS = 200;
    private BlockPos homeLaunchPad = BlockPos.ORIGIN;
    private boolean flying;
    private int countdownTicks;

    public RocketEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.homeLaunchPad = NbtHelper.toBlockPos(nbt.getCompound("HomeLaunchPad"));
        this.flying = nbt.getBoolean("Flying");
        this.countdownTicks = nbt.getInt("CountdownTicks");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.put("HomeLaunchPad", NbtHelper.fromBlockPos(this.homeLaunchPad));
        nbt.putBoolean("IsFlying", this.flying);
        nbt.putInt("CountdownTicks", this.countdownTicks);
    }

    public void setHomeLaunchPad(BlockPos pos) {
        this.homeLaunchPad = pos;
    }

    @Override
    public void tick() {
        super.tick();
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
                    // Phase two: the rocket has launched.
                } else if (this.getY() < 600) {
                    this.spawnAfterburnerParticles();
                    this.travel();
                    // Phase three: the rocket has reached the required height.
                } else if (this.getY() >= 600) {
                    this.flying = false;
                }
        }
    }

    // When the countdown is in progress and the rocket engine is warming up, releasing smoke.
    public void spawnSmokeParticles() {
        if (this.world instanceof ServerWorld serverWorld) {
            Vec3d vec = this.getVelocity();
            for (ServerPlayerEntity player : serverWorld.getPlayers()) {
                serverWorld.spawnParticles(player, ParticleTypes.CAMPFIRE_COSY_SMOKE, true, this.getX() - vec.x, this.getY() - vec.y - 0.1, this.getZ() - vec.z, 6, 0.1, 0.1, 0.1, 0.023);
            }
        }
    }

    public float getAfterburnerLength() {
        return 0;
    }

    // When the rocket launches and is burning fuel.
    public void spawnAfterburnerParticles() {
        Vec3d pos = this.getPos();
        if (this.world.isClient) {
            this.world.addImportantParticle(ModParticles.LARGE_FLAME, true, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
            this.world.addImportantParticle(ModParticles.LARGE_SMOKE, true, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
        }
    }

    private void travel() {
        // Vec3d velocity = this.getVelocity();
        // this.setVelocity(velocity.getX(), velocity.getY() + 1, velocity.getZ());

        Vec3d pos = this.getPos();
        this.setPos(pos.getX(), pos.getY() + 0.8, pos.getZ());
    }

    public void initiateLaunchSequenceFromServer() {
        initiateLaunchSequence();
        world.playSound(null, this.getBlockPos(), ModSounds.ROCKET_LAUNCH_SOUND_EVENT, SoundCategory.BLOCKS, 1, 1);
    }

    public void initiateLaunchSequence() {
        this.flying = true;
        this.countdownTicks = MAX_COUNTDOWN_TICKS;
    }

    public boolean isFlying() {
        return this.flying;
    }

    public int getCountdownSeconds() {
        return (int)((this.countdownTicks + 20) / 20);
    }
}