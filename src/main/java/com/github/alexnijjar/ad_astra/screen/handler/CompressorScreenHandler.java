package com.github.alexnijjar.ad_astra.screen.handler;

import com.github.alexnijjar.ad_astra.blocks.machines.entity.CompressorBlockEntity;
import com.github.alexnijjar.ad_astra.registry.ModScreenHandlers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class CompressorScreenHandler extends AbstractMachineScreenHandler {

	public CompressorScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
		this(syncId, inventory, (CompressorBlockEntity) inventory.player.world.getBlockEntity(buf.readBlockPos()));

	}

	@Override
	public int getPlayerInventoryOffset() {
		return 30;
	}

	public CompressorScreenHandler(int syncId, PlayerInventory inventory, CompressorBlockEntity entity) {
		super(ModScreenHandlers.COMPRESSOR_SCREEN_HANDLER, syncId, inventory, entity, new Slot[]{new Slot(entity, 0, 53, 56), new Slot(entity, 1, 100, 56) {
			@Override
			public boolean canInsert(ItemStack stack) {
				return false;
			}
		}});
	}
}