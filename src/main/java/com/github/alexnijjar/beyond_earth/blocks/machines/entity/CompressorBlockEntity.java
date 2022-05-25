package com.github.alexnijjar.beyond_earth.blocks.machines.entity;

import com.github.alexnijjar.beyond_earth.gui.screen_handlers.CompressorScreenHandler;
import com.github.alexnijjar.beyond_earth.recipes.CookingRecipe;
import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;
import com.github.alexnijjar.beyond_earth.registry.ModRecipes;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class CompressorBlockEntity extends ProcessingMachineBlockEntity {

    public static final long MAX_ENERGY = 9000L;
    public static final long ENERGY_PER_TICK = 1L;

    public CompressorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.COMPRESSOR_ENTITY, blockPos, blockState);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new CompressorScreenHandler(syncId, inv, this);
    }

    @Override
    public long getMaxGeneration() {
        return MAX_ENERGY;
    }

    @Override
    public long getEnergyPerTick() {
        return ENERGY_PER_TICK;
    }

    @Override
    public long getMaxEnergyInsert() {
        return ENERGY_PER_TICK * 32;
    }

    // Input and output.
    @Override
    public int getInventorySize() {
        return 2;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        return slot == 0;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot == 1;
    }

    @SuppressWarnings("unused")
    public static void serverTick(World world, BlockPos pos, BlockState state, AbstractMachineBlockEntity blockEntity) {
        if (blockEntity.useEnergy()) {
            CompressorBlockEntity entity = (CompressorBlockEntity) blockEntity;

            ItemStack input = entity.getStack(0);
            ItemStack output = entity.getStack(1);

            if (entity.getEnergy() > 0) {

                if (!input.isEmpty() && (input.getItem().equals(entity.inputItem) || entity.inputItem == null)) {
                    if (entity.cookTime < entity.cookTimeTotal) {
                        entity.cookTime++;
                        entity.drainEnergy();

                    } else if (entity.outputStack != null) {
                        input.decrement(1);
                        entity.finishCooking();

                    } else {
                        CookingRecipe recipe = entity.createRecipe(ModRecipes.COMPRESSING_RECIPE, input, true);
                        if (recipe != null) {
                            entity.cookTimeTotal = recipe.getCookTime();
                            entity.cookTime = 0;
                        }
                    }
                } else if (entity.outputStack != null) {
                    entity.stopCooking();
                }
            }
        }
    }
}