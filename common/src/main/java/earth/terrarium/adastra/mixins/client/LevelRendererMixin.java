package earth.terrarium.adastra.mixins.client;

import earth.terrarium.adastra.client.utils.DimensionUtils;
import earth.terrarium.adastra.common.registry.ModParticleTypes;
import earth.terrarium.adastra.common.tags.ModBiomeTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

    @Shadow
    @Final
    private RenderBuffers renderBuffers;

    @ModifyArg(method = "tickRain", at = @At(
        value = "INVOKE",
        target = "Lnet/minecraft/client/multiplayer/ClientLevel;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"
    ), index = 0)
    private ParticleOptions adastra$tickRain(ParticleOptions original) {
        return adastra$hasAcidRain() ? ModParticleTypes.ACID_RAIN.get() : original;
    }

    @ModifyArg(method = "renderSnowAndRain", at = @At(
        value = "INVOKE",
        target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V",
        ordinal = 0), index = 1)
    private ResourceLocation adastra$renderSnowAndRain(ResourceLocation original) {
        return adastra$hasAcidRain() ? DimensionUtils.ACID_RAIN : original;
    }

    @ModifyArg(method = "renderClouds", at = @At(
        value = "INVOKE",
        target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V",
        ordinal = 0), index = 1)
    private ResourceLocation adastra$renderClouds(ResourceLocation original) {
        return adastra$hasAcidRain() ? DimensionUtils.VENUS_CLOUDS : original;
    }

    @Unique
    private boolean adastra$hasAcidRain() {
        var player = Minecraft.getInstance().player;
        if (player == null) return false;
        return player.level().getBiome(player.blockPosition()).is(ModBiomeTags.HAS_ACID_RAIN);
    }
}
