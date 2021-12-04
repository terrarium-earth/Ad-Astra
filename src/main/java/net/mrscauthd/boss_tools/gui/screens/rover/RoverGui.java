package net.mrscauthd.boss_tools.gui.screens.rover;

import net.minecraftforge.items.*;

import net.minecraftforge.fml.network.IContainerFactory;

import net.minecraft.network.PacketBuffer;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.RoverEntity;
import net.mrscauthd.boss_tools.events.Methodes;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.gui.helper.ContainerHelper;

public class RoverGui {

	public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
		public GuiContainer create(int id, PlayerInventory inv, PacketBuffer extraData) {
			return new GuiContainer(id, inv, extraData);
		}
	}

	public static class GuiContainer extends Container {
		Entity rover;

		public GuiContainer(int id, PlayerInventory inv, PacketBuffer extraData) {
			super(ModInnet.ROVER_GUI.get(), id);

			this.rover = inv.player.world.getEntityByID(extraData.readVarInt());

			IItemHandlerModifiable itemHandler = ((RoverEntity) rover).getItemHandler();
			this.addSlot(new SlotItemHandler(itemHandler, 0, 8, 63) {
				@Override
				public boolean isItemValid(ItemStack stack) {
					if (Methodes.tagCheck(FluidUtil2.findBucketFluid(stack.getItem()), ModInnet.FLUID_VEHICLE_FUEL_TAG)) {
						return true;
					}
					return false;
				}
			});

			this.addSlot(new SlotItemHandler(itemHandler, 1, 151, 16));
			this.addSlot(new SlotItemHandler(itemHandler, 2, 133, 16));
			this.addSlot(new SlotItemHandler(itemHandler, 3, 115, 16));
			this.addSlot(new SlotItemHandler(itemHandler, 4, 97, 16));

			this.addSlot(new SlotItemHandler(itemHandler, 5, 151, 34));
			this.addSlot(new SlotItemHandler(itemHandler, 6, 133, 34));
			this.addSlot(new SlotItemHandler(itemHandler, 7, 115, 34));
			this.addSlot(new SlotItemHandler(itemHandler, 8, 97, 34));

			ContainerHelper.addInventorySlots(this, inv, 8, 93, this::addSlot);
		}

		@Override
		public boolean canInteractWith(PlayerEntity player) {
			return !rover.removed;
		}

		@Override
		public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
			return ContainerHelper.transferStackInSlot(this, playerIn, index, 0, 9, this::mergeItemStack);
		}
	}
}