package com.github.alexnijjar.beyond_earth.entities.vehicles;

import com.github.alexnijjar.beyond_earth.util.ModKeyBindings;
import com.github.alexnijjar.beyond_earth.util.ModUtils;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LanderEntity extends VehicleEntity {

    public LanderEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public double getMountedHeightOffset() {
        return super.getMountedHeightOffset() + 1.6f;
    }

    @Override
    public boolean shouldRenderPlayer() {
        return false;
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
    public void tick() {
        super.tick();

        if (this.getFirstPassenger() instanceof PlayerEntity player) {

            // Player is clicking 'space' to move upward.
            if (ModKeyBindings.jumpKeyDown(player)) {
                this.applyBoosters();
            }
        }
    }

    public void applyBoosters() {
        if (this.getVelocity().getY() != 0) {
            this.setVelocity(this.getVelocity().add(0.0, 0.05, 0.0));

            if (this.getVelocity().getY() > -0.1) {
                this.setVelocity(0.0, -0.1, 0.0);
            }

            // Particles.
            if (this.world instanceof ServerWorld serverWorld) {
                Vec3d pos = this.getPos();
                ModUtils.spawnForcedParticles(serverWorld, ParticleTypes.SPIT, pos.getX(), pos.getY() - 0.3, pos.getZ(), 3, 0.1, 0.1, 0.1, 0.001);
            }

            this.move(MovementType.SELF, this.getVelocity());
        }
    }
}