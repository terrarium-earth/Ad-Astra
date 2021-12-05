package net.mrscauthd.boss_tools.gui.screens.rocket;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.items.*;
import net.minecraftforge.network.IContainerFactory;
import net.mrscauthd.boss_tools.ModInnet;

import net.minecraft.item.ItemStack;
import net.minecraft.inventory.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.mrscauthd.boss_tools.entity.RocketTier1Entity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.events.Methodes;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.gui.helper.ContainerHelper;

public class RocketGui {

	public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
		public GuiContainer create(int id, PlayerInventory inv, PacketBuffer extraData) {
			return new GuiContainer(id, inv, extraData);
		}
	}

	public static class GuiContainer extends Container {
		Entity rocket;

		public GuiContainer(int id, PlayerInventory inv, PacketBuffer extraData) {
			super(ModInnet.ROCKET_GUI.get(), id);

			this.rocket = inv.player.world.getEntityByID(extraData.readVarInt());

			IItemHandlerModifiable itemHandler = null;

			if (rocket instanceof RocketTier1Entity) {
				itemHandler = ((RocketTier1Entity) rocket).getItemHandler();
			} else if (rocket instanceof RocketTier2Entity) {
				itemHandler = ((RocketTier2Entity) rocket).getItemHandler();
			} else if (rocket instanceof RocketTier3Entity) {
				itemHandler = ((RocketTier3Entity) rocket).getItemHandler();
			}

			this.addSlot(new SlotItemHandler(itemHandler, 0, 46, 22) {
				@Override
				public boolean isItemValid(ItemStack stack) {
					if (Methodes.tagCheck(FluidUtil2.findBucketFluid(stack.getItem()), ModInnet.FLUID_VEHICLE_FUEL_TAG)) {
						return true;
					}
					return false;
				}
			});

			ContainerHelper.addInventorySlots(this, inv, 8, 84, this::addSlot);
		}

		@Override
		public boolean canInteractWith(PlayerEntity player) {
			return !rocket.removed;
		}

		@Override
		public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
			return ContainerHelper.transferStackInSlot(this, playerIn, index, 0, 1, this::mergeItemStack);
		}
	}
}
