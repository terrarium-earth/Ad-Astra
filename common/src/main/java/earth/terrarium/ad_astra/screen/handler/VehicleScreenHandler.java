package earth.terrarium.ad_astra.screen.handler;

import earth.terrarium.ad_astra.entities.vehicles.VehicleEntity;
import earth.terrarium.ad_astra.registry.ModScreenHandlers;
import earth.terrarium.ad_astra.screen.NoInventorySlot;

import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class VehicleScreenHandler extends AbstractVehicleScreenHandler {

	public VehicleScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
		this(syncId, inventory, (VehicleEntity) inventory.player.world.getEntityById(buf.readInt()));
	}

	public VehicleScreenHandler(int syncId, PlayerInventory inventory, VehicleEntity entity) {
		super(ModScreenHandlers.VEHICLE_SCREEN_HANDLER.get(), syncId, inventory, entity, new Slot[] {

				// Left input slot.
				new NoInventorySlot(entity.getInventory(), 0, 20, 24) {
					@Override
					public boolean canInsert(ItemStack stack) {
						if (!super.canInsert(stack)) {
							return false;
						}
						return FluidHooks.isFluidContainingItem(stack);
					}
				},
				// Left output slot.
				new Slot(entity.getInventory(), 1, 20, 54) {
					@Override
					public boolean canInsert(ItemStack stack) {
						return false;
					}
				},

				// Inventory
				new NoInventorySlot(entity.getInventory(), 2, 86, 31),
				//
				new NoInventorySlot(entity.getInventory(), 3, 86 + 18, 31),
				//
				new NoInventorySlot(entity.getInventory(), 4, 86 + 18 * 2, 31),
				//
				new NoInventorySlot(entity.getInventory(), 5, 86 + 18 * 3, 31),
				//
				new NoInventorySlot(entity.getInventory(), 6, 86, 49),
				//
				new NoInventorySlot(entity.getInventory(), 7, 86 + 18, 49),
				//
				new NoInventorySlot(entity.getInventory(), 8, 86 + 18 * 2, 49),
				//
				new NoInventorySlot(entity.getInventory(), 9, 86 + 18 * 3, 49) });
	}

	@Override
	public int getPlayerInventoryOffset() {
		return 8;
	}
}