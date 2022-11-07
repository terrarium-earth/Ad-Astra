package earth.terrarium.ad_astra.world.processor;

import com.mojang.serialization.Codec;
import earth.terrarium.ad_astra.registry.ModStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

public class StructureVoidProcessor extends StructureProcessor {

    public static final Codec<StructureVoidProcessor> CODEC = Codec.unit(StructureVoidProcessor::new);

    private StructureVoidProcessor() {
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader level, BlockPos pos, BlockPos pivot, StructureTemplate.StructureBlockInfo structureBlockInfo, StructureTemplate.StructureBlockInfo structureBlockInfo2, StructurePlaceSettings data) {
        if (structureBlockInfo2.state.getBlock().equals(Blocks.STRUCTURE_VOID)) {
            return null;
        }
        if (level.getBlockState(structureBlockInfo2.pos).isAir()) {
            return null;
        }

        return structureBlockInfo2;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModStructures.STRUCTURE_VOID_PROCESSOR.get();
    }
}