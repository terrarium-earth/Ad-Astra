package com.github.alexnijjar.beyond_earth.items;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantItemStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public interface FluidContainingItem {

	public long getTankSize();

	public default boolean insertIntoTank(Storage<FluidVariant> storage, ItemStack stack) {
		try (Transaction transaction = Transaction.openOuter()) {
			if (storage.insert(this.getFluid(stack), this.getAmount(stack), transaction) == this.getAmount(stack)) {
				transaction.commit();
				return true;
			}
		}
		return false;
	}

	public default long transferFluid(Storage<FluidVariant> from, Storage<FluidVariant> to) {
		return StorageUtil.move(from, to, f -> true, Long.MAX_VALUE, null);
	}

	public default FluidVariant getFluid(ItemStack stack) {
		NbtCompound nbt = stack.getOrCreateNbt();
		if (nbt.contains("Fluid")) {
			return FluidVariant.fromNbt(nbt.getCompound("Fluid"));
		} else {
			return FluidVariant.blank();
		}
	}

	public default void setFluid(ItemStack stack, FluidVariant variant) {
		NbtCompound nbt = stack.getOrCreateNbt();
		nbt.put("Fluid", variant.toNbt());
	}

	public default long getAmount(ItemStack stack) {
		NbtCompound nbt = stack.getOrCreateNbt();
		if (nbt.contains("Amount")) {
			return nbt.getLong("Amount");
		} else {
			return 0;
		}
	}

	public default void setAmount(ItemStack stack, long amount) {
		NbtCompound nbt = stack.getOrCreateNbt();
		nbt.putLong("Amount", amount);
	}

	public class TankStorage extends SingleVariantItemStorage<FluidVariant> {

		private FluidContainingItem item;

		public TankStorage(ItemStack stack, ContainerItemContext context) {
			super(context);
			item = (FluidContainingItem) stack.getItem();
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
			ItemStack stack = currentVariant.toStack();
			if (!newResource.isBlank() && newAmount > 0) {
				item.setFluid(stack, newResource);
				item.setAmount(stack, newAmount);
			}
			return ItemVariant.of(stack);
		}
	}
}
