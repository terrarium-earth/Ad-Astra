package earth.terrarium.adastra.mixins.common.environment;

import earth.terrarium.adastra.api.systems.OxygenApi;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.WeatheringCopperFullBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WeatheringCopperFullBlock.class)
public abstract class WeatheringCopperFullBlockMixin {

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void adastra$randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (!OxygenApi.API.hasOxygen(level, pos)) {
            ci.cancel();
        }
    }
}
