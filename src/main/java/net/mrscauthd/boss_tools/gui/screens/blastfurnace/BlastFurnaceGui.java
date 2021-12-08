package net.mrscauthd.boss_tools.gui.screens.blastfurnace;

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
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.gui.helper.ContainerHelper;
import net.mrscauthd.boss_tools.machines.tile.BlastingFurnaceBlockEntity;
import net.mrscauthd.boss_tools.machines.tile.ItemStackToItemStackBlockEntity;

public class BlastFurnaceGui {

	public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
		public GuiContainer create(int id, Inventory inv, FriendlyByteBuf extraData) {
			BlockPos pos = extraData.readBlockPos();
			BlastingFurnaceBlockEntity blockEntity = (BlastingFurnaceBlockEntity) inv.player.level.getBlockEntity(pos);
			return new GuiContainer(id, inv, blockEntity);
		}
	}

	public static class GuiContainer extends AbstractContainerMenu {
		private BlastingFurnaceBlockEntity blockEntity;

		public GuiContainer(int id, Inventory inv, BlastingFurnaceBlockEntity blockEntity) {
			super(ModInnet.BLAST_FURNACE_GUI.get(), id);
			this.blockEntity = blockEntity;

			IItemHandlerModifiable itemHandler = blockEntity.getItemHandler();
			this.addSlot(new SlotItemHandler(itemHandler, ItemStackToItemStackBlockEntity.SLOT_INGREDIENT, 53, 19));
			this.addSlot(new SlotItemHandler(itemHandler, BlastingFurnaceBlockEntity.SLOT_FUEL, 53, 56));

			this.addSlot(new SlotItemHandler(itemHandler, ItemStackToItemStackBlockEntity.SLOT_OUTPUT, 104, 38) {
				@Override
				public boolean mayPlace(@NotNull ItemStack stack) {
					return false;
				}
			});

			ContainerHelper.addInventorySlots(this, inv, 8, 87, this::addSlot);
		}

		public BlastingFurnaceBlockEntity getBlockEntity() {
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
