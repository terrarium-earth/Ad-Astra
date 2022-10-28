package earth.terrarium.ad_astra.mixin;

import earth.terrarium.ad_astra.registry.ModBlocks;
import earth.terrarium.ad_astra.util.OxygenUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidBlock.class)
public class FluidBlockMixin {

    @Inject(method = "onBlockAdded", at = @At("HEAD"))
    public void adastra_onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        if (!world.isClient) {
            FluidBlock block = (FluidBlock) (Object) this;
            if (block.getFluidState(state).isIn(FluidTags.WATER) && !block.equals(ModBlocks.CRYO_FUEL_BLOCK.get())) {
                if (!OxygenUtils.posHasOxygen(world, pos)) {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                }
            }
        }
    }

    // Turn water fluids into ice upon contact with cryo fuel
    @Inject(method = "receiveNeighborFluids", at = @At("HEAD"), cancellable = true)
    public void adastra_receiveNeighborFluids(World world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> ci) {
        FluidBlock block = (FluidBlock) (Object) this;
        if (block.equals(Blocks.WATER)) {
            for (Direction direction : new Direction[]{Direction.DOWN, Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST}) {
                BlockPos blockPos = pos.offset(direction.getOpposite());
                if (world.getFluidState(blockPos).getBlockState().getBlock().equals(ModBlocks.CRYO_FUEL_BLOCK.get())) {
                    world.setBlockState(pos, Blocks.ICE.getDefaultState());
                    ci.setReturnValue(false);
                }
            }
        }
    }
}
