package net.mrscauthd.astrocraft.gui.screens.coalgenerator;

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
import net.mrscauthd.astrocraft.machines.tile.CoalGeneratorBlockEntity;

public class CoalGeneratorGui {

	public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
		public GuiContainer create(int id, Inventory inv, FriendlyByteBuf extraData) {
			BlockPos pos = extraData.readBlockPos();
			CoalGeneratorBlockEntity blockEntity = (CoalGeneratorBlockEntity) inv.player.level.getBlockEntity(pos);
			return new GuiContainer(id, inv, blockEntity);
		}
	}

	public static class GuiContainer extends AbstractContainerMenu {
		private CoalGeneratorBlockEntity blockEntity;

		public GuiContainer(int id, Inventory inv, CoalGeneratorBlockEntity blockEntity) {
			super(ModInnet.COAL_GENERATOR_GUI.get(), id);
			this.blockEntity = blockEntity;

			IItemHandlerModifiable itemHandler = blockEntity.getItemHandler();
			this.addSlot(new SlotItemHandler(itemHandler, CoalGeneratorBlockEntity.SLOT_FUEL, 77, 31));

			ContainerHelper.addInventorySlots(this, inv, 8, 84, this::addSlot);
		}

		@Override
		public boolean stillValid(Player p_38874_) {
			return !this.getBlockEntity().isRemoved();
		}

		public CoalGeneratorBlockEntity getBlockEntity() {
			return this.blockEntity;
		}

		@Override
		public ItemStack quickMoveStack(Player playerIn, int index) {
			return ContainerHelper.transferStackInSlot(this, playerIn, index, this.getBlockEntity(), this::moveItemStackTo);
		}
	}
}
