package net.mrscauthd.beyond_earth.gui.screens.oxygenloader;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.network.IContainerFactory;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.gui.helper.ContainerHelper;
import net.mrscauthd.beyond_earth.machines.tile.OxygenLoaderBlockEntity;
import net.mrscauthd.beyond_earth.machines.tile.OxygenMakingBlockEntity;

public class OxygenLoaderGui {

	public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
		public GuiContainer create(int id, Inventory inv, FriendlyByteBuf extraData) {
			BlockPos pos = extraData.readBlockPos();
			OxygenLoaderBlockEntity blockEntity = (OxygenLoaderBlockEntity) inv.player.level.getBlockEntity(pos);
			return new GuiContainer(id, inv, blockEntity);
		}
	}

	public static class GuiContainer extends AbstractContainerMenu {
		private OxygenLoaderBlockEntity blockEntity;

		public GuiContainer(int id, Inventory inv, OxygenLoaderBlockEntity blockEntity) {
			super(ModInit.OXYGEN_LOADER_GUI.get(), id);
			this.blockEntity = blockEntity;

			IItemHandlerModifiable internal = blockEntity.getItemHandler();
			this.addSlot(new SlotItemHandler(internal, OxygenMakingBlockEntity.SLOT_INPUT_SOURCE, 26, 22));
			this.addSlot(new SlotItemHandler(internal, OxygenLoaderBlockEntity.SLOT_OUTPUT_SINK, 92, 52));
			this.addSlot(new SlotItemHandler(internal, OxygenMakingBlockEntity.SLOT_INPUT_SINK, 26, 52));
			this.addSlot(new SlotItemHandler(internal, OxygenLoaderBlockEntity.SLOT_OUTPUT_SOURCE, 92, 22));

			ContainerHelper.addInventorySlots(this, inv, 8, 90, this::addSlot);
		}

		public OxygenLoaderBlockEntity getBlockEntity() {
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
