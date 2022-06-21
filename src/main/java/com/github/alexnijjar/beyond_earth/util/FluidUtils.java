package com.github.alexnijjar.beyond_earth.util;

import java.util.List;
import java.util.function.Predicate;

import com.github.alexnijjar.beyond_earth.blocks.machines.entity.FluidMachineBlockEntity;
import com.github.alexnijjar.beyond_earth.items.FluidContainingItem;
import com.github.alexnijjar.beyond_earth.recipes.ConversionRecipe;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class FluidUtils {

	public static long dropletsToMillibuckets(long droplets) {
		return droplets / 81;
	}

	public static long millibucketsToDroplets(long millibuckets) {
		return millibuckets * 81;
	}

	public static Storage<FluidVariant> getStorage(ItemStack stack) {
		return ContainerItemContext.withInitial(stack).find(FluidStorage.ITEM);
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
			protected void onFinalCommit() {
				blockEntity.markDirty();
			}
		};
	}

	public static <T extends ConversionRecipe> SingleVariantStorage<FluidVariant> createTank(long tankSize) {
		return new SingleVariantStorage<>() {
			@Override
			protected FluidVariant getBlankVariant() {
				return FluidVariant.blank();
			}

			@Override
			protected long getCapacity(FluidVariant variant) {
				return tankSize * FluidConstants.BUCKET;
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
		ContainerItemContext context = ContainerItemContext.withInitial(inventory.getStack(insertSlot));
		Storage<FluidVariant> storage = context.find(FluidStorage.ITEM);

		if (storage != null) {
			try (Transaction transaction = Transaction.openOuter()) {
				if (StorageUtil.move(storage, inventory.inputTank, filter, Long.MAX_VALUE, transaction) > 0) {
					if (canInsert(inventory, extractSlot, context)) {
						consumeStorage(inventory, context, insertSlot, extractSlot);
						transaction.commit();
					}
				}

			}
		}
	}

	public static void insertFluidIntoTank(CustomInventory inventory, Storage<FluidVariant> inputTank, int insertSlot, int extractSlot, Predicate<FluidVariant> filter) {
		ContainerItemContext context = ContainerItemContext.withInitial(inventory.getStack(insertSlot));
		Storage<FluidVariant> storage = context.find(FluidStorage.ITEM);

		if (storage != null) {
			try (Transaction transaction = Transaction.openOuter()) {
				if (StorageUtil.move(storage, inputTank, filter, Long.MAX_VALUE, transaction) > 0) {
					if (canInsert(inventory, extractSlot, context)) {
						consumeStorage(inventory, context, insertSlot, extractSlot);
						transaction.commit();
					}
				}
			}
		}
	}

	// Extracts fluid from a tank and inserts it into a storage, such as a bucket.
	public static void extractFluidFromTank(FluidMachineBlockEntity inventory, int insertSlot, int extractSlot) {
		ItemStack extractCopy = inventory.getStack(insertSlot).copy();
		ContainerItemContext context = ContainerItemContext.withInitial(extractCopy);
		extractCopy.setCount(1);
		Storage<FluidVariant> storage = context.find(FluidStorage.ITEM);

		if (storage != null) {
			try (Transaction transaction = Transaction.openOuter()) {
				if (StorageUtil.move(inventory.outputTank, storage, f -> true, Long.MAX_VALUE, transaction) > 0) {
					if (canInsert(inventory, extractSlot, context)) {
						if (extractCopy.getItem() instanceof FluidContainingItem) {
							inventory.setStack(insertSlot, context.getItemVariant().toStack());
							inventory.markDirty();
						} else {
							consumeStorage(inventory, context, insertSlot, extractSlot);
						}
						transaction.commit();
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

	public static void consumeStorage(CustomInventory inventory, ContainerItemContext context, int insertSlot, int extractSlot) {
		ItemStack emptyStack = context.getItemVariant().toStack();
		inventory.setStack(extractSlot, new ItemStack(emptyStack.getItem(), inventory.getStack(extractSlot).getCount() + 1));
		inventory.stacks.get(insertSlot).decrement(1);
		inventory.markDirty();
	}

	public static boolean canInsert(Inventory inventory, int extractSlot, ContainerItemContext context) {
		ItemStack emptyStack = context.getItemVariant().toStack();
		ItemStack extractSlotStack = inventory.getStack(extractSlot);
		return extractSlotStack.isEmpty() || (emptyStack.isOf(extractSlotStack.getItem()) && extractSlotStack.getCount() <= extractSlotStack.getMaxCount());
	}
}