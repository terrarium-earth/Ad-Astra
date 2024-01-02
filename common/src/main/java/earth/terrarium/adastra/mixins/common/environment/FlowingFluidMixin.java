package earth.terrarium.adastra.mixins.common.environment;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import earth.terrarium.adastra.api.systems.TemperatureApi;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FlowingFluid.class)
public abstract class FlowingFluidMixin {


    @WrapOperation(
        method = "getNewLiquid",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;isSolid()Z"
        )
    )
    private boolean adastra$getNewLiquid(BlockState instance, Operation<Boolean> original, Level level, BlockPos pos, BlockState state) {
        if (!TemperatureApi.API.isLiveable(level, pos)) {
            return false; // Prevent infinite fluid source in hot and cold areas
        }
        return original.call(instance);
    }
}
