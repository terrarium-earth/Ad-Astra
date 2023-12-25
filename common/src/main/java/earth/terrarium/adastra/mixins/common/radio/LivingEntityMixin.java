package earth.terrarium.adastra.mixins.common.radio;

import earth.terrarium.adastra.client.radio.audio.RadioHandler;
import earth.terrarium.adastra.common.entities.base.RadioHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "stopRiding", at = @At("HEAD"))
    private void adastra$stopRiding(CallbackInfo ci) {
        //noinspection ConstantValue
        if ((Object) this instanceof Player player && player.level().isClientSide() && player.getVehicle() instanceof RadioHolder) {
            RadioHandler.stop();
        }
    }
}
