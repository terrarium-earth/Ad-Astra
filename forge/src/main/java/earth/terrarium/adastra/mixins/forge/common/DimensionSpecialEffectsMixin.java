package earth.terrarium.adastra.mixins.forge.common;

import earth.terrarium.adastra.client.forge.ClientPlatformUtilsImpl;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionSpecialEffects.class)
public abstract class DimensionSpecialEffectsMixin {

    // Mixin directly instead of using the event because Ad Astra dimension renderers are added dynamically via resource pack.
    @Inject(method = "forType", at = @At("HEAD"), cancellable = true)
    private static void adastra$forType(DimensionType type, CallbackInfoReturnable<DimensionSpecialEffects> cir) {
        ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION, type.effectsLocation());
        if (ClientPlatformUtilsImpl.DIMENSION_RENDERERS.containsKey(dimension)) {
            cir.setReturnValue(ClientPlatformUtilsImpl.DIMENSION_RENDERERS.get(dimension));
        }
    }
}
