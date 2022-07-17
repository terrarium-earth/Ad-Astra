package com.github.alexnijjar.beyond_earth.mixin;

import java.util.Optional;

import com.github.alexnijjar.beyond_earth.world.WorldSeed;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.GeneratorOptions;

@Mixin(GeneratorOptions.class)
public class GeneratorOptionsMixin {

    // Gets the world seed to replace the default value of '0' for custom dimensions
    @Inject(at = @At(value = "TAIL"), method = "<init>(JZZLnet/minecraft/util/registry/Registry;Ljava/util/Optional;)V")
    private void GeneratorOptions(long seed, boolean generateStructures, boolean bonusChest, Registry<DimensionOptions> options, Optional<String> legacyCustomOptions, CallbackInfo ci) {
        WorldSeed.setSeed(seed);
    }
}