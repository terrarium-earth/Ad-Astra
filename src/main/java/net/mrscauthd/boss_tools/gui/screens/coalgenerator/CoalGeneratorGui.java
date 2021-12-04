package net.mrscauthd.boss_tools.gui.screens.coalgenerator;

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
import net.mrscauthd.boss_tools.machines.CoalGeneratorBlock;
import net.mrscauthd.boss_tools.machines.CoalGeneratorBlock.CustomTileEntity;

public class CoalGeneratorGui {

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
			super(ModInnet.COAL_GENERATOR_GUI.get(), id);
			this.tileEntity = tileEntity;

			IItemHandlerModifiable itemHandler = tileEntity.getItemHandler();
			this.addSlot(new SlotItemHandler(itemHandler, CoalGeneratorBlock.SLOT_FUEL, 77, 31));

			ContainerHelper.addInventorySlots(this, inv, 8, 84, this::addSlot);
		}

		@Override
		public boolean canInteractWith(PlayerEntity player) {
			return !this.getTileEntity().isRemoved();
		}

		public CustomTileEntity getTileEntity() {
			return this.tileEntity;
		}

		@Override
		public ItemStack transferStackInSlot(PlayerEntity player, int index) {
			return ContainerHelper.transferStackInSlot(this, player, index, this.getTileEntity(), this::mergeItemStack);
		}
	}
}
