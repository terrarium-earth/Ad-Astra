package com.github.alexnijjar.ad_astra.screen.handler;

import com.github.alexnijjar.ad_astra.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.ad_astra.util.CustomInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.world.World;

public abstract class AbstractVehicleScreenHandler extends ScreenHandler {

	protected final VehicleEntity vehicle;
	protected final World world;
	protected final CustomInventory inventory;

	public AbstractVehicleScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory inventory, VehicleEntity entity) {
		this(type, syncId, inventory, entity, new Slot[] {});
	}

	// Add additional slots.
	public AbstractVehicleScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory inventory, VehicleEntity entity, Slot[] slots) {
		super(type, syncId);
		this.vehicle = entity;
		this.inventory = entity.getInventory();
		this.world = entity.getWorld();

		checkSize(inventory, this.vehicle.getInventorySize());

		for (Slot slot : slots) {
			this.addSlot(slot);
		}

		this.setPlayerInventory(inventory);
	}

	public VehicleEntity getVehicleEntity() {
		return this.vehicle;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player) && this.vehicle.isAlive() && this.vehicle.distanceTo(player) < 8.0f;
	}

	protected void setPlayerInventory(PlayerInventory inventory) {
		int m;
		int l;

		for (m = 0; m < 3; ++m) {
			for (l = 0; l < 9; ++l) {
				addSlot(new Slot(inventory, l + m * 9 + 9, 8 + l * 18, 84 + this.getPlayerInventoryOffset() + m * 18));
			}
		}

		for (m = 0; m < 9; ++m) {
			addSlot(new Slot(inventory, m, 8 + m * 18, 142 + this.getPlayerInventoryOffset()));
		}
	}

	public int getPlayerInventoryOffset() {
		return 0;
	}

	@Override
	public ItemStack quickTransfer(PlayerEntity player, int index) {
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot.hasStack()) {
			ItemStack originalStack = slot.getStack();
			newStack = originalStack.copy();
			if (index < this.inventory.size()) {
				if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
				return ItemStack.EMPTY;
			}

			if (originalStack.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}
		return newStack;
	}

	// Fixes a client sync issue.
	@Override
	public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
		super.onSlotClick(slotIndex, button, actionType, player);
		this.updateToClient();
	}
}