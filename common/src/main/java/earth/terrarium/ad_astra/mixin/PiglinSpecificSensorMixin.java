package earth.terrarium.ad_astra.mixin;

import earth.terrarium.ad_astra.common.entity.mob.Pygro;
import earth.terrarium.ad_astra.common.entity.mob.PygroBrute;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.PiglinSpecificSensor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PiglinSpecificSensor.class)
public abstract class PiglinSpecificSensorMixin {

    // Disable Pygros running away from soul blocks.
    @Inject(method = "findNearestRepellent", at = @At("HEAD"), cancellable = true)
    private static void ad_astra$findPiglinRepellent(ServerLevel level, LivingEntity entity, CallbackInfoReturnable<Optional<BlockPos>> cir) {
        if (entity instanceof Pygro || entity instanceof PygroBrute) {
            cir.setReturnValue(BlockPos.findClosestMatch(entity.blockPosition(), 8, 4, pos -> false));
        }
    }

    // Disable Pygros attacking players without gold.
    @Inject(method = "doTick", at = @At("TAIL"))
    public void ad_astra$sense(ServerLevel level, LivingEntity entity, CallbackInfo ci) {
        if (entity instanceof Pygro || entity instanceof PygroBrute) {
            entity.getBrain().eraseMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD);
        }
    }
}
