package earth.terrarium.ad_astra.blocks.launchpad;

import earth.terrarium.ad_astra.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LaunchPadBlockEntity extends BlockEntity {

    public LaunchPadBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.LAUNCH_PAD.get(), blockPos, blockState);
    }

    public void tick() {
    }
}
