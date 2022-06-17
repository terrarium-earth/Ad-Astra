package com.github.alexnijjar.beyond_earth.blocks.machines.entity;

import org.jetbrains.annotations.Nullable;

import com.github.alexnijjar.beyond_earth.gui.screen_handlers.CoalGeneratorScreenHandler;
import com.github.alexnijjar.beyond_earth.recipes.CookingRecipe;
import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;
import com.github.alexnijjar.beyond_earth.registry.ModRecipes;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;

public class CoalGeneratorBlockEntity extends ProcessingMachineBlockEntity {

    public static final long MAX_ENERGY = 9000L;
    public static final long ENERGY_PER_TICK = 2L;

    public CoalGeneratorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.COAL_GENERATOR_ENTITY, blockPos, blockState);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new CoalGeneratorScreenHandler(syncId, inv, this);
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
    public long getMaxEnergyExtract() {
        return ENERGY_PER_TICK * 2;
    }

    // Only input.
    @Override
    public int getInventorySize() {
        return 1;
    }

    @Override
    public void tick() {
        if (!this.world.isClient) {
            if (this.usesEnergy()) {
                ItemStack input = this.getItems().get(0);

                if (this.getEnergy() < this.getMaxGeneration()) {

                    // Consume the fuel.
                    if (this.cookTime > 0) {
                        this.cookTime--;
                        this.cumulateEnergy();
                        this.setActive(true);
                        // Check if the input is a valid fuel.
                    } else if (!input.isEmpty()) {
                        CookingRecipe recipe = this.createRecipe(ModRecipes.GENERATING_RECIPE, input, false);
                        if (recipe != null) {
                            input.decrement(1);
                            this.cookTimeTotal = recipe.getCookTime();
                            this.cookTime = recipe.getCookTime();
                        }
                    } else {
                        this.setActive(false);
                    }
                }
                // Send energy to surrounding blocks.
                this.energyOut();
            }
        }
    }
}