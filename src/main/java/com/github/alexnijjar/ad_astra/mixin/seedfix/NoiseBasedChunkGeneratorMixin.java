package com.github.alexnijjar.ad_astra.mixin.seedfix;

import java.util.Optional;

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

import net.minecraft.util.HolderSet;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryOps;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.structure.StructureSet;

@Mixin(NoiseChunkGenerator.class)
public abstract class NoiseBasedChunkGeneratorMixin extends ChunkGenerator {
   @Shadow
   @Final
   @Mutable
   public static Codec<NoiseChunkGenerator> CODEC;

   @Unique
   private static long generationSeed = 0;

   public NoiseBasedChunkGeneratorMixin(Registry<StructureSet> registry, Optional<HolderSet<StructureSet>> optional, BiomeSource biomeSource, BiomeSource biomeSource2, long l) {
       super(registry, optional, biomeSource, biomeSource2, l);
   }

   @Unique
   private static long getGenerationSeed() {
       return generationSeed;
   }


   @Inject(method = "<clinit>", at = @At("RETURN"))
   private static void seedfix$clinit(CallbackInfo ci) {
       CODEC = RecordCodecBuilder.create((instance) -> {
           return NoiseChunkGenerator.method_41042(instance).and(instance.group(RegistryOps.getRegistry(Registry.NOISE_KEY).forGetter(generator -> {
               return ((NoiseBasedChunkGeneratorAccessor) generator).getNoiseRegistry();
           }), BiomeSource.CODEC.fieldOf("biome_source").forGetter(ChunkGenerator::getBiomeSource),
           Codec.LONG.fieldOf("seed").orElseGet(NoiseBasedChunkGeneratorMixin::getGenerationSeed).stable().forGetter(generator -> {
               return ((NoiseBasedChunkGeneratorAccessor) generator).getSeed();
           }), ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter(generator ->
               ((NoiseBasedChunkGeneratorAccessor) generator).getSettings()
           ))).apply(instance, instance.stable(NoiseChunkGenerator::new));
       });
   }

   @ModifyVariable(
           method = "<init>(Lnet/minecraft/util/registry/Registry;Lnet/minecraft/util/registry/Registry;Lnet/minecraft/world/biome/source/BiomeSource;Lnet/minecraft/world/biome/source/BiomeSource;JLnet/minecraft/util/Holder;)V",
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
