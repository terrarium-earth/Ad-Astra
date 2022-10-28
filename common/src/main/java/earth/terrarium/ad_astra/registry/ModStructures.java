package earth.terrarium.ad_astra.registry;

import dev.architectury.registry.registries.DeferredRegister;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.world.processor.StructureVoidProcessor;
import earth.terrarium.ad_astra.world.structures.LargeJigsawStructure;
import net.minecraft.structure.StructureType;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.registry.Registry;

public class ModStructures {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(AdAstra.MOD_ID, Registry.STRUCTURE_TYPE_WORLDGEN);
    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPES = DeferredRegister.create(AdAstra.MOD_ID, Registry.STRUCTURE_PROCESSOR_KEY);

    public static final StructureProcessorType<StructureVoidProcessor> STRUCTURE_VOID_PROCESSOR = () -> StructureVoidProcessor.CODEC;
    public static final StructureType<LargeJigsawStructure> LARGE_JIGSAW_STRUCTURE = () -> LargeJigsawStructure.CODEC;

    public static void register() {
        STRUCTURE_TYPES.register("large_jigsaw_structure", () -> LARGE_JIGSAW_STRUCTURE);
        STRUCTURE_PROCESSOR_TYPES.register("structure_void_processor", () -> STRUCTURE_VOID_PROCESSOR);
        STRUCTURE_PROCESSOR_TYPES.register();
        STRUCTURE_TYPES.register();
    }

}