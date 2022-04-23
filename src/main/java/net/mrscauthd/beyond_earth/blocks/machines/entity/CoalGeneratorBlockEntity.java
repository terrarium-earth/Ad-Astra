package net.mrscauthd.beyond_earth.blocks.machines.entity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.gui.screen_handlers.CoalGeneratorScreenHandler;
import net.mrscauthd.beyond_earth.recipes.ModRecipe;
import net.mrscauthd.beyond_earth.registry.ModBlockEntities;
import net.mrscauthd.beyond_earth.registry.ModRecipes;

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

    public static void serverTick(World world, BlockPos pos, BlockState state, AbstractMachineBlockEntity blockEntity) {
        if (blockEntity.useEnergy()) {
            CoalGeneratorBlockEntity entity = (CoalGeneratorBlockEntity) blockEntity;

            ItemStack input = entity.getItems().get(0);

            if (entity.getEnergy() < entity.getMaxGeneration()) {

                // Consume the fuel.
                if (entity.cookTime > 0) {
                    entity.cookTime--;
                    entity.cumulateEnergy();
                    // Check if the input is a valid fuel.
                } else if (!input.isEmpty()) {
                    ModRecipe recipe = entity.createRecipe(ModRecipes.GENERATING_RECIPE, input, false);
                    if (recipe != null) {
                        input.decrement(1);
                        entity.cookTimeTotal = recipe.getCookTime();
                        entity.cookTime = recipe.getCookTime();
                    }
                }
            }
            // Send energy to surrounding blocks.
            entity.energyOut();
        }
    }
}