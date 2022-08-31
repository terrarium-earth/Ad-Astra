package com.github.alexnijjar.ad_astra.blocks.machines.entity;

import org.jetbrains.annotations.Nullable;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.blocks.machines.AbstractMachineBlock;
import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;
import com.github.alexnijjar.ad_astra.registry.ModParticleTypes;
import com.github.alexnijjar.ad_astra.screen.handler.WaterPumpScreenHandler;
import com.github.alexnijjar.ad_astra.util.ModUtils;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class WaterPumpBlockEntity extends FluidMachineBlockEntity {

	public static final Direction[] INSERT_DIRECTIONS = { Direction.UP, Direction.SOUTH };

	private long waterExtracted;

	public WaterPumpBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ModBlockEntities.WATER_PUMP, blockPos, blockState);
	}

	@Override
	public long getInputSize() {
		return AdAstra.CONFIG.waterPump.tankBuckets;
	}

	@Override
	public long getOutputSize() {
		return 0;
	}

	@Override
	public boolean usesEnergy() {
		return true;
	}

	@Override
	public long getMaxGeneration() {
		return AdAstra.CONFIG.waterPump.maxEnergy;
	}

	@Override
	public long getEnergyPerTick() {
		return AdAstra.CONFIG.waterPump.energyPerTick;
	}

	@Override
	public long getMaxEnergyInsert() {
		return AdAstra.CONFIG.waterPump.energyPerTick * 32;
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		return new WaterPumpScreenHandler(syncId, inv, this);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		this.waterExtracted = nbt.getLong("waterExtracted");
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		nbt.putLong("waterExtracted", this.waterExtracted);
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.world.isClient) {
			FluidVariant waterFluid = FluidVariant.of(Fluids.WATER);
			BlockState water = this.world.getBlockState(this.getPos().down());
			if (this.inputTank.getAmount() < this.inputTank.getCapacity()) {
				if (water.isOf(Blocks.WATER) && water.get(FluidBlock.LEVEL) == 0) {

					// Drain the water block and add it to the tank.
					if (!this.getCachedState().get(AbstractMachineBlock.POWERED) && this.hasEnergy()) {
						this.setActive(true);
						ModUtils.spawnForcedParticles((ServerWorld) this.world, ModParticleTypes.OXYGEN_BUBBLE, this.getPos().getX() + 0.5, this.getPos().getY() - 0.5, this.getPos().getZ() + 0.5, 1, 0.0, 0.0, 0.0, 0.01);
						this.drainEnergy();
						waterExtracted += AdAstra.CONFIG.waterPump.transferPerTick;
						try (Transaction transaction = Transaction.openOuter()) {
							this.inputTank.insert(waterFluid, AdAstra.CONFIG.waterPump.transferPerTick, transaction);
							transaction.commit();
						}
					} else {
						this.setActive(false);
					}

					if (AdAstra.CONFIG.waterPump.deleteWaterBelowWaterPump) {
						// Delete the water block after it has been fully extracted.
						if (waterExtracted >= FluidConstants.BLOCK) {
							waterExtracted = 0;
							world.setBlockState(this.getPos().down(), Blocks.AIR.getDefaultState());
						}
					}
				}
			} else {
				this.setActive(false);
			}

			if (this.hasEnergy()) {
				if (this.outputTank.amount < this.outputTank.getCapacity()) {
					this.drainEnergy();
				}
				// Insert the fluid into nearby tanks.
				for (Direction direction : new Direction[] { Direction.UP, this.getCachedState().get(AbstractMachineBlock.FACING) }) {
					Storage<FluidVariant> storage = FluidStorage.SIDED.find(this.world, this.getPos().offset(direction), direction);
					if (storage != null) {
						try (Transaction transaction = Transaction.openOuter()) {
							long transferPerTick = AdAstra.CONFIG.waterPump.transferPerTick;
							if (this.inputTank.extract(waterFluid, transferPerTick, transaction) == transferPerTick && storage.insert(waterFluid, transferPerTick, transaction) == transferPerTick) {
								transaction.commit();
							}
						}
					}
				}
			}
		}
	}
}