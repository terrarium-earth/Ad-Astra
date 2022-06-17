package com.github.alexnijjar.beyond_earth.util;

import java.util.List;
import java.util.function.Predicate;

import com.github.alexnijjar.beyond_earth.blocks.machines.entity.FluidMachineBlockEntity;
import com.github.alexnijjar.beyond_earth.items.armour.SpaceSuit;
import com.github.alexnijjar.beyond_earth.recipes.ConversionRecipe;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.item.ItemStack;

public class FluidUtils {

	public static long dropletsToMillibuckets(long droplets) {
		return droplets / 81;
	}

	public static long millibucketsToDroplets(long millibuckets) {
		return millibuckets * 81;
	}

	public static <T extends ConversionRecipe> SingleVariantStorage<FluidVariant> createTank(FluidMachineBlockEntity blockEntity, long tankSize) {
		return new SingleVariantStorage<>() {
			@Override
			protected FluidVariant getBlankVariant() {
				return FluidVariant.blank();
			}

			@Override
			protected long getCapacity(FluidVariant variant) {
				return tankSize * FluidConstants.BUCKET;
			}

			@Override
			protected boolean canInsert(FluidVariant variant) {
				return true;
			}

			@Override
			protected void onFinalCommit() {
				blockEntity.markDirty();
			}
		};
	}

	// Transfers and converts a fluid from the input tank to the output tank.
	public static <T extends ConversionRecipe> boolean convertFluid(FluidMachineBlockEntity inventory, List<T> recipes) {
		FluidVariant inputTankFluid = inventory.inputTank.variant;
		for (T recipe : recipes) {
			double conversionRatio = recipe.getConversionRatio();
			FluidVariant recipeInputVariant = FluidVariant.of(recipe.getFluidInput());
			FluidVariant recipeOutputVariant = FluidVariant.of(recipe.getFluidOutput());
			if (inputTankFluid.equals(recipeInputVariant)) {

				try (Transaction transaction = Transaction.openOuter()) {
					long maxFluid = millibucketsToDroplets(5);
					long convertedMaxFluid = (long) (maxFluid * conversionRatio);
					if (inventory.inputTank.extract(recipeInputVariant, maxFluid, transaction) == maxFluid && inventory.outputTank.insert(recipeOutputVariant, convertedMaxFluid, transaction) == convertedMaxFluid) {
						transaction.commit();
						return true;
					}
				}
			}
		}
		return false;
	}

	// Inserts a fluid storage, such as a bucket into a tank.
	public static void insertFluidIntoTank(FluidMachineBlockEntity inventory, int insertSlot, int extractSlot, Predicate<FluidVariant> filter) {
		if (inventory.canInsert(extractSlot, inventory.getStack(insertSlot), null)) {
			ContainerItemContext context = ContainerItemContext.withInitial(inventory.getStack(insertSlot));
			Storage<FluidVariant> storage = context.find(FluidStorage.ITEM);

			if (storage != null) {
				if (StorageUtil.move(storage, inventory.inputTank, filter, Long.MAX_VALUE, null) > 0) {
					consumeStorage(inventory, context, insertSlot, extractSlot);
				}
			}
		}
	}

	// Extracts fluid from a tank and inserts it into a storage, such as a bucket.
	public static void extractFluidFromTank(FluidMachineBlockEntity inventory, int insertSlot, int extractSlot) {
		if (inventory.canInsert(extractSlot, inventory.getStack(insertSlot), null)) {
			ItemStack extractCopy = inventory.getStack(insertSlot).copy();
			extractCopy.setCount(1);
			ContainerItemContext context = ContainerItemContext.withInitial(extractCopy);
			Storage<FluidVariant> storage = context.find(FluidStorage.ITEM);

			if (storage != null) {
				if (StorageUtil.move(inventory.outputTank, storage, f -> true, Long.MAX_VALUE, null) > 0) {
					if (extractCopy.getItem() instanceof SpaceSuit) {
						inventory.setStack(insertSlot, context.getItemVariant().toStack());
						inventory.markDirty();
					} else {
						consumeStorage(inventory, context, insertSlot, extractSlot);
					}
				}
			}
		}
	}

	// Decrements the input fluid storage and inserts and empty storage, such as an empty bucket into an output slot.
	public static void consumeStorage(FluidMachineBlockEntity inventory, ContainerItemContext context, int insertSlot, int extractSlot) {
		ItemStack emptyStack = context.getItemVariant().toStack();
		inventory.setStack(extractSlot, new ItemStack(emptyStack.getItem(), inventory.getStack(extractSlot).getCount() + 1));
		inventory.getItems().get(insertSlot).decrement(1);
		inventory.markDirty();
	}
}