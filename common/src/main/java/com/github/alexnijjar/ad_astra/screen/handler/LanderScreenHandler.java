package com.github.alexnijjar.ad_astra.screen.handler;

import com.github.alexnijjar.ad_astra.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.ad_astra.registry.ModScreenHandlers;
import com.github.alexnijjar.ad_astra.screen.NoInventorySlot;

import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class LanderScreenHandler extends AbstractVehicleScreenHandler {

	public LanderScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
		this(syncId, inventory, (VehicleEntity) inventory.player.world.getEntityById(buf.readInt()));
	}

	public LanderScreenHandler(int syncId, PlayerInventory inventory, VehicleEntity entity) {
		super(ModScreenHandlers.LANDER_SCREEN_HANDLER.get(), syncId, inventory, entity, new Slot[] {

				// Left input slot
				new NoInventorySlot(entity.getInventory(), 0, 19, 58) {
					@Override
					public boolean canInsert(ItemStack stack) {
						if (!super.canInsert(stack)) {
							return false;
						}
						return !FluidHooks.isFluidContainingItem(stack);
					}
				},
				// Left output slot
				new NoInventorySlot(entity.getInventory(), 1, 48, 58) {
					@Override
					public boolean canInsert(ItemStack stack) {
						return false;
					}
				},

				// Inventory
				new NoInventorySlot(entity.getInventory(), 2, 85, 31),
				//
				new NoInventorySlot(entity.getInventory(), 3, 85 + 18, 31),
				//
				new NoInventorySlot(entity.getInventory(), 4, 85 + 18 * 2, 31),
				//
				new NoInventorySlot(entity.getInventory(), 5, 85 + 18 * 3, 31),
				//
				new NoInventorySlot(entity.getInventory(), 6, 85, 49),
				//
				new NoInventorySlot(entity.getInventory(), 7, 85 + 18, 49),
				//
				new NoInventorySlot(entity.getInventory(), 8, 85 + 18 * 2, 49),
				//
				new NoInventorySlot(entity.getInventory(), 9, 85 + 18 * 3, 49),

				// Rocket slot.
				new Slot(entity.getInventory(), 10, 34, 27) {
					@Override
					public boolean canInsert(ItemStack stack) {
						return false;
					}
				}, });
	}

	@Override
	public int getPlayerInventoryOffset() {
		return 8;
	}
}