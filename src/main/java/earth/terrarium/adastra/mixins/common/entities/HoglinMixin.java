package earth.terrarium.adastra.mixins.common.entities;

import earth.terrarium.adastra.common.entities.mob.Mogler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hoglin.class)
public abstract class HoglinMixin {

    @Inject(method = "finishConversion", at = @At("HEAD"), cancellable = true)
    private void adastra$finishConversion(ServerLevel level, CallbackInfo ci) {
        if (((Hoglin) (Object) this) instanceof Mogler mogler) {
            ci.cancel();
            mogler.finishConversion();
        }
    }
}
