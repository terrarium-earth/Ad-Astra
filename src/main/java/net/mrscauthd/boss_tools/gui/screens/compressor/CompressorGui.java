package net.mrscauthd.boss_tools.gui.screens.compressor;

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
import net.mrscauthd.boss_tools.machines.CompressorBlock.CustomTileEntity;
import net.mrscauthd.boss_tools.machines.tile.ItemStackToItemStackTileEntity;
import org.jetbrains.annotations.NotNull;

public class CompressorGui {

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
			super(ModInnet.COMPRESSOR_GUI.get(), id);
			this.tileEntity = tileEntity;

			IItemHandlerModifiable itemHandler = tileEntity.getItemHandler();
			this.addSlot(new SlotItemHandler(itemHandler, CustomTileEntity.SLOT_INGREDIENT, 40, 37));

			this.addSlot(new SlotItemHandler(itemHandler, ItemStackToItemStackTileEntity.SLOT_OUTPUT, 92, 36) {
				@Override
				public boolean mayPlace(@NotNull ItemStack stack) {
					return false;
				}
			});

			ContainerHelper.addInventorySlots(this, inv, 8, 86, this::addSlot);
		}
		public CustomTileEntity getTileEntity() {
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
