package com.github.alexnijjar.ad_astra.blocks.machines.entity;

import java.util.List;

import javax.annotation.Nullable;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.recipes.OxygenConversionRecipe;
import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;
import com.github.alexnijjar.ad_astra.registry.ModRecipes;
import com.github.alexnijjar.ad_astra.screen.handler.ConversionScreenHandler;
import com.github.alexnijjar.ad_astra.util.FluidUtils;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class OxygenLoaderBlockEntity extends FluidMachineBlockEntity {

    public OxygenLoaderBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.OXYGEN_LOADER, blockPos, blockState);
    }

    @Override
    public long getInputSize() {
        return AdAstra.CONFIG.oxygenLoader.tankBuckets;
    }

    @Override
    public long getOutputSize() {
        return AdAstra.CONFIG.oxygenLoader.tankBuckets;
    }

    @Override
    public boolean usesEnergy() {
        return true;
    }

    @Override
    public long getMaxGeneration() {
        return AdAstra.CONFIG.oxygenLoader.maxEnergy;
    }

    @Override
    public long getEnergyPerTick() {
        return AdAstra.CONFIG.oxygenLoader.energyPerTick;
    }

    @Override
    public long getMaxEnergyInsert() {
        return AdAstra.CONFIG.oxygenLoader.energyPerTick * 32;
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
        super.tick();
        if (!this.world.isClient) {
            ItemStack insertSlot = this.getItems().get(0);
            ItemStack extractSlot = this.getItems().get(1);
            ItemStack outputInsertSlot = this.getItems().get(2);
            ItemStack outputExtractSlot = this.getItems().get(3);

            if (!insertSlot.isEmpty() && extractSlot.getCount() < extractSlot.getMaxCount()) {
                ModRecipes.OXYGEN_CONVERSION_RECIPE.getRecipes(this.world);
                FluidUtils.insertFluidIntoTank(this, this.inputTank, 0, 1, f -> ModRecipes.OXYGEN_CONVERSION_RECIPE.getRecipes(this.world).stream().anyMatch(r -> r.getFluidInput().equals(f.getFluid())));
            }

            if (!outputInsertSlot.isEmpty() && outputExtractSlot.getCount() < outputExtractSlot.getMaxCount()) {
                FluidUtils.extractFluidFromTank(this, this.outputTank, 2, 3);
            }

            if (this.hasEnergy()) {
                List<OxygenConversionRecipe> recipes = ModRecipes.OXYGEN_CONVERSION_RECIPE.getRecipes(this.world);
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