package com.github.alexnijjar.beyond_earth.blocks.machines.entity;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.github.alexnijjar.beyond_earth.gui.screen_handlers.NasaWorkbenchScreenHandler;
import com.github.alexnijjar.beyond_earth.recipes.NasaWorkbenchRecipe;
import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;
import com.github.alexnijjar.beyond_earth.registry.ModRecipes;
import com.github.alexnijjar.beyond_earth.util.ModUtils;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class NasaWorkbenchBlockEntity extends AbstractMachineBlockEntity {

    private List<NasaWorkbenchRecipe> acceptedInputs = new ArrayList<>();

    public NasaWorkbenchBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.NASA_WORKBENCH, blockPos, blockState);
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

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        return slot < 14;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }

    public void spawnWorkingParticles() {
        if (this.world instanceof ServerWorld serverWorld) {
            BlockPos pos = this.getPos();
            ModUtils.spawnForcedParticles(serverWorld, ParticleTypes.CRIT, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 10, 0.1, 0.1, 0.1, 0.1);
        }
    }

    public List<NasaWorkbenchRecipe> getAcceptedInputs() {
        return this.acceptedInputs;
    }

    public void spawnResultParticles() {
        if (this.world instanceof ServerWorld serverWorld) {
            BlockPos pos = this.getPos();
            ModUtils.spawnForcedParticles(serverWorld, ParticleTypes.TOTEM_OF_UNDYING, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 100, 0.1, 0.1, 0.1, 0.7);
            this.world.playSound(null, pos, SoundEvents.ITEM_TOTEM_USE, SoundCategory.NEUTRAL, 1.0f, 1.0f);
        }
    }

    public void spawnOutputAndClearInput(List<Integer> stackCounts, ItemStack output) {
        BlockPos pos = this.getPos();
        ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 2.5, pos.getZ() + 0.5, output.copy());
        itemEntity.setVelocity(itemEntity.getVelocity().multiply(0.5));
        this.world.spawnEntity(itemEntity);
        itemEntity.setToDefaultPickupDelay();

        for (int i = 0; i < this.getItems().size() - 1; i++) {
            this.getItems().get(i).decrement(stackCounts.get(i));
        }

    }

    @Override
    public void tick() {
        if (!this.world.isClient) {
            for (ItemStack input : this.getItems()) {
                if (!input.isEmpty()) {
                    NasaWorkbenchRecipe recipe = ModRecipes.NASA_WORKBENCH_RECIPE.findFirst(world, f -> f.test(input));
                    if (recipe != null) {
                        this.spawnWorkingParticles();
                        this.setActive(true);
                    } else {
                        this.setActive(false);
                    }
                }
            }
        }
    }
}