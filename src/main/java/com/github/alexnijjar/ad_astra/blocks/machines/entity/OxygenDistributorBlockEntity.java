package com.github.alexnijjar.ad_astra.blocks.machines.entity;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.blocks.machines.AbstractMachineBlock;
import com.github.alexnijjar.ad_astra.recipes.OxygenConversionRecipe;
import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;
import com.github.alexnijjar.ad_astra.registry.ModParticleTypes;
import com.github.alexnijjar.ad_astra.registry.ModRecipes;
import com.github.alexnijjar.ad_astra.screen.handler.OxygenDistributorScreenHandler;
import com.github.alexnijjar.ad_astra.util.FluidUtils;
import com.github.alexnijjar.ad_astra.util.ModUtils;
import com.github.alexnijjar.ad_astra.util.algorithms.OxygenFillerAlgorithm;
import com.github.alexnijjar.ad_astra.util.entity.OxygenUtils;

import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class OxygenDistributorBlockEntity extends FluidMachineBlockEntity {

	private int oxygenFillCheckTicks = AdAstra.CONFIG.oxygenDistributor.refreshTicks;
	private boolean showOxygen = false;

	public OxygenDistributorBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ModBlockEntities.OXYGEN_DISTRIBUTOR, blockPos, blockState);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		showOxygen = nbt.getBoolean("showOxygen");
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		nbt.putBoolean("showOxygen", showOxygen);
	}

	public boolean shouldShowOxygen() {
		return this.showOxygen;
	}

	public void setShowOxygen(boolean value) {
		this.showOxygen = value;
	}

	@Override
	public long getInputSize() {
		return AdAstra.CONFIG.oxygenDistributor.tankBuckets;
	}

	@Override
	public long getOutputSize() {
		return AdAstra.CONFIG.oxygenDistributor.tankBuckets * 2;
	}

	@Override
	public boolean usesEnergy() {
		return true;
	}

	@Override
	public long getMaxGeneration() {
		return AdAstra.CONFIG.oxygenDistributor.maxEnergy;
	}

	@Override
	public long getEnergyPerTick() {
		return AdAstra.CONFIG.oxygenDistributor.fluidConversionEnergyPerTick;
	}

	@Override
	public long getMaxEnergyInsert() {
		return AdAstra.CONFIG.oxygenDistributor.fluidConversionEnergyPerTick * 512;
	}

	@Override
	public int getInventorySize() {
		return 2;
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		return new OxygenDistributorScreenHandler(syncId, inv, this);
	}

	@Override
	public boolean canInsert(int slot, ItemStack stack, Direction dir) {
		return slot == 0;
	}

	@Override
	public boolean canExtract(int slot, ItemStack stack, Direction dir) {
		return slot == 1;
	}

	public int getMaxBlockChecks() {
		return AdAstra.CONFIG.oxygenDistributor.maxBlockChecks;
	}

	public long getFluidToExtract(long oxygenBlocks, boolean client) {
		long value = (long) ((oxygenBlocks * AdAstra.CONFIG.oxygenDistributor.oxygenMultiplier) / 40);
		if (client) {
			return value;
		}
		return value == 0 ? 1 : value;
	}

	public long getEnergyToConsume(long oxygenBlocks, boolean client) {
		long value = (long) ((oxygenBlocks * AdAstra.CONFIG.oxygenDistributor.energyMultiplier) / 75);
		if (client) {
			return value;
		}
		return value == 0 ? 1 : value;
	}

	public void extractResources() {
		long oxygenBlocks = OxygenUtils.getOxygenBlocksCount(this.world, this.getPos());
		long amountOfFluidToExtract = this.getFluidToExtract(oxygenBlocks, false);
		long amountOfEnergyToConsume = this.getEnergyToConsume(oxygenBlocks, false);

		try (Transaction transaction = Transaction.openOuter()) {
			if (!this.outputTank.variant.isBlank()) {
				if (this.outputTank.extract(this.outputTank.getResource(), amountOfFluidToExtract, transaction) != amountOfFluidToExtract) {
					this.outputTank.amount = 0;
				}
				transaction.commit();
			}
		}

		if (this.drainEnergy(amountOfEnergyToConsume)) {
			ModUtils.spawnForcedParticles((ServerWorld) this.world, ModParticleTypes.OXYGEN_BUBBLE, this.getPos().getX() + 0.5, this.getPos().getY() + 0.5, this.getPos().getZ() + 0.5, 1, 0.0, 0.0, 0.0, 0.03);
		}
	}

	public boolean canDistribute(int oxygenBlocks) {
		long amountOfFluidToExtract = this.getFluidToExtract(oxygenBlocks, false);
		long amountOfEnergyToConsume = this.getEnergyToConsume(oxygenBlocks, false);
		if (this.outputTank.isResourceBlank()) {
			return false;
		} else if (this.getCachedState().get(AbstractMachineBlock.POWERED)) {
			return false;
		} else if (!this.canDrainEnergy(amountOfEnergyToConsume)) {
			return false;
		} else if (this.outputTank.variant.isBlank()) {
			return false;
		} else
			return this.outputTank.simulateExtract(this.outputTank.getResource(), amountOfFluidToExtract, null) == amountOfFluidToExtract;
	}

	@Override
	public void tick() {
		super.tick();

		ItemStack insertSlot = this.getItems().get(0);
		ItemStack extractSlot = this.getItems().get(1);

		// Convert the input fluid into oxygen
		if (!this.world.isClient) {
			if (!insertSlot.isEmpty() && extractSlot.getCount() < extractSlot.getMaxCount()) {
				ModRecipes.OXYGEN_CONVERSION_RECIPE.getRecipes(this.world);
				FluidUtils.insertFluidIntoTank(this, this.inputTank, 0, 1, f -> ModRecipes.OXYGEN_CONVERSION_RECIPE.getRecipes(this.world).stream().anyMatch(r -> r.getFluidInput().equals(f.getFluid())));
			}

			if (this.canDrainEnergy()) {
				List<OxygenConversionRecipe> recipes = ModRecipes.OXYGEN_CONVERSION_RECIPE.getRecipes(this.world);
				if (FluidUtils.convertFluid(this, recipes, 50)) {
					this.drainEnergy();
				}
			}
		}

		// Distribute the oxygen every certain amount of ticks. The algorithm is then run to determine how much oxygen to distribute.
		if (oxygenFillCheckTicks >= AdAstra.CONFIG.oxygenDistributor.refreshTicks) {
			this.runAlgorithm();
			oxygenFillCheckTicks = 0;
		} else {
			oxygenFillCheckTicks++;
		}

		if (!world.isClient) {
			boolean active = OxygenUtils.getOxygenBlocksCount(this.world, this.getPos()) > 0;
			this.setActive(active);

			if (active) {
				this.extractResources();
			}
		}

	}

	public void runAlgorithm() {
		if (this.world.isClient) {
			if (!this.getCachedState().get(AbstractMachineBlock.LIT)) {
				return;
			}
		} else {
			if (this.outputTank.amount <= 0 && this.energyStorage.amount <= 0) {
				return;
			}
		}

		OxygenFillerAlgorithm floodFiller = new OxygenFillerAlgorithm(this.world, this.getMaxBlockChecks());
		Set<BlockPos> positions = floodFiller.runAlgorithm(pos.up());

		if (this.canDistribute(positions.size())) {
			OxygenUtils.setEntry(this.world, pos, positions);
		} else if (!world.isClient) {
			OxygenUtils.removeEntry(this.world, this.getPos());
		}

		if (this.shouldShowOxygen()) {
			this.spawnParticles(positions);
		}
	}

	// Spawn the bubble particles in each oxygenated position. The "show" button must be clicked in the oxygen distributor GUI in order to work.
	public void spawnParticles(Set<BlockPos> positions) {
		if (!world.isClient && this.getCachedState().get(AbstractMachineBlock.LIT)) {
			for (BlockPos pos : positions) {
				ModUtils.spawnForcedParticles((ServerWorld) this.getWorld(), ModParticleTypes.OXYGEN_BUBBLE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1, 0.0, 0.0, 0.0, 0.0);
			}
		}
	}
}