package com.github.alexnijjar.ad_astra.items;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantItemStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import java.util.List;

public interface FluidContainingItem {

	long getTankSize();

	default boolean insertIntoTank(Storage<FluidVariant> storage, ItemStack stack) {
		try (Transaction transaction = Transaction.openOuter()) {
			if (storage.insert(this.getFluid(stack), this.getAmount(stack), transaction) == this.getAmount(stack)) {
				transaction.commit();
				return true;
			}
		}
		return false;
	}

	List<Fluid> getInputFluids();

	default long transferFluid(Storage<FluidVariant> from, Storage<FluidVariant> to) {
		return StorageUtil.move(from, to, f -> true, Long.MAX_VALUE, null);
	}

	default FluidVariant getFluid(ItemStack stack) {
		NbtCompound nbt = stack.getOrCreateNbt();
		if (nbt.contains("fluid")) {
			return FluidVariant.fromNbt(nbt.getCompound("fluid"));
		} else {
			return FluidVariant.blank();
		}
	}

	default void setFluid(ItemStack stack, FluidVariant variant) {
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

	class TankStorage extends SingleVariantItemStorage<FluidVariant> {

		private final FluidContainingItem item;

		public TankStorage(ItemStack stack, ContainerItemContext context) {
			super(context);
			item = (FluidContainingItem) stack.getItem();
		}

		@Override
		protected boolean canInsert(FluidVariant resource) {
			return item.getInputFluids().contains(resource.getFluid());
		}

		@Override
		protected FluidVariant getBlankResource() {
			return FluidVariant.blank();
		}

		@Override
		protected FluidVariant getResource(ItemVariant currentVariant) {
			return item.getFluid(currentVariant.toStack());
		}

		@Override
		protected long getAmount(ItemVariant currentVariant) {
			return item.getAmount(currentVariant.toStack());
		}

		@Override
		protected long getCapacity(FluidVariant variant) {
			return item.getTankSize();
		}

		@Override
		protected ItemVariant getUpdatedVariant(ItemVariant currentVariant, FluidVariant newResource, long newAmount) {
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
