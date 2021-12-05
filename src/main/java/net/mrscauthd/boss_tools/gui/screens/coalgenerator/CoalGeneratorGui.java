package net.mrscauthd.boss_tools.gui.screens.coalgenerator;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.network.IContainerFactory;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.gui.helper.ContainerHelper;
import net.mrscauthd.boss_tools.machines.CoalGeneratorBlock;
import net.mrscauthd.boss_tools.machines.CoalGeneratorBlock.CustomTileEntity;

public class CoalGeneratorGui {

	public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
		public GuiContainer create(int id, Inventory inv, FriendlyByteBuf extraData) {
			BlockPos pos = extraData.readBlockPos();
			CustomTileEntity tileEntity = (CustomTileEntity) inv.player.level.getBlockEntity(pos);
			return new GuiContainer(id, inv, tileEntity);
		}
	}

	public static class GuiContainer extends AbstractContainerMenu {
		private CustomTileEntity tileEntity;

		public GuiContainer(int id, Inventory inv, CustomTileEntity tileEntity) {
			super(ModInnet.COAL_GENERATOR_GUI.get(), id);
			this.tileEntity = tileEntity;

			IItemHandlerModifiable itemHandler = tileEntity.getItemHandler();
			this.addSlot(new SlotItemHandler(itemHandler, CoalGeneratorBlock.SLOT_FUEL, 77, 31));

			ContainerHelper.addInventorySlots(this, inv, 8, 84, this::addSlot);
		}

		@Override
		public boolean stillValid(Player p_38874_) {
			return !this.getTileEntity().isRemoved();
		}

		public CustomTileEntity getTileEntity() {
			return this.tileEntity;
		}

		@Override
		public ItemStack quickMoveStack(Player playerIn, int index) {
			return ContainerHelper.transferStackInSlot(this, playerIn, index, this.getTileEntity(), this::moveItemStackTo);
		}
	}
}
