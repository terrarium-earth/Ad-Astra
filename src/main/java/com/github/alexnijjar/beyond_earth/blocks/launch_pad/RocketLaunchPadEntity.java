package com.github.alexnijjar.beyond_earth.blocks.launch_pad;

import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RocketLaunchPadEntity extends BlockEntity {

    public RocketLaunchPadEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ROCKET_LAUNCH_PAD_ENTITY, pos, state);
    }

    public void tick() {
        if (world.isClient) {
            boolean raise = checkInRadius(true, pos, this.world) && checkInRadius(false, this.getPos(), this.world);
            this.world.setBlockState(pos, this.getCachedState().with(RocketLaunchPad.STAGE, raise));
        }
    }

    private static boolean checkInRadius(boolean lookForPlatforms, BlockPos pos, World world) {
        for (int i = 0; i < (lookForPlatforms ? 3 : 5); i++) {
            for (int j = 0; j < (lookForPlatforms ? 3 : 5); j++) {
                BlockPos padPos = new BlockPos(pos.getX() + i - (lookForPlatforms ? 1 : 2), pos.getY(), pos.getZ() + j - (lookForPlatforms ? 1 : 2));
                BlockState state = world.getBlockState(padPos);
                boolean isRocket = state.getBlock() instanceof RocketLaunchPad;

                // Skip for self.
                if (padPos.equals(pos)) {
                    continue;
                }

                if (lookForPlatforms) {
                    // Checks if every block in a 3x3 radius is a rocket launch pad.
                    if (!isRocket) {
                        return false;
                    }
                } else {
                    // Checks if every block in a 5x5 radius is not a raised rocket launch pad.
                    if (isRocket && state.get(RocketLaunchPad.STAGE).equals(true)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}