package net.mrscauthd.boss_tools.gui.screens.oxygenloader;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.gui.helper.ContainerHelper;
import net.mrscauthd.boss_tools.machines.OxygenLoaderBlock;
import net.mrscauthd.boss_tools.machines.OxygenLoaderBlock.CustomTileEntity;
import net.mrscauthd.boss_tools.machines.tile.OxygenMakingTileEntity;

public class OxygenLoaderGui {

	public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
		public GuiContainer create(int id, PlayerInventory inv, PacketBuffer extraData) {
			BlockPos pos = extraData.readBlockPos();
			CustomTileEntity tileEntity = (CustomTileEntity) inv.player.world.getTileEntity(pos);
			return new GuiContainer(id, inv, tileEntity);
		}
	}

	public static class GuiContainer extends Container {
		private CustomTileEntity tileEntity;

		public GuiContainer(int id, PlayerInventory inv, CustomTileEntity tileEntity) {
			super(ModInnet.OXYGEN_LOADER_GUI.get(), id);
			this.tileEntity = tileEntity;

			IItemHandlerModifiable internal = tileEntity.getItemHandler();
			this.addSlot(new SlotItemHandler(internal, OxygenMakingTileEntity.SLOT_INPUT_SOURCE, 26, 22));
			this.addSlot(new SlotItemHandler(internal, OxygenLoaderBlock.SLOT_OUTPUT_SINK, 92, 52));
			this.addSlot(new SlotItemHandler(internal, OxygenMakingTileEntity.SLOT_INPUT_SINK, 26, 52));
			this.addSlot(new SlotItemHandler(internal, OxygenLoaderBlock.SLOT_OUTPUT_SOURCE, 92, 22));

			ContainerHelper.addInventorySlots(this, inv, 8, 90, this::addSlot);
		}

		public CustomTileEntity getTileEntity() {
			return this.tileEntity;
		}

		@Override
		public boolean canInteractWith(PlayerEntity player) {
			return !this.getTileEntity().isRemoved();
		}

		@Override
		public ItemStack transferStackInSlot(PlayerEntity player, int index) {
			return ContainerHelper.transferStackInSlot(this, player, index, this.getTileEntity(), this::mergeItemStack);
		}
	}
}
