package earth.terrarium.ad_astra.mixin;

import earth.terrarium.ad_astra.registry.ModBlocks;
import earth.terrarium.ad_astra.util.OxygenUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LiquidBlock.class)
public class FluidBlockMixin {

    @Inject(method = "onPlace", at = @At("HEAD"))
    public void adastra_onBlockAdded(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        if (!level.isClientSide) {
            LiquidBlock block = (LiquidBlock) (Object) this;
            if (block.getFluidState(state).is(FluidTags.WATER) && !block.equals(ModBlocks.CRYO_FUEL_BLOCK.get())) {
                if (!OxygenUtils.posHasOxygen(level, pos)) {
                    level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                }
            }
        }
    }

    // Turn water fluids into ice upon contact with cryo fuel
    @Inject(method = "shouldSpreadLiquid", at = @At("HEAD"), cancellable = true)
    public void adastra_receiveNeighborFluids(Level level, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> ci) {
        LiquidBlock block = (LiquidBlock) (Object) this;
        if (block.equals(Blocks.WATER)) {
            for (Direction direction : new Direction[]{Direction.DOWN, Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST}) {
                BlockPos blockPos = pos.relative(direction.getOpposite());
                if (level.getFluidState(blockPos).createLegacyBlock().getBlock().equals(ModBlocks.CRYO_FUEL_BLOCK.get())) {
                    level.setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
                    ci.setReturnValue(false);
                }
            }
        }
    }
}
