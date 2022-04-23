package net.mrscauthd.beyond_earth.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.structure.StructureSet;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;

@Mixin(ChunkGenerator.class)
public interface ChunkGeneratorAccessor {

    @Accessor("field_37053")
    Registry<StructureSet> getStructureSet();

    @Accessor("populationSource")
    BiomeSource getPopulationSource();
}