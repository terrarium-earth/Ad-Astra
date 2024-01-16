package earth.terrarium.adastra.mixins.common.entities;

import earth.terrarium.adastra.common.entities.mob.ZombifiedMogler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zoglin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Zoglin.class)
public abstract class ZoglinMixin {

    @Inject(method = "isTargetable", at = @At("RETURN"), cancellable = true)
    private void adastra$isTargetable(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        if (((Zoglin) (Object) this) instanceof ZombifiedMogler) {
            cir.setReturnValue(cir.getReturnValue() && !(livingEntity instanceof ZombifiedMogler));
        }
    }
}
