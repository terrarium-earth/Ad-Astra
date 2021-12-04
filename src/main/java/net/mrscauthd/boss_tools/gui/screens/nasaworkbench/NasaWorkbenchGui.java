package net.mrscauthd.boss_tools.gui.screens.nasaworkbench;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.IContainerFactory;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.crafting.WorkbenchingRecipe;
import net.mrscauthd.boss_tools.gui.helper.ContainerHelper;
import net.mrscauthd.boss_tools.gui.helper.GridPlacer;
import net.mrscauthd.boss_tools.gui.helper.RocketPartGridPlacer;
import net.mrscauthd.boss_tools.inventory.RocketPartsItemHandler;
import net.mrscauthd.boss_tools.machines.NASAWorkbenchBlock.CustomTileEntity;

public class NasaWorkbenchGui {

	public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
		public GuiContainer create(int id, PlayerInventory inv, PacketBuffer extraData) {
			BlockPos pos = extraData.readBlockPos();
			CustomTileEntity tileEntity = (CustomTileEntity) inv.player.world.getTileEntity(pos);
			return new GuiContainer(id, inv, tileEntity);
		}
	}

	public static class GuiContainer extends Container {
		private CustomTileEntity tileEntity;
		private CraftResultInventory resultInventory;
		private Slot resultSlot;
		private int partSlotStart = 0;
		private int partSlotEnd = 0;

		public GuiContainer(int id, PlayerInventory inv, CustomTileEntity tileEntity) {
			super(ModInnet.NASA_WORKBENCH_GUI.get(), id);
			this.tileEntity = tileEntity;

			this.resultInventory = new CraftResultInventory() {
				@Override
				public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
					ItemStack stack = super.decrStackSize(p_70298_1_, p_70298_2_);
					GuiContainer.this.onExtractResult(stack);
					return stack;
				}

				@Override
				public ItemStack removeStackFromSlot(int p_70304_1_) {
					ItemStack stack = super.removeStackFromSlot(p_70304_1_);
					GuiContainer.this.onExtractResult(stack);
					return stack;
				}
			};

			this.resultSlot = this.addSlot(new NasaWorkbenchResultSlot(this.resultInventory, 0, 133, 74, tileEntity));

			this.partSlotStart = this.inventorySlots.size();

			RocketPartsItemHandler partsItemHandler = tileEntity.getPartsItemHandler();
			GridPlacer placer = new GridPlacer();
			RocketPartGridPlacer.placeContainer(40, 18, 1, placer::placeBottom, ModInnet.ROCKET_PART_NOSE.get(), partsItemHandler, this::addSlot);
			RocketPartGridPlacer.placeContainer(31, 36, 2, placer::placeBottom, ModInnet.ROCKET_PART_BODY.get(), partsItemHandler, this::addSlot);
			RocketPartGridPlacer.placeContainer(31, 90, 1, placer::placeRight, ModInnet.ROCKET_PART_TANK.get(), partsItemHandler, this::addSlot);
			RocketPartGridPlacer.placeContainer(13, 90, 1, placer::placeBottom, ModInnet.ROCKET_PART_FIN_LEFT.get(), partsItemHandler, this::addSlot);
			RocketPartGridPlacer.placeContainer(67, 90, 1, placer::placeBottom, ModInnet.ROCKET_PART_FIN_RIGHT.get(), partsItemHandler, this::addSlot);
			RocketPartGridPlacer.placeContainer(40, 108, 1, placer::placeBottom, ModInnet.ROCKET_PART_ENGINE.get(), partsItemHandler, this::addSlot);

			this.partSlotEnd = this.inventorySlots.size();

			ContainerHelper.addInventorySlots(this, inv, 8, 142, this::addSlot);
		}

		private void onExtractResult(ItemStack stack) {
			CustomTileEntity tileEntity = this.getTileEntity();

			if (!stack.isEmpty() && tileEntity.cacheRecipes() != null) {
				tileEntity.consumeIngredient();
			}
		}

		@Override
		public void detectAndSendChanges() {
			super.detectAndSendChanges();

			WorkbenchingRecipe recipe = this.getTileEntity().cacheRecipes();

			this.resultSlot.putStack(recipe != null ? recipe.getOutput() : ItemStack.EMPTY);
		}

		@Override
		public boolean canInteractWith(PlayerEntity player) {
			return !this.getTileEntity().isRemoved();
		}

		@Override
		public ItemStack transferStackInSlot(PlayerEntity playerIn, int slotNumber) {
			if (this.partSlotStart <= slotNumber && slotNumber < this.partSlotEnd) {
				return ContainerHelper.transferStackInSlot(this, playerIn, slotNumber, slotNumber - this.partSlotStart, this.getTileEntity(), this::mergeItemStack);
			} else if (slotNumber == this.resultSlot.slotNumber) {
				Slot slot = this.getSlot(slotNumber);
				ItemStack prev = slot.getStack().copy();
				ItemStack itemStack = ContainerHelper.transferStackInSlot(this, playerIn, slotNumber, this.getTileEntity(), this::mergeItemStack);

				if (slotNumber == this.resultSlot.slotNumber) {
					ItemStack next = slot.getStack().copy();

					if (!prev.isEmpty()) {
						int nextSize = next.isEmpty() ? 0 : next.getCount();

						if (nextSize > 0) {
							playerIn.dropItem(next, false);
							slot.putStack(ItemStack.EMPTY);
						}
					}
					this.onExtractResult(prev);
				}

				return itemStack;
			} else {
				return ContainerHelper.transferStackInSlot(this, playerIn, slotNumber, this.getTileEntity(), this::mergeItemStack);
			}
		}

		public CustomTileEntity getTileEntity() {
			return this.tileEntity;
		}

		public CraftResultInventory getResultInventory() {
			return this.resultInventory;
		}

		public Slot getResultSlot() {
			return this.resultSlot;
		}
	}
}
