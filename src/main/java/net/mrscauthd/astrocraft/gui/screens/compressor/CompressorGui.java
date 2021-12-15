package net.mrscauthd.astrocraft.gui.screens.compressor;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.network.IContainerFactory;
import net.mrscauthd.astrocraft.ModInnet;
import net.mrscauthd.astrocraft.gui.helper.ContainerHelper;
import net.mrscauthd.astrocraft.machines.tile.CompressorBlockEntity;
import net.mrscauthd.astrocraft.machines.tile.ItemStackToItemStackBlockEntity;

public class CompressorGui {

	public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
		public GuiContainer create(int id, Inventory inv, FriendlyByteBuf extraData) {
			BlockPos pos = extraData.readBlockPos();
			CompressorBlockEntity blockEntity = (CompressorBlockEntity) inv.player.level.getBlockEntity(pos);
			return new GuiContainer(id, inv, blockEntity);
		}
	}

	public static class GuiContainer extends AbstractContainerMenu {
		private CompressorBlockEntity blockEntity;

		public GuiContainer(int id, Inventory inv, CompressorBlockEntity blockEntity) {
			super(ModInnet.COMPRESSOR_GUI.get(), id);
			this.blockEntity = blockEntity;

			IItemHandlerModifiable itemHandler = blockEntity.getItemHandler();
			this.addSlot(new SlotItemHandler(itemHandler, CompressorBlockEntity.SLOT_INGREDIENT, 40, 37));

			this.addSlot(new SlotItemHandler(itemHandler, ItemStackToItemStackBlockEntity.SLOT_OUTPUT, 92, 36) {
				@Override
				public boolean mayPlace(@NotNull ItemStack stack) {
					return false;
				}
			});

			ContainerHelper.addInventorySlots(this, inv, 8, 86, this::addSlot);
		}
		public CompressorBlockEntity getBlockEntity() {
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
