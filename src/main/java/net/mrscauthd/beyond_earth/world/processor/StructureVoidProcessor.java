package net.mrscauthd.beyond_earth.world.processor;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.mrscauthd.beyond_earth.registry.ModStructures;
import org.jetbrains.annotations.Nullable;

public class StructureVoidProcessor extends StructureProcessor {

    public static final Codec<StructureVoidProcessor> CODEC = Codec.unit(StructureVoidProcessor::new);

    private StructureVoidProcessor() {

    }

    @Nullable
    @Override
    public Structure.StructureBlockInfo process(WorldView world, BlockPos pos, BlockPos pivot, Structure.StructureBlockInfo structureBlockInfo, Structure.StructureBlockInfo structureBlockInfo2, StructurePlacementData data) {
        if (structureBlockInfo2.state.getBlock().equals(Blocks.STRUCTURE_VOID)) {
            return null;
        }
        if (world.getBlockState(structureBlockInfo2.pos).isAir()) {
            return null;
        }

        return structureBlockInfo2;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModStructures.STRUCTURE_VOID_PROCESSOR;
    }
}