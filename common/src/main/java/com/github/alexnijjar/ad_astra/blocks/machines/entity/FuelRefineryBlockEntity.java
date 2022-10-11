package com.github.alexnijjar.ad_astra.blocks.machines.entity;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.recipes.FluidConversionRecipe;
import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;
import com.github.alexnijjar.ad_astra.registry.ModRecipes;
import com.github.alexnijjar.ad_astra.screen.handler.ConversionScreenHandler;
import com.github.alexnijjar.ad_astra.util.FluidUtils;

import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class FuelRefineryBlockEntity extends FluidMachineBlockEntity {

    public FuelRefineryBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.FUEL_REFINERY.get(), blockPos, blockState);
    }

    @Override
    public long getInputTankCapacity() {
        return FluidHooks.buckets(AdAstra.CONFIG.fuelRefinery.tankBuckets);
    }

    @Override
    public long getOutputTankCapacity() {
        return FluidHooks.buckets(AdAstra.CONFIG.fuelRefinery.tankBuckets);
    }

    @Override
    public boolean usesEnergy() {
        return true;
    }

    @Override
    public long getMaxGeneration() {
        return AdAstra.CONFIG.fuelRefinery.maxEnergy;
    }

    @Override
    public long getEnergyPerTick() {
        return AdAstra.CONFIG.fuelRefinery.energyPerTick;
    }

    @Override
    public long getMaxEnergyInsert() {
        return AdAstra.CONFIG.fuelRefinery.energyPerTick * 32;
    }

    @Override
    public int getInventorySize() {
        return 4;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        return slot == 0;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot == 1 || slot == 3;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ConversionScreenHandler(syncId, inv, this);
    }

    @Override
    public void tick() {
        if (!this.world.isClient) {
            ItemStack insertSlot = this.getItems().get(0);
            ItemStack extractSlot = this.getItems().get(1);
            ItemStack outputInsertSlot = this.getItems().get(2);
            ItemStack outputExtractSlot = this.getItems().get(3);

            if (!insertSlot.isEmpty() && extractSlot.getCount() < extractSlot.getMaxCount() && FluidHooks.isFluidContainingItem(insertSlot)) {
                FluidUtils.insertFluidFromItem(insertSlot, 0, this, f -> ModRecipes.FUEL_CONVERSION_RECIPE.get().getRecipes(this.world).stream().anyMatch(r -> r.matches(f.getFluid())));
            }

            if (!outputInsertSlot.isEmpty() && outputExtractSlot.getCount() < outputExtractSlot.getMaxCount()) {
                FluidUtils.extractFluidFromItem(outputExtractSlot, 0, this, f -> true);
            }

            if (this.hasEnergy()) {
                List<FluidConversionRecipe> recipes = ModRecipes.FUEL_CONVERSION_RECIPE.get().getRecipes(this.world);
                if (FluidUtils.convertFluid(this, recipes, 10)) {
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