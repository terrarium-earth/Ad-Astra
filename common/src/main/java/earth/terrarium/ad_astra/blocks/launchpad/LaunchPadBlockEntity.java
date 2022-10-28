package earth.terrarium.ad_astra.blocks.launchpad;

import earth.terrarium.ad_astra.registry.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class LaunchPadBlockEntity extends BlockEntity {

    public LaunchPadBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.LAUNCH_PAD.get(), blockPos, blockState);
    }

    public void tick() {
    }
}
