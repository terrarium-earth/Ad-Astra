package earth.terrarium.ad_astra.common.system;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.phys.Vec3;

public class SpaceMovementSystem {

    public static void travel(LivingEntity entity, Vec3 travelVector) {
        if (entity.isEffectiveAi() || entity.isControlledByLocalInstance()) {
            double entityGravity = GravitySystem.getPosGravity(entity.level, entity.blockPosition());
            double gravity = 0.08 * entityGravity;
            float speed;

            BlockPos blockPos = new BlockPos(entity.getX(), entity.getBoundingBox().minY - 0.5000001, entity.getZ());


            Vec3 newTravelVector = travelVector;
            boolean touchingSomething = false;
            for (Direction direction : Direction.values()) {
                if (!entity.level.getBlockState(entity.blockPosition().relative(direction)).isAir() || !entity.level.getBlockState(entity.blockPosition().above().relative(direction)).isAir()) {
                    newTravelVector = travelVector.scale(0.5 + entityGravity / 2);
                    touchingSomething = true;
                    break;
                } else if (entityGravity < 0.5 && GravitySystem.getPosGravity(entity.level, entity.blockPosition().above()) < 0.5) {
                    newTravelVector = travelVector.scale(0.1);
                }
            }

            float friction = entity.level.getBlockState(blockPos).getBlock().getFriction();
            float spaceFriction = (float)(0.999 - (entityGravity / 12));
            speed = touchingSomething ? entity.isOnGround() ? friction * 0.91f : 0.91f : entity.isOnGround() ? friction * spaceFriction : spaceFriction;
            Vec3 vec36 = entity.handleRelativeFrictionAndCalculateMovement(newTravelVector, friction);
            double downSpeed = vec36.y;
            if (entity.level.isClientSide && !entity.level.hasChunkAt(blockPos)) {
                if (entity.getY() > (double) entity.level.getMinBuildHeight()) {
                    downSpeed = -0.1;
                } else {
                    downSpeed = 0.0;
                }
            } else if (!entity.isNoGravity()) {
                downSpeed -= gravity;
            }

            if (entity.shouldDiscardFriction()) {
                entity.setDeltaMovement(vec36.x, downSpeed, vec36.z);
            } else {
                entity.setDeltaMovement(vec36.x * (double) speed, downSpeed * 0.9800000190734863, vec36.z * (double) speed);
            }
        }

        entity.calculateEntityAnimation(entity, entity instanceof FlyingAnimal);
    }
}
