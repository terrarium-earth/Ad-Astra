package com.github.alexnijjar.ad_astra.mixin.seedfix;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.Holder;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryOps;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.TheEndBiomeSource;

@Mixin(TheEndBiomeSource.class)
public abstract class TheEndBiomeSourceMixin extends BiomeSource {
    @Shadow
    @Final
    @Mutable
    public static Codec<TheEndBiomeSource> CODEC;

    @Unique
    private static long generationSeed = 0;

    @Unique
    private static long getGenerationSeed() {
        return generationSeed;
    }

    protected TheEndBiomeSourceMixin(List<Holder<Biome>> list) {
        super(list);
    }

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void seedfix$clinit(CallbackInfo ci) {
        CODEC = RecordCodecBuilder.create((instance) -> {
            return instance.group(RegistryOps.getRegistry(Registry.BIOME_KEY).forGetter((theEndBiomeSource) -> {
                return null;
            }), Codec.LONG.fieldOf("seed").orElseGet(TheEndBiomeSourceMixin::getGenerationSeed).stable().forGetter((theEndBiomeSource) -> {
                return ((TheEndBiomeSourceAccessor) theEndBiomeSource).getSeed();
            })).apply(instance, instance.stable(TheEndBiomeSource::new));
        });
    }

    @ModifyVariable(
            method = "<init>(JLnet/minecraft/util/Holder;Lnet/minecraft/util/Holder;Lnet/minecraft/util/Holder;Lnet/minecraft/util/Holder;Lnet/minecraft/util/Holder;)V",
            at = @At("HEAD"),
            argsOnly = true
    )
    private static long seedfix$init(long seed) {
        if (seed == 0) {
            return getGenerationSeed();
        } else {
            return generationSeed = seed;
        }
    }
}
