package com.github.alexnijjar.ad_astra.mixin;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.structure.StructureSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChunkGenerator.class)
public interface ChunkGeneratorAccessor {

	@Accessor("field_37053")
	Registry<StructureSet> getStructureSet();

	@Accessor("populationSource")
	BiomeSource getPopulationSource();
}