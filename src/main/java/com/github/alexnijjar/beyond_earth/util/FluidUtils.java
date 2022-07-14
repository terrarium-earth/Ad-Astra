package com.github.alexnijjar.beyond_earth.util;

import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.github.alexnijjar.beyond_earth.blocks.machines.entity.FluidMachineBlockEntity;
import com.github.alexnijjar.beyond_earth.items.FluidContainingItem;
import com.github.alexnijjar.beyond_earth.recipes.ConversionRecipe;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.inventory.Inventory;
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
				if (convertAndMove(inventory.inputTank, inventory.outputTank, f -> true, millibucketsToDroplets(5), null, recipeOutputVariant, conversionRatio) > 0) {
					return true;
				}
			}
		}
		return false;
	}

	// Inserts fluid from an input item, such as a bucket, into the input tank. The item is then emptied and placed into the output
	// slot.
	public static void insertFluidIntoTank(Inventory inventory, Storage<FluidVariant> inputTank, int insertSlot, int extractSlot, Predicate<FluidVariant> filter) {
		ItemStack original = inventory.getStack(insertSlot).copy();
		inventory.getStack(insertSlot).setCount(1);
		ContainerItemContext context = ContainerItemContext.ofSingleSlot(InventoryStorage.of(inventory, null).getSlots().get(insertSlot));
		Storage<FluidVariant> storage = context.find(FluidStorage.ITEM);

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

	// Extracts fluid from an output, and inserts it into a fluid-holding item. The item is then moved to the output slot.
	public static void extractFluidFromTank(Inventory inventory, Storage<FluidVariant> outputTank, int insertSlot, int extractSlot) {
		ItemStack original = inventory.getStack(insertSlot).copy();
		inventory.getStack(insertSlot).setCount(1);
		ContainerItemContext context = ContainerItemContext.ofSingleSlot(InventoryStorage.of(inventory, null).getSlots().get(insertSlot));
		Storage<FluidVariant> storage = context.find(FluidStorage.ITEM);

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

	public static boolean canInsert(Inventory inventory, int extractSlot, ContainerItemContext context) {
		ItemStack extractSlotStack = inventory.getStack(extractSlot);
		return !(!extractSlotStack.isEmpty() && (!ItemStack.canCombine(extractSlotStack, extractSlotStack) || extractSlotStack.getCount() >= extractSlotStack.getMaxCount()));
	}

	public static long convertAndMove(@Nullable Storage<FluidVariant> from, @Nullable Storage<FluidVariant> to, Predicate<FluidVariant> filter, long maxAmount, @Nullable TransactionContext transaction, FluidVariant outputFluid, double conversionRatio) {
		if (from == null || to == null)
			return 0;

		long totalMoved = 0;

		try (Transaction iterationTransaction = Transaction.openNested(transaction)) {
			for (StorageView<FluidVariant> view : from.iterable(iterationTransaction)) {
				if (view.isResourceBlank())
					continue;
				FluidVariant resource = view.getResource();
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