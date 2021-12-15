package net.mrscauthd.astrocraft.gui.screens.solarpanel;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.IContainerFactory;
import net.mrscauthd.astrocraft.ModInnet;
import net.mrscauthd.astrocraft.gui.helper.ContainerHelper;
import net.mrscauthd.astrocraft.machines.tile.SolarPanelBlockEntity;

public class SolarPanelGui {

	public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
		public GuiContainer create(int id, Inventory inv, FriendlyByteBuf extraData) {
			BlockPos pos = extraData.readBlockPos();
			SolarPanelBlockEntity blockEntity = (SolarPanelBlockEntity) inv.player.level.getBlockEntity(pos);
			return new GuiContainer(id, inv, blockEntity);
		}
	}

	public static class GuiContainer extends AbstractContainerMenu {
		private SolarPanelBlockEntity blockEntity;

		public GuiContainer(int id, Inventory inv, SolarPanelBlockEntity blockEntity) {
			super(ModInnet.SOLAR_PANEL_GUI.get(), id);
			this.blockEntity = blockEntity;

			ContainerHelper.addInventorySlots(this, inv, 8, 84, this::addSlot);
		}

		public SolarPanelBlockEntity getBlockEntity() {
			return this.blockEntity;
		}

		@Override
		public boolean stillValid(Player p_38874_) {
			return !this.getBlockEntity().isRemoved();
		}

		@Override
		public ItemStack quickMoveStack(Player playerIn, int index) {
			return ContainerHelper.transferStackInSlot(this, playerIn, index, this.getBlockEntity(), this::moveItemStackTo);
		}
	}
}
