package earth.terrarium.adastra.mixins.common.environment;

import earth.terrarium.adastra.api.systems.TemperatureApi;
import earth.terrarium.adastra.common.constants.PlanetConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IceBlock.class)
public abstract class IceBlockMixin {
    @Inject(method = "playerDestroy", at = @At("TAIL"))
    private void adastra$playerDestroy(Level level, net.minecraft.world.entity.player.Player player, BlockPos pos, BlockState state, net.minecraft.world.level.block.entity.BlockEntity blockEntity, net.minecraft.world.item.ItemStack stack, CallbackInfo ci) {
        if (TemperatureApi.API.getTemperature(level, pos) < PlanetConstants.FREEZE_TEMPERATURE) {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
    }

    @Inject(method = "melt", at = @At("HEAD"), cancellable = true)
    private void adastra$melt(BlockState state, Level level, BlockPos pos, CallbackInfo ci) {
        if (TemperatureApi.API.getTemperature(level, pos) < PlanetConstants.FREEZE_TEMPERATURE) {
            ci.cancel();
        }
    }
}
