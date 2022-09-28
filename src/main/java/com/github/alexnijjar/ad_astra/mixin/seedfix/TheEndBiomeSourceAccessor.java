package com.github.alexnijjar.ad_astra.mixin.seedfix;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.biome.source.TheEndBiomeSource;

@Mixin(TheEndBiomeSource.class)
public interface TheEndBiomeSourceAccessor {
    @Accessor("seed")
    long getSeed();
}
