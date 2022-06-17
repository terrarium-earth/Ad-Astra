package com.github.alexnijjar.beyond_earth.blocks.machines.entity;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.github.alexnijjar.beyond_earth.gui.screen_handlers.ConversionScreenHandler;
import com.github.alexnijjar.beyond_earth.recipes.FuelConversionRecipe;
import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;
import com.github.alexnijjar.beyond_earth.registry.ModRecipes;
import com.github.alexnijjar.beyond_earth.util.FluidUtils;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;

public class FuelRefineryBlockEntity extends FluidMachineBlockEntity {

    public static final long MAX_ENERGY = 9000L;
    public static final long ENERGY_PER_TICK = 10L;
    public static final int TANK_SIZE = 3;

    public FuelRefineryBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.FUEL_REFINERY_ENTITY, blockPos, blockState);
    }

    @Override
    public long getInputSize() {
        return TANK_SIZE;
    }

    @Override
    public long getOutputSize() {
        return TANK_SIZE;
    }

    @Override
    public boolean usesEnergy() {
        return true;
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

    @Override
    public int getInventorySize() {
        return 4;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ConversionScreenHandler<FuelRefineryBlockEntity>(syncId, inv, this);
    }

    @Override
    public void tick() {
        if (!this.world.isClient) {
            ItemStack insertSlot = this.getItems().get(0);
            ItemStack extractSlot = this.getItems().get(1);
            ItemStack outputInsertSlot = this.getItems().get(2);
            ItemStack outputExtractSlot = this.getItems().get(3);

            if (!insertSlot.isEmpty() && extractSlot.getCount() < extractSlot.getMaxCount()) {
                FluidUtils.insertFluidIntoTank(this, 0, 1);
            }

            if (!outputInsertSlot.isEmpty() && outputExtractSlot.getCount() < outputExtractSlot.getMaxCount()) {
                FluidUtils.extractFluidFromTank(this, 2, 3);
            }

            if (this.usesEnergy()) {
                if (this.getEnergy() > 0) {
                    List<FuelConversionRecipe> recipes = ModRecipes.FUEL_CONVERSION_RECIPE.getRecipes(this.world);
                    if (FluidUtils.convertFluid(this, recipes)) {
                        this.drainEnergy();
                        this.setActive(true);
                    } else {
                        this.setActive(false);
                    }
                } else {
                    this.setActive(false);
                }
            }
        }
    }
}