package com.github.alexnijjar.ad_astra.screen.handler;

import com.github.alexnijjar.ad_astra.blocks.machines.entity.FluidMachineBlockEntity;
import com.github.alexnijjar.ad_astra.registry.ModScreenHandlers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class ConversionScreenHandler extends AbstractMachineScreenHandler {

	public ConversionScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
		this(syncId, inventory, (FluidMachineBlockEntity) inventory.player.world.getBlockEntity(buf.readBlockPos()));
	}

	public ConversionScreenHandler(int syncId, PlayerInventory inventory, FluidMachineBlockEntity entity) {
		super(ModScreenHandlers.CONVERSION_SCREEN_HANDLER, syncId, inventory, entity, new Slot[]{

				// Left Insert.
				new Slot(entity, 0, 12, 22),
				// Left Extract.
				new Slot(entity, 1, 12, 52) {
					@Override
					public boolean canInsert(ItemStack stack) {
						return false;
					}
				},
				// Right Insert.
				new Slot(entity, 2, 127, 22),
				// Right Extract.
				new Slot(entity, 3, 127, 52) {
					@Override
					public boolean canInsert(ItemStack stack) {
						return false;
					}
				}});
	}

	@Override
	public int getPlayerInventoryOffset() {
		return 18;
	}
}