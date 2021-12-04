package net.mrscauthd.boss_tools.gui.screens.lander;

import net.minecraftforge.items.*;

import net.minecraftforge.fml.network.IContainerFactory;

import net.minecraft.network.PacketBuffer;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.LanderEntity;
import net.mrscauthd.boss_tools.gui.helper.ContainerHelper;

public class LanderGui {

	public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
		public GuiContainer create(int id, PlayerInventory inv, PacketBuffer extraData) {
			return new GuiContainer(id, inv, extraData);
		}
	}

	public static class GuiContainer extends Container {
		Entity lander;

		public GuiContainer(int id, PlayerInventory inv, PacketBuffer extraData) {
			super(ModInnet.LANDER_GUI.get(), id);

			this.lander = inv.player.world.getEntityByID(extraData.readVarInt());

			IItemHandlerModifiable itemHandler = ((LanderEntity) lander).getItemHandler();
			this.addSlot(new SlotItemHandler(itemHandler, 0, 28, 29) {
				@Override
				public boolean isItemValid(ItemStack stack) {
					return false;
				}
			});

			this.addSlot(new SlotItemHandler(itemHandler, 1, 78, 29) {
				@Override
				public boolean isItemValid(ItemStack stack) {
					return false;
				}
			});

			ContainerHelper.addInventorySlots(this, inv, 8, 84, this::addSlot);
		}

		@Override
		public boolean canInteractWith(PlayerEntity player) {
			return !lander.removed;
		}

		@Override
		public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
			return ContainerHelper.transferStackInSlot(this, playerIn, index, 0, 2, this::mergeItemStack);
		}
	}
}