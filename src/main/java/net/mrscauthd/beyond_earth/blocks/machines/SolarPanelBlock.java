package net.mrscauthd.beyond_earth.blocks.machines;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.blocks.machines.entity.SolarPanelBlockEntity;
import net.mrscauthd.beyond_earth.registry.ModBlockEntities;

public class SolarPanelBlock extends AbstractMachineBlock {

    public SolarPanelBlock(Settings settings) {
        super(settings);
    }

    @Override
    public SolarPanelBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SolarPanelBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, ModBlockEntities.SOLAR_PANEL_ENTITY, SolarPanelBlockEntity::serverTick);
    }
}