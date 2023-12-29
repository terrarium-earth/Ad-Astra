package earth.terrarium.adastra.common.entities.vehicles;

import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.client.utils.SoundUtils;
import earth.terrarium.adastra.common.menus.vehicles.LanderMenu;
import earth.terrarium.adastra.common.registry.ModParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Lander extends Vehicle {
    private float speed;
    private float angle;
    public boolean startedRocketSound;

    public Lander(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        speed = compound.getFloat("Speed");
        angle = compound.getFloat("Angle");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat("Speed", speed);
        compound.putFloat("Angle", angle);
    }

    @Override
    public double getPassengersRidingOffset() {
        return 2.3;
    }

    @Override
    public boolean hideRider() {
        return true;
    }

    @Override
    public boolean zoomOutCameraInThirdPerson() {
        return true;
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        return super.getDismountLocationForPassenger(passenger)
            .add(passenger.getLookAngle().normalize().scale(2));
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
    public void tick() {
        super.tick();
        if (!onGround()) flightTick();
    }

    private void flightTick() {
        var delta = getDeltaMovement();

        float xxa = -xxa(); // right/left
        float yya = yya(); // up/down

        if (xxa != 0) {
            angle += xxa * 1;
        } else {
            angle *= 0.9f;
        }

        if (yya > 0 && delta.y < -0.05) {
            speed += 0.01f;
            fallDistance *= 0.9f;
            spawnLanderParticles();
            if (level().isClientSide() && !startedRocketSound) {
                startedRocketSound = true;
                SoundUtils.playLanderSound(this);
            }
        } else if (speed > -1.1) {
            speed -= 0.01f;
        }

        // clamp turning
        angle = Mth.clamp(angle, -3, 3);

        setYRot(getYRot() + angle);

        setDeltaMovement(
            delta.x(),

            speed,
            delta.z()
        );

        if (isInWater()) {
            setDeltaMovement(delta.x(), Math.min(0.06, delta.y() + 0.15), delta.z());
            speed *= 0.9f;
        }
    }

    public void explode() {
        if (level().isClientSide()) return;
        level().explode(
            this,
            getX(), getY(), getZ(),
            10,
            OxygenApi.API.hasOxygen(this.level()),
            Level.ExplosionInteraction.TNT);
        discard();
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        if (level().isClientSide()) return false;
        if (fallDistance > 40 && onGround()) {
            explode();
            return true;
        }
        return false;
    }

    public void spawnLanderParticles() {
        if (!level().isClientSide()) return;
        for (int i = 0; i < 10; i++) {
            level().addParticle(ModParticleTypes.LARGE_FLAME.get(),
                getX(), getY() - 0.2, getZ(),
                Mth.nextDouble(level().random, -0.05, 0.05),
                Mth.nextDouble(level().random, -0.05, 0.05),
                Mth.nextDouble(level().random, -0.05, 0.05));
        }

        for (int i = 0; i < 10; i++) {
            level().addParticle(ModParticleTypes.LARGE_SMOKE.get(),
                getX(), getY() - 0.2, getZ(),
                Mth.nextDouble(level().random, -0.05, 0.05),
                Mth.nextDouble(level().random, -0.05, 0.05),
                Mth.nextDouble(level().random, -0.05, 0.05));
        }
    }

    @Override
    public ItemStack getDropStack() {
        return ItemStack.EMPTY;
    }

    @Override
    public int getInventorySize() {
        return 11;
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new LanderMenu(containerId, inventory, this);
    }
}
