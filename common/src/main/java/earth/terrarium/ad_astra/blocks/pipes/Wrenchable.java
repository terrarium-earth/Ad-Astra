package earth.terrarium.ad_astra.blocks.pipes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface Wrenchable {

    void handleWrench(Level level, BlockPos pos, BlockState state, Direction side, Player user);
}
