package earth.terrarium.ad_astra.registry;

import earth.terrarium.ad_astra.level.processor.StructureVoidProcessor;
import earth.terrarium.ad_astra.level.structure.LargeJigsawStructure;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

import java.util.function.Supplier;

public class ModStructures {
    public static final Supplier<StructureType<LargeJigsawStructure>> LARGE_JIGSAW_STRUCTURE = register("large_jigsaw_structure", () -> () -> LargeJigsawStructure.CODEC);
    public static final Supplier<StructureProcessorType<StructureVoidProcessor>> STRUCTURE_VOID_PROCESSOR = registerProcessor("structure_void_processor", () -> () -> StructureVoidProcessor.CODEC);

    private static <T extends StructureType<S>, S extends Structure> Supplier<T> register(String id, Supplier<T> object) {
        return ModRegistryHelpers.register(Registry.STRUCTURE_TYPES, id, object);
    }

    private static <T extends StructureProcessorType<S>, S extends StructureProcessor> Supplier<T> registerProcessor(String id, Supplier<T> object) {
        return ModRegistryHelpers.register(Registry.STRUCTURE_PROCESSOR, id, object);
    }

    public static void init() {
    }
}