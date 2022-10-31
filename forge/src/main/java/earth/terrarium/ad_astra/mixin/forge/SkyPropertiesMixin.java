package earth.terrarium.ad_astra.mixin.forge;

import earth.terrarium.ad_astra.client.dimension.forge.ClientModSkiesImpl;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionSpecialEffects.class)
public abstract class SkyPropertiesMixin {

    @Inject(method = "byDimensionType", at = @At("HEAD"), cancellable = true)
    private static void onGetType(DimensionType type, CallbackInfoReturnable<DimensionSpecialEffects> cir) {
        if (ClientModSkiesImpl.SKY_PROPERTIES.containsKey(type.effectsLocation())) {
            cir.setReturnValue(ClientModSkiesImpl.SKY_PROPERTIES.get(type.effectsLocation()));
        }
    }
}
