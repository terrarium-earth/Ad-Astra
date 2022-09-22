package com.github.alexnijjar.ad_astra.items;

import java.util.List;

import earth.terrarium.botarium.api.fluid.FluidHolder;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantItemStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.LinkedBlockPosHashSet.Storage;

public interface FluidContainingItem {

	long getTankSize();

	default boolean insertIntoTank(Storage<FluidHolder> storage, ItemStack stack) {
		try (Transaction transaction = Transaction.openOuter()) {
			if (storage.insert(this.getFluid(stack), this.getAmount(stack), transaction) == this.getAmount(stack)) {
				transaction.commit();
				return true;
			}
		}
		return false;
	}

	List<Fluid> getInputFluids();

	default long transferFluid(Storage<FluidHolder> from, Storage<FluidHolder> to) {
		return StorageUtil.move(from, to, f -> true, Long.MAX_VALUE, null);
	}

	default FluidHolder getFluid(ItemStack stack) {
		NbtCompound nbt = stack.getOrCreateNbt();
		if (nbt.contains("fluid")) {
			return FluidHolder.fromNbt(nbt.getCompound("fluid"));
		} else {
			return FluidHolder.blank();
		}
	}

	default void setFluid(ItemStack stack, FluidHolder variant) {
		NbtCompound nbt = stack.getOrCreateNbt();
		nbt.put("fluid", variant.toNbt());
	}

	default long getAmount(ItemStack stack) {
		NbtCompound nbt = stack.getOrCreateNbt();
		if (nbt.contains("amount")) {
			return nbt.getLong("amount");
		} else {
			return 0;
		}
	}

	default void setAmount(ItemStack stack, long amount) {
		NbtCompound nbt = stack.getOrCreateNbt();
		nbt.putLong("amount", amount);
	}

	class TankStorage extends SingleVariantItemStorage<FluidHolder> {

		private final FluidContainingItem item;

		public TankStorage(ItemStack stack, ContainerItemContext context) {
			super(context);
			item = (FluidContainingItem) stack.getItem();
		}

		@Override
		protected boolean canInsert(FluidHolder resource) {
			return item.getInputFluids().contains(resource.getFluid());
		}

		@Override
		protected FluidHolder getBlankResource() {
			return FluidHolder.blank();
		}

		@Override
		protected FluidHolder getResource(ItemVariant currentVariant) {
			return item.getFluid(currentVariant.toStack());
		}

		@Override
		protected long getAmount(ItemVariant currentVariant) {
			return item.getAmount(currentVariant.toStack());
		}

		@Override
		protected long getCapacity(FluidHolder variant) {
			return item.getTankSize();
		}

		@Override
		protected ItemVariant getUpdatedVariant(ItemVariant currentVariant, FluidHolder newResource, long newAmount) {
			ItemStack stack = new ItemStack(currentVariant.getItem());
			NbtCompound nbt = currentVariant.copyNbt();

			if (nbt != null && !nbt.isEmpty()) {
				if (nbt.contains("fluid")) {
					nbt.remove("fluid");
				}
				if (nbt.contains("amount")) {
					nbt.remove("amount");
				}
				stack.setNbt(nbt);
			}

			if (!newResource.isBlank() && newAmount > 0) {
				item.setFluid(stack, newResource);
				item.setAmount(stack, newAmount);
			}
			return ItemVariant.of(stack);
		}
	}
}
