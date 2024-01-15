package earth.terrarium.adastra.common.systems;

import earth.terrarium.adastra.api.planets.PlanetApi;
import earth.terrarium.adastra.api.systems.GravityApi;
import earth.terrarium.adastra.common.constants.PlanetConstants;
import earth.terrarium.adastra.common.handlers.PlanetHandler;
import earth.terrarium.adastra.common.planets.Planet;
import earth.terrarium.adastra.mixins.common.LivingEntityAccessor;
import net.minecraft.Optionull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;

public class GravityApiImpl implements GravityApi {
    @Override
    public float getGravity(Level level) {
        return getGravity(level.dimension());
    }

    @Override
    public float getGravity(ResourceKey<Level> level) {
        return Optionull.mapOrDefault(PlanetApi.API.getPlanet(level), Planet::gravity, PlanetConstants.EARTH_GRAVITY) / PlanetConstants.EARTH_GRAVITY;
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
    public void setGravity(Level level, Collection<BlockPos> positions, float gravity) {
        if (level.isClientSide()) return;
        PlanetHandler.setGravity((ServerLevel) level, positions, gravity);
    }

    @Override
    public void removeGravity(Level level, BlockPos pos) {
        setGravity(level, pos, getGravity(level));
    }

    @Override
    public void removeGravity(Level level, Collection<BlockPos> positions) {
        setGravity(level, positions, getGravity(level));
    }

    @Override
    public void entityTick(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos) {
        if (!entity.isControlledByLocalInstance()) return;

        boolean touchingSomething = false;
        for (Direction direction : Direction.values()) {
            BlockPos pos = movementAffectingPos.relative(direction);
            if (!level.getBlockState(pos).isAir()) {
                touchingSomething = true;
                break;
            }
        }

        float ySpeed = 0;
        if ((touchingSomething || level.getGameTime() % 10 == 0) && entity instanceof Player player) {
            if (player.isShiftKeyDown()) {
                ySpeed = -0.01f;
            } else if (((LivingEntityAccessor) player).isJumping()) {
                ySpeed = 0.01f;
            }
        }

        if (!touchingSomething) {
            travelVector = travelVector.scale(0.12f);
        }

        float friction = level.getBlockState(movementAffectingPos).getBlock().getFriction();
        float speed = touchingSomething ? entity.onGround() ? friction * 0.91f : 0.91f : entity.onGround() ? friction * PlanetConstants.SPACE_FRICTION : PlanetConstants.SPACE_FRICTION;
        Vec3 movementVector = entity.handleRelativeFrictionAndCalculateMovement(travelVector, friction);

        double downSpeed = movementVector.y;
        //noinspection deprecation
        if (entity.level().isClientSide() && !entity.level().hasChunkAt(movementAffectingPos)) {
            if (entity.getY() > entity.level().getMinBuildHeight()) {
                downSpeed = -0.1;
            } else {
                downSpeed = 0.0;
            }
        }
        downSpeed += ySpeed;

        if (entity.shouldDiscardFriction()) {
            entity.setDeltaMovement(movementVector.x, downSpeed, movementVector.z);
        } else {
            entity.setDeltaMovement(movementVector.x * speed, downSpeed * 0.98, movementVector.z * speed);
        }

        entity.calculateEntityAnimation(this instanceof FlyingAnimal);
    }
}
