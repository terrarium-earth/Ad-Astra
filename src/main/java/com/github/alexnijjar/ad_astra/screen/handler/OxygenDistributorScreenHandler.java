package com.github.alexnijjar.ad_astra.screen.handler;

import com.github.alexnijjar.ad_astra.blocks.machines.entity.OxygenDistributorBlockEntity;
import com.github.alexnijjar.ad_astra.registry.ModScreenHandlers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class OxygenDistributorScreenHandler extends AbstractMachineScreenHandler {

	public OxygenDistributorScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
		this(syncId, inventory, (OxygenDistributorBlockEntity) inventory.player.world.getBlockEntity(buf.readBlockPos()));
	}

	public OxygenDistributorScreenHandler(int syncId, PlayerInventory inventory, OxygenDistributorBlockEntity entity) {
		super(ModScreenHandlers.OXYGEN_DISTRIBUTOR_SCREEN_HANDLER, syncId, inventory, entity, new Slot[]{

				// Left Insert.
				new Slot(entity, 0, 17, 82),
				// Left Extract.
				new Slot(entity, 1, 17, 112) {
					@Override
					public boolean canInsert(ItemStack stack) {
						return false;
					}
				}});
	}

	@Override
	public int getPlayerInventoryOffset() {
		return 78;
	}
}