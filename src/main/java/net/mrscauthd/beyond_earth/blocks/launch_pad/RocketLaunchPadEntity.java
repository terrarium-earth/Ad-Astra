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

    public static void tick(World world, BlockPos pos, BlockState state, RocketLaunchPadEntity blockEntity) {

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                BlockPos padPos = new BlockPos(x + i - 1, y, z + j - 1);

                if (!(world.getBlockState(padPos).getBlock() instanceof RocketLaunchPad)) {
                    if (state.get(RocketLaunchPad.STAGE).equals(true)) {
                        world.setBlockState(pos, state.with(RocketLaunchPad.STAGE, false));
                    }
                    return;
                }
            }
        }

        if (state.get(RocketLaunchPad.STAGE).equals(false)) {
            world.setBlockState(pos, state.with(RocketLaunchPad.STAGE, true));
        }
    }
}
