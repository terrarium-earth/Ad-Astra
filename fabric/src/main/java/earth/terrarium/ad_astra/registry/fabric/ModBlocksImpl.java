package earth.terrarium.ad_astra.registry.fabric;

import earth.terrarium.ad_astra.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

public class ModBlocksImpl {
    public static <T extends FlowingFluid> Supplier<LiquidBlock> registerLiquid(Supplier<T> fluid, BlockBehaviour.Properties properties, ModBlocks.InsideLiquid inside) {
        return () -> new LiquidBlock(fluid.get(), properties) {
            @Override
            public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
                inside.onEntityInside(state, level, pos, entity);
            }
        };
    }
}
