package com.github.alexnijjar.ad_astra.blocks.machines.entity;

import org.jetbrains.annotations.Nullable;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.recipes.CryoFuelConversionRecipe;
import com.github.alexnijjar.ad_astra.recipes.ModRecipeType;
import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;
import com.github.alexnijjar.ad_astra.registry.ModRecipes;
import com.github.alexnijjar.ad_astra.screen.handler.CryoFreezerScreenHandler;
import com.github.alexnijjar.ad_astra.util.FluidUtils;

import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class CryoFreezerBlockEntity extends FluidMachineBlockEntity {

	protected short cookTime;
	protected short cookTimeTotal;

	@Nullable
	protected Item inputItem;
	@Nullable
	protected Fluid outputFluid;

	public CryoFreezerBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ModBlockEntities.CRYO_FREEZER.get(), blockPos, blockState);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		this.cookTime = nbt.getShort("cookTime");
		this.cookTimeTotal = nbt.getShort("cookTimeTotal");
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		nbt.putShort("cookTime", this.cookTime);
		nbt.putShort("cookTimeTotal", this.cookTimeTotal);
	}

	public short getCookTime() {
		return this.cookTime;
	}

	public short getCookTimeTotal() {
		return this.cookTimeTotal;
	}

	public void finishCooking() {
		if (this.outputFluid != null) {
			CryoFuelConversionRecipe recipe = this.createRecipe(ModRecipes.CRYO_FUEL_CONVERSION_RECIPE, this.getStack(0), false);
			if (recipe != null) {
				try (Transaction transaction = Transaction.openOuter()) {
					if (this.inputTank.insert(FluidHolder.of(recipe.getFluidOutput()), FluidUtils.millibucketsToDroplets((long) (1000 * recipe.getConversionRatio())), transaction) > 0) {
						transaction.commit();
					}
				}
			}
		}
		this.stopCooking();
	}

	public void stopCooking() {
		this.cookTime = 0;
		this.cookTimeTotal = 0;
		this.outputFluid = null;
		this.inputItem = null;
		this.markDirty();
	}

	@Override
	public long getInputSize() {
		return FluidHooks.buckets(AdAstra.CONFIG.cryoFreezer.tankBuckets);
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
		return AdAstra.CONFIG.cryoFreezer.maxEnergy;
	}

	@Override
	public long getEnergyPerTick() {
		return AdAstra.CONFIG.cryoFreezer.energyPerTick;
	}

	@Override
	public long getMaxEnergyInsert() {
		return AdAstra.CONFIG.cryoFreezer.energyPerTick * 32;
	}

	@Override
	public int getInventorySize() {
		return 3;
	}

	@Override
	public boolean canInsert(int slot, ItemStack stack, Direction dir) {
		return slot == 0;
	}

	@Override
	public boolean canExtract(int slot, ItemStack stack, Direction dir) {
		return slot == 2;
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		return new CryoFreezerScreenHandler(syncId, inv, this);
	}

	public CryoFuelConversionRecipe createRecipe(ModRecipeType<CryoFuelConversionRecipe> type, ItemStack testStack, boolean checkOutput) {
		stopCooking();

		CryoFuelConversionRecipe recipe = type.findFirst(this.world, f -> f.test(testStack));

		if (recipe != null) {

			// Stop if something is already in the output.
			if (checkOutput) {
				ItemStack outputSlot = this.getStack(1);
				ItemStack output = recipe.getOutput();
				if (!outputSlot.isEmpty() && !outputSlot.getItem().equals(recipe.getOutput().getItem()) || outputSlot.getCount() + output.getCount() > outputSlot.getMaxCount()) {
					return null;
				}
			}

			this.outputFluid = recipe.getFluidOutput();
			this.inputItem = testStack.getItem();
		}

		return recipe;
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.world.isClient) {

			ItemStack input = this.getStack(0);
			ItemStack outputInsertSlot = this.getStack(1);
			ItemStack outputExtractSlot = this.getStack(2);

			if (!outputInsertSlot.isEmpty() && outputExtractSlot.getCount() < outputExtractSlot.getMaxCount()) {
				FluidUtils.extractFluidFromTank(this, tanks.getFluids().get(0), 1, 2);
			}

			if (this.hasEnergy()) {
				if ((!input.isEmpty() && (input.getItem().equals(this.inputItem) || this.inputItem == null)) && tanks.getFluids().get(0).getFluidAmount() < this.getInputSize()) {
					this.setActive(true);
					if (this.cookTime < this.cookTimeTotal) {
						this.cookTime++;
						this.drainEnergy();

					} else if (this.outputFluid != null) {
						this.finishCooking();
						input.decrement(1);

					} else {
						CryoFuelConversionRecipe recipe = this.createRecipe(ModRecipes.CRYO_FUEL_CONVERSION_RECIPE, input, false);
						if (recipe != null) {
							this.cookTimeTotal = 25;
							this.cookTime = 0;
						}
					}
				} else if (this.outputFluid != null) {
					this.stopCooking();
				} else {
					this.setActive(false);
				}
			}
		}
	}
}