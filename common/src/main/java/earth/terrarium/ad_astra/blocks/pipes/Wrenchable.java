package earth.terrarium.ad_astra.blocks.pipes;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public interface Wrenchable {

    void handleWrench(World world, BlockPos pos, BlockState state, Direction side, PlayerEntity user);
}
