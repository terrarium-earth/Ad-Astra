package net.mrscauthd.beyond_earth.blocks.launch_pad;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.registry.ModBlockEntities;

public class RocketLaunchPadEntity extends BlockEntity {

    public RocketLaunchPadEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ROCKET_LAUNCH_PAD_ENTITY, pos, state);
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, RocketLaunchPadEntity blockEntity) {

        boolean raise = checkInRadius(true, pos, world) && checkInRadius(false, pos, world);
        world.setBlockState(pos, state.with(RocketLaunchPad.STAGE, raise));
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