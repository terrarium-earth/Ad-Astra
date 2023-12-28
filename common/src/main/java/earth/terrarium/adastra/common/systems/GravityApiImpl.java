package earth.terrarium.adastra.common.systems;

import earth.terrarium.adastra.api.systems.GravityApi;
import earth.terrarium.adastra.common.constants.PlanetConstants;
import earth.terrarium.adastra.common.handlers.PlanetHandler;
import earth.terrarium.adastra.common.planets.AdAstraData;
import earth.terrarium.adastra.common.planets.Planet;
import net.minecraft.Optionull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

public class GravityApiImpl implements GravityApi {
    @Override
    public float getGravity(Level level) {
        return getGravity(level.dimension());
    }

    @Override
    public float getGravity(ResourceKey<Level> level) {
        return Optionull.mapOrDefault(AdAstraData.getPlanet(level), Planet::gravity, PlanetConstants.EARTH_GRAVITY) / PlanetConstants.EARTH_GRAVITY;
    }

    @Override
    public float getGravity(Level level, BlockPos pos) {
        if (level.isClientSide()) return getGravity(level);
        return PlanetHandler.getGravity((ServerLevel) level, pos);
    }

    @Override
    public float getGravity(Entity entity) {
        return getGravity(entity.level(), BlockPos.containing(entity.getX(), entity.getEyeY(), entity.getZ()));
    }

    @Override
    public void setGravity(Level level, BlockPos pos, float gravity) {
        if (level.isClientSide()) return;
        PlanetHandler.setGravity((ServerLevel) level, pos, gravity);
    }

    @Override
    public void setGravity(Level level, Set<BlockPos> positions, float gravity) {
        if (level.isClientSide()) return;
        PlanetHandler.setGravity((ServerLevel) level, positions, gravity);
    }

    @Override
    public void removeGravity(Level level, BlockPos pos) {
        setGravity(level, pos, getGravity(level));
    }

    @Override
    public void removeGravity(Level level, Set<BlockPos> positions) {
        setGravity(level, positions, getGravity(level));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void entityTick(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos) {
        if (!entity.isControlledByLocalInstance()) return;

        boolean touchingSomething = false;
        for (Direction direction : Direction.values()) {
            BlockPos pos = movementAffectingPos.relative(direction);
            if (level.getBlockState(pos).isCollisionShapeFullBlock(level, pos)) {
                touchingSomething = true;
                break;
            }
        }

        if (!touchingSomething) {
            travelVector = travelVector.scale(0.1f);
        }

        float friction = level.getBlockState(movementAffectingPos).getBlock().getFriction();
        float speed = touchingSomething ? entity.onGround() ? friction * 0.91f : 0.91f : entity.onGround() ? friction * PlanetConstants.SPACE_FRICTION : PlanetConstants.SPACE_FRICTION;
        Vec3 movementVector = entity.handleRelativeFrictionAndCalculateMovement(travelVector, friction);

        double downSpeed = movementVector.y;
        if (entity.level().isClientSide() && !entity.level().hasChunkAt(movementAffectingPos)) {
            if (entity.getY() > entity.level().getMinBuildHeight()) {
                downSpeed = -0.1;
            } else {
                downSpeed = 0.0;
            }
        }

        if (entity.shouldDiscardFriction()) {
            entity.setDeltaMovement(movementVector.x, downSpeed, movementVector.z);
        } else {
            entity.setDeltaMovement(movementVector.x * speed, downSpeed * 0.98, movementVector.z * speed);
        }

        entity.calculateEntityAnimation(this instanceof FlyingAnimal);
    }
}
