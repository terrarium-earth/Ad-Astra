package earth.terrarium.ad_astra.common.block.machine;

import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class AnimatedMachineBlock extends MachineBlock {
    public AnimatedMachineBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }
}
