package earth.terrarium.adastra.common.blocks;

import earth.terrarium.adastra.common.blocks.base.MachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public class SolarPanelBlock extends MachineBlock {
    private final long generationRate;
    private final long capacity;

    public static final VoxelShape SHAPE = Stream.of(
        Block.box(1, 0, 1, 15, 5, 15),
        Block.box(2, 5, 2, 14, 15, 14),
        Block.box(6, 14, 6, 10, 22, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public SolarPanelBlock(Properties properties, long generationRate, long capacity) {
        super(properties, true);
        this.generationRate = generationRate;
        this.capacity = capacity;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    public long generationRate() {
        return this.generationRate;
    }

    public long capacity() {
        return this.capacity;
    }
}
