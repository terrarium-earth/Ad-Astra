package earth.terrarium.ad_astra.common.registry;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.level.processor.StructureVoidProcessor;
import earth.terrarium.ad_astra.common.level.structure.LargeJigsawStructure;
import earth.terrarium.botarium.api.registry.RegistryHolder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

import java.util.function.Supplier;

public class ModStructures {
    public static final RegistryHolder<StructureType<?>> STRUCTURE_TYPES = new RegistryHolder<>(Registry.STRUCTURE_TYPES, AdAstra.MOD_ID);
    public static final RegistryHolder<StructureProcessorType<?>> STRUCTURE_PROCESSORS = new RegistryHolder<>(Registry.STRUCTURE_PROCESSOR, AdAstra.MOD_ID);

    public static final Supplier<StructureType<LargeJigsawStructure>> LARGE_JIGSAW_STRUCTURE = STRUCTURE_TYPES.register("large_jigsaw_structure", () -> () -> LargeJigsawStructure.CODEC);
    public static final Supplier<StructureProcessorType<StructureVoidProcessor>> STRUCTURE_VOID_PROCESSOR = STRUCTURE_PROCESSORS.register("structure_void_processor", () -> () -> StructureVoidProcessor.CODEC);
}