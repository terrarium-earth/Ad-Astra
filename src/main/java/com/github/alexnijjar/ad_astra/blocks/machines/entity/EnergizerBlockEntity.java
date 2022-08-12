package com.github.alexnijjar.ad_astra.blocks.machines.entity;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.blocks.machines.AbstractMachineBlock;
import com.github.alexnijjar.ad_astra.blocks.machines.EnergizerBlock;
import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;
import com.github.alexnijjar.ad_astra.util.ModUtils;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;

public class EnergizerBlockEntity extends AbstractMachineBlockEntity {

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
		return AdAstra.CONFIG.energizer.maxEnergy;
	}

	@Override
	public long getEnergyPerTick() {
		return AdAstra.CONFIG.energizer.energyPerTick;
	}

	@Override
	public long getMaxEnergyInsert() {
		return AdAstra.CONFIG.energizer.energyPerTick * 8;
	}

	@Override
	public long getMaxEnergyExtract() {
		return AdAstra.CONFIG.energizer.energyPerTick * 12;
	}

	@Override
	public boolean canInsert(int slot, ItemStack stack, Direction dir) {
		return slot == 0;
	}

	@Override
	public boolean canExtract(int slot, ItemStack stack, Direction dir) {
		return slot == 0;
	}

	@Override
	public void tick() {
		if (!this.world.isClient) {

			if (!this.getCachedState().get(AbstractMachineBlock.POWERED)) {
				ItemStack stack = this.getStack(0);
				this.setActive(true);
				if (!stack.isEmpty()) {
					if (this.hasEnergy()) {
						long moved = EnergyStorageUtil.move(this.getSideEnergyStorage(null), ContainerItemContext.ofSingleSlot(InventoryStorage.of(this, null).getSlots().get(0)).find(EnergyStorage.ITEM), this.getEnergyPerTick(), null);
						if (moved > 0) {
							if (this.world instanceof ServerWorld serverWorld) {
								BlockPos pos = this.getPos();
								ModUtils.spawnForcedParticles(serverWorld, ParticleTypes.ELECTRIC_SPARK, pos.getX() + 0.5, pos.getY() + 1.8, pos.getZ() + 0.5, 2, 0.1, 0.1, 0.1, 0.1);
							}
						}
					}
				} else {
					this.setActive(false);
				}
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