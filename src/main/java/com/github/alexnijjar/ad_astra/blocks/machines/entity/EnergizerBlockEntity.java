package com.github.alexnijjar.beyond_earth.blocks.machines.entity;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.blocks.machines.EnergizerBlock;
import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;
import com.github.alexnijjar.beyond_earth.util.ModUtils;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;

public class EnergizerBlockEntity extends AbstractMachineBlockEntity {

    public static final long MAX_ENERGY = BeyondEarth.CONFIG.energizer.maxEnergy;
    public static final long ENERGY_PER_TICK = BeyondEarth.CONFIG.energizer.energyPerTick;

    public EnergizerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ENERGIZER, blockPos, blockState);
    }

    @Override
    public boolean usesEnergy() {
        return true;
    }

    @Override
    public int getInventorySize() {
        return 1;
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
        return ENERGY_PER_TICK * 128;
    }

    @Override
    public long getMaxEnergyExtract() {
        return ENERGY_PER_TICK * 8;
    }

    @Override
    public void tick() {
        if (!this.world.isClient) {

            ItemStack stack = this.getStack(0).copy();
            if (!stack.isEmpty() && this.hasEnergy()) {
                long moved = EnergyStorageUtil.move(this.getSideEnergyStorage(null), ContainerItemContext.ofSingleSlot(InventoryStorage.of(this, null).getSlots().get(0)).find(EnergyStorage.ITEM), this.getEnergyPerTick(), null);
                if (moved > 0) {
                    this.setActive(true);
                    if (this.world instanceof ServerWorld serverWorld) {
                        BlockPos pos = this.getPos();
                        ModUtils.spawnForcedParticles(serverWorld, ParticleTypes.ELECTRIC_SPARK, pos.getX() + 0.5, pos.getY() + 1.8, pos.getZ() + 0.5, 2, 0.1, 0.1, 0.1, 0.1);
                    }
                }
            } else {
                this.setActive(false);
            }

            float ratio = (float) this.getEnergy() / (float) this.getMaxGeneration();
            // convert the ratio into an int between 0 and 4
            int level = (int) (ratio * 4);
            if (ratio > 0.0002f) {
                level++;
            }
            // Set the block state to the correct level
            this.world.setBlockState(this.getPos(), this.getCachedState().with(EnergizerBlock.POWER, level));
            this.energyOut();
        }
    }
}