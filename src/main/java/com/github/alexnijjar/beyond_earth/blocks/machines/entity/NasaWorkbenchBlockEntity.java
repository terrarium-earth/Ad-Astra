package com.github.alexnijjar.beyond_earth.blocks.machines.entity;

import com.github.alexnijjar.beyond_earth.gui.screen_handlers.NasaWorkbenchScreenHandler;
import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NasaWorkbenchBlockEntity extends AbstractMachineBlockEntity {

    public NasaWorkbenchBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.NASA_WORKBENCH_ENTITY, blockPos, blockState);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new NasaWorkbenchScreenHandler(syncId, inv, this);
    }

    @Override
    public int getInventorySize() {
        return 15;
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, AbstractMachineBlockEntity blockEntity) {
        if (blockEntity.useEnergy()) {

        }
    }
}