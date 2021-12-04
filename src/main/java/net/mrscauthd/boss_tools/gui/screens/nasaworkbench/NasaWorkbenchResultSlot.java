package net.mrscauthd.boss_tools.gui.screens.nasaworkbench;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.mrscauthd.boss_tools.machines.NASAWorkbenchBlock.CustomTileEntity;

public class NasaWorkbenchResultSlot extends Slot {

	private final CustomTileEntity tileEntity;

	public NasaWorkbenchResultSlot(IInventory inventory, int slotIndex, int xPos, int yPos, CustomTileEntity tileEntity) {
		super(inventory, slotIndex, xPos, yPos);
		this.tileEntity = tileEntity;
	}

	public boolean isItemValid(ItemStack p_75214_1_) {
		return false;
	}

	public CustomTileEntity getTileEntity() {
		return this.tileEntity;
	}
}