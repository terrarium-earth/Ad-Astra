package net.mrscauthd.boss_tools.gui.screens.waterpump;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.IContainerFactory;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.gui.helper.ContainerHelper;
import net.mrscauthd.boss_tools.machines.tile.WaterPumpTileEntity;

public class WaterPumpGui {

	public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
		public GuiContainer create(int id, Inventory inv, FriendlyByteBuf extraData) {
			BlockPos pos = extraData.readBlockPos();
			WaterPumpTileEntity tileEntity = (WaterPumpTileEntity) inv.player.level.getBlockEntity(pos);
			return new GuiContainer(id, inv, tileEntity);
		}
	}

	public static class GuiContainer extends AbstractContainerMenu {
		private final WaterPumpTileEntity tileEntity;

		public GuiContainer(int id, Inventory inv, WaterPumpTileEntity tileEntity) {
			super(ModInnet.WATER_PUMP_GUI.get(), id);
			this.tileEntity = tileEntity;

			ContainerHelper.addInventorySlots(this, inv, 8, 90, this::addSlot);
		}

		public WaterPumpTileEntity getTileEntity() {
			return this.tileEntity;
		}

		@Override
		public boolean stillValid(Player p_38874_) {
			return !this.getTileEntity().isRemoved();
		}

		@Override
		public ItemStack quickMoveStack(Player playerIn, int index) {
			return ContainerHelper.transferStackInSlot(this, playerIn, index, this.getTileEntity(), this::moveItemStackTo);
		}
	}
}
