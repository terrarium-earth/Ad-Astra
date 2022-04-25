package net.mrscauthd.beyond_earth.blocks.machines;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.blocks.machines.entity.CoalGeneratorBlockEntity;
import net.mrscauthd.beyond_earth.registry.ModBlockEntities;

public class CoalGeneratorBlock extends AbstractMachineBlock {

    public CoalGeneratorBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean useFacing() {
        return true;
    }

    @Override
    protected boolean useLit() {
        return true;
    }

    @Override
    public CoalGeneratorBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CoalGeneratorBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, ModBlockEntities.COAL_GENERATOR_ENTITY, CoalGeneratorBlockEntity::serverTick);
    }
}
