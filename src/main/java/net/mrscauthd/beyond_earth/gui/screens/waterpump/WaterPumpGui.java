package net.mrscauthd.beyond_earth.gui.screens.waterpump;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.IContainerFactory;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.gui.helper.ContainerHelper;
import net.mrscauthd.beyond_earth.machines.tile.WaterPumpBlockEntity;

public class WaterPumpGui {

	public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
		public GuiContainer create(int id, Inventory inv, FriendlyByteBuf extraData) {
			BlockPos pos = extraData.readBlockPos();
			WaterPumpBlockEntity blockEntity = (WaterPumpBlockEntity) inv.player.level.getBlockEntity(pos);
			return new GuiContainer(id, inv, blockEntity);
		}
	}

	public static class GuiContainer extends AbstractContainerMenu {
		private final WaterPumpBlockEntity blockEntity;

		public GuiContainer(int id, Inventory inv, WaterPumpBlockEntity blockEntity) {
			super(ModInit.WATER_PUMP_GUI.get(), id);
			this.blockEntity = blockEntity;

			ContainerHelper.addInventorySlots(this, inv, 8, 90, this::addSlot);
		}

		public WaterPumpBlockEntity getBlockEntity() {
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
