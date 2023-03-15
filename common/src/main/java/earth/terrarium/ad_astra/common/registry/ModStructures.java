package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.world.processor.StructureVoidProcessor;
import earth.terrarium.ad_astra.common.world.structure.LargeJigsawStructure;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public class ModStructures {
    public static final ResourcefulRegistry<StructureType<?>> STRUCTURE_TYPES = ResourcefulRegistries.create(BuiltInRegistries.STRUCTURE_TYPE, AdAstra.MOD_ID);
    public static final ResourcefulRegistry<StructureProcessorType<?>> STRUCTURE_PROCESSORS = ResourcefulRegistries.create(BuiltInRegistries.STRUCTURE_PROCESSOR, AdAstra.MOD_ID);

    public static final RegistryEntry<StructureType<LargeJigsawStructure>> LARGE_JIGSAW_STRUCTURE = STRUCTURE_TYPES.register("large_jigsaw_structure", () -> () -> LargeJigsawStructure.CODEC);
    public static final RegistryEntry<StructureProcessorType<StructureVoidProcessor>> STRUCTURE_VOID_PROCESSOR = STRUCTURE_PROCESSORS.register("structure_void_processor", () -> () -> StructureVoidProcessor.CODEC);
}