package com.github.alexnijjar.ad_astra.util;

import java.util.List;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import com.github.alexnijjar.ad_astra.blocks.machines.entity.FluidMachineBlockEntity;
import com.github.alexnijjar.ad_astra.items.FluidContainingItem;
import com.github.alexnijjar.ad_astra.recipes.ConversionRecipe;

import earth.terrarium.botarium.api.fluid.FluidHolder;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.LinkedBlockPosHashSet.Storage;

public class FluidUtils {

	public static long dropletsToMillibuckets(long droplets) {
		return droplets / 81;
	}

	public static long millibucketsToDroplets(long millibuckets) {
		return millibuckets * 81;
	}

	public static float dropletsToMillibucketsFloat(long droplets) {
		return droplets / 81.0f;
	}

	public static float millibucketsToDropletsFloat(long millibuckets) {
		return millibuckets * 81.0f;
	}

	/**
	 * Transfers and converts a fluid from the input tank to the output tank.
	 */
	public static <T extends ConversionRecipe> boolean convertFluid(FluidMachineBlockEntity inventory, List<T> recipes, int transferPerTick) {
		if (recipes == null) {
			return false;
		}
		FluidHolder inputTankFluid = inventory.tanks.getFluids().get(0);
		for (T recipe : recipes) {
			double conversionRatio = recipe.getConversionRatio();
			Fluid recipeInputVariant = recipe.getFluidInput();
			Fluid recipeOutputVariant = recipe.getFluidOutput();
			if (inputTankFluid.getFluid().equals(recipeInputVariant)) {
				if (convertAndMove(inventory.tanks.getFluids().get(0), inventory.tanks.getFluids().get(1), f -> true, millibucketsToDroplets(transferPerTick), null, recipeOutputVariant, conversionRatio) > 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Inserts fluid from an input item, such as a bucket, into the input tank. The item is then emptied and placed into the output slot.
	 */
	public static void insertFluidIntoTank(Inventory inventory, FluidHolder inputTank, int insertSlot, int extractSlot, Predicate<FluidHolder> filter) {
		ItemStack original = inventory.getStack(insertSlot).copy();
		inventory.getStack(insertSlot).setCount(1);
		ContainerItemContext context = ContainerItemContext.ofSingleSlot(InventoryStorage.of(inventory, null).getSlots().get(insertSlot));
		FluidHolder storage = context.find(FluidStorage.ITEM);

		try (Transaction transaction = Transaction.openOuter()) {
			if (StorageUtil.move(storage, inputTank, filter, Long.MAX_VALUE, transaction) > 0) {
				if (canInsert(inventory, extractSlot, context)) {
					ItemStack stack = context.getItemVariant().toStack();

					stack.setCount(inventory.getStack(extractSlot).getCount() + 1);
					original.decrement(1);
					inventory.setStack(extractSlot, stack);

					transaction.commit();

					inventory.setStack(insertSlot, original.copy());
					return;
				}
			}
		}

		// Allows you to place an empty bucket to collect the fluid from the input tank.
		try (Transaction transaction = Transaction.openOuter()) {
			if (StorageUtil.move(inputTank, storage, filter, Long.MAX_VALUE, transaction) > 0) {
				if (canInsert(inventory, extractSlot, context)) {
					ItemStack stack = context.getItemVariant().toStack();

					stack.setCount(inventory.getStack(extractSlot).getCount() + 1);
					original.decrement(1);
					inventory.setStack(extractSlot, stack);

					transaction.commit();
				}
			}
		}

		inventory.setStack(insertSlot, original.copy());
	}

	/**
	 * Extracts fluid from an output, and inserts it into a fluid-holding item. The item is then moved to the output slot.
	 */
	public static void extractFluidFromTank(Inventory inventory, FluidHolder outputTank, int insertSlot, int extractSlot) {
		ItemStack original = inventory.getStack(insertSlot).copy();
		inventory.getStack(insertSlot).setCount(1);
		ContainerItemContext context = ContainerItemContext.ofSingleSlot(InventoryStorage.of(inventory, null).getSlots().get(insertSlot));
		FluidHolder storage = context.find(FluidStorage.ITEM);

		try (Transaction transaction = Transaction.openOuter()) {
			if (StorageUtil.move(outputTank, storage, f -> true, Long.MAX_VALUE, transaction) > 0) {
				if (canInsert(inventory, extractSlot, context)) {
					ItemStack stack = context.getItemVariant().toStack();
					if (!(stack.getItem() instanceof FluidContainingItem)) {
						stack.setCount(inventory.getStack(extractSlot).getCount() + 1);
						original.decrement(1);
						inventory.setStack(extractSlot, stack);
					}
					transaction.commit();
				}
			}
		}

		if (!(context.getItemVariant().toStack().getItem() instanceof FluidContainingItem)) {
			inventory.setStack(insertSlot, original);
		}
	}

	/**
	 * Checks if a stack can be inserted into the output slot.
	 */
	public static boolean canInsert(Inventory inventory, int extractSlot, ContainerItemContext context) {
		ItemStack stack = context.getItemVariant().toStack();
		ItemStack extractSlotStack = inventory.getStack(extractSlot);
		return !(!extractSlotStack.isEmpty() && (!ItemStack.canCombine(extractSlotStack, stack) || extractSlotStack.getCount() >= extractSlotStack.getMaxCount()));
	}

	/**
	 * Move resources between two storages, matching the passed filter, and return the amount that was successfully transferred.
	 *
	 * @param from            The source storage. May be null.
	 * @param to              The target storage. May be null.
	 * @param filter          The filter for transferred resources. Only resources for which this filter returns {@code true} will be transferred. This filter will never be tested with a blank resource,
	 *                        and filters are encouraged to throw an exception if this guarantee is violated.
	 * @param maxAmount       The maximum amount that will be transferred.
	 * @param transaction     The transaction this transfer is part of, or {@code null} if a transaction should be opened just for this transfer.
	 * @param <T>             The type of resources to move.
	 * @param outputFluid     The fluid to output as the result.
	 * @param conversionRatio A ratio of the input to output fluid. For example, if 100 millibuckets are put in and the conversion ratio is 0.5, then 25 millibuckets will be output.
	 * @return The total amount of resources that was successfully transferred.
	 * @throws IllegalStateException If no transaction is passed and a transaction is already active on the current thread.
	 * @see {@link StorageUtil#move(Storage, Storage, Predicate, long, Transaction)}
	 */
	public static long convertAndMove(@Nullable FluidHolder from, @Nullable FluidHolder to, Predicate<FluidHolder> filter, long maxAmount, @Nullable TransactionContext transaction, FluidHolder outputFluid, double conversionRatio) {
		if (from == null || to == null)
			return 0;

		long totalMoved = 0;

		try (Transaction iterationTransaction = Transaction.openNested(transaction)) {
			for (StorageView<FluidHolder> view : from) {
				if (view.isResourceBlank())
					continue;
				FluidHolder resource = view.getResource();
				if (!filter.test(resource))
					continue;
				long maxExtracted;

				// check how much can be extracted
				try (Transaction extractionTestTransaction = iterationTransaction.openNested()) {
					maxExtracted = view.extract(resource, maxAmount - totalMoved, extractionTestTransaction);
					extractionTestTransaction.abort();
				}

				try (Transaction transferTransaction = iterationTransaction.openNested()) {
					// check how much can be inserted
					long accepted = to.insert(outputFluid, (long) (maxExtracted * conversionRatio), transferTransaction);

					// extract it, or rollback if the amounts don't match
					if (view.extract(resource, (long) (accepted / conversionRatio), transferTransaction) == (long) (accepted / conversionRatio)) {
						totalMoved += accepted;
						transferTransaction.commit();
					}
				}

				if (maxAmount == totalMoved) {
					// early return if nothing can be moved anymore
					iterationTransaction.commit();
					return totalMoved;
				}
			}

			iterationTransaction.commit();
		}

		return totalMoved;
	}

}