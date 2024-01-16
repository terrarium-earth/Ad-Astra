package earth.terrarium.adastra.mixins.common.entities;

import earth.terrarium.adastra.common.entities.mob.SulfurCreeper;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Creeper.class)
public abstract class CreeperMixin {

    @Inject(method = "explodeCreeper", at = @At("HEAD"), cancellable = true)
    private void adastra$explodeCreeper(CallbackInfo ci) {
        if (((Creeper) (Object) this) instanceof SulfurCreeper creeper) {
            ci.cancel();
            creeper.explodeCreeper();
        }
    }
}
