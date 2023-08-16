package earth.terrarium.ad_astra.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import earth.terrarium.ad_astra.common.entity.system.EntityOxygenSystem;
import earth.terrarium.ad_astra.common.entity.system.EntityOxygenSystem.EntityOxygenStatus;
import earth.terrarium.ad_astra.common.util.OxygenUtils;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;

@Mixin(Mob.class)
public abstract class MobMixin {

    @Inject(method = "checkSpawnObstruction", at = @At("HEAD"), cancellable = true)
    public void ad_astra$checkSpawnObstruction(LevelReader levelReader, CallbackInfoReturnable<Boolean> cir) {

        if (levelReader instanceof Level level && !OxygenUtils.levelHasOxygen(level)) {
            Mob mob = (Mob) (Object) this;

            if (EntityOxygenSystem.getOxygenStatus(mob, level) == EntityOxygenStatus.DAMAGE) {
                cir.setReturnValue(false);
            }
        }
    }
}
