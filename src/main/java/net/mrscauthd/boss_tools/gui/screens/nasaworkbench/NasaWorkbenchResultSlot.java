package net.mrscauthd.boss_tools.gui.screens.nasaworkbench;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.mrscauthd.boss_tools.machines.NASAWorkbenchBlock.CustomTileEntity;

public class NasaWorkbenchResultSlot extends Slot {

	private final CustomTileEntity tileEntity;

	public NasaWorkbenchResultSlot(Container inventory, int slotIndex, int xPos, int yPos, CustomTileEntity tileEntity) {
		super(inventory, slotIndex, xPos, yPos);
		this.tileEntity = tileEntity;
	}

	@Override
	public boolean mayPlace(ItemStack p_40231_) {
		return false;
	}

	public CustomTileEntity getTileEntity() {
		return this.tileEntity;
	}
}