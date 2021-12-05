package net.mrscauthd.boss_tools.gui.screens.nasaworkbench;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.IContainerFactory;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.crafting.WorkbenchingRecipe;
import net.mrscauthd.boss_tools.gui.helper.ContainerHelper;
import net.mrscauthd.boss_tools.gui.helper.GridPlacer;
import net.mrscauthd.boss_tools.gui.helper.RocketPartGridPlacer;
import net.mrscauthd.boss_tools.inventory.RocketPartsItemHandler;
import net.mrscauthd.boss_tools.machines.NASAWorkbenchBlock.CustomTileEntity;

public class NasaWorkbenchGui {

	public static class GuiContainerFactory implements IContainerFactory<GuiContainer> {
		public GuiContainer create(int id, Inventory inv, FriendlyByteBuf extraData) {
			BlockPos pos = extraData.readBlockPos();
			CustomTileEntity tileEntity = (CustomTileEntity) inv.player.level.getBlockEntity(pos);
			return new GuiContainer(id, inv, tileEntity);
		}
	}

	public static class GuiContainer extends AbstractContainerMenu {
		private CustomTileEntity tileEntity;
		private ResultContainer resultInventory;
		private Slot resultSlot;
		private int partSlotStart = 0;
		private int partSlotEnd = 0;

		public GuiContainer(int id, Inventory inv, CustomTileEntity tileEntity) {
			super(ModInnet.NASA_WORKBENCH_GUI.get(), id);
			this.tileEntity = tileEntity;

			this.resultInventory = new ResultContainer() {
				@Override
				public ItemStack removeItem(int p_40149_, int p_40150_) {
					ItemStack stack = super.removeItem(p_40149_, p_40150_);
					GuiContainer.this.onExtractResult(stack);
					return stack;
				}

				@Override
				public ItemStack removeItemNoUpdate(int p_40160_) {
					ItemStack stack = super.removeItemNoUpdate(p_40160_);
					GuiContainer.this.onExtractResult(stack);
					return stack;
				}
			};

			this.resultSlot = this.addSlot(new NasaWorkbenchResultSlot(this.resultInventory, 0, 133, 74, tileEntity));

			this.partSlotStart = this.slots.size();

			RocketPartsItemHandler partsItemHandler = tileEntity.getPartsItemHandler();
			GridPlacer placer = new GridPlacer();
			RocketPartGridPlacer.placeContainer(40, 18, 1, placer::placeBottom, ModInnet.ROCKET_PART_NOSE.get(), partsItemHandler, this::addSlot);
			RocketPartGridPlacer.placeContainer(31, 36, 2, placer::placeBottom, ModInnet.ROCKET_PART_BODY.get(), partsItemHandler, this::addSlot);
			RocketPartGridPlacer.placeContainer(31, 90, 1, placer::placeRight, ModInnet.ROCKET_PART_TANK.get(), partsItemHandler, this::addSlot);
			RocketPartGridPlacer.placeContainer(13, 90, 1, placer::placeBottom, ModInnet.ROCKET_PART_FIN_LEFT.get(), partsItemHandler, this::addSlot);
			RocketPartGridPlacer.placeContainer(67, 90, 1, placer::placeBottom, ModInnet.ROCKET_PART_FIN_RIGHT.get(), partsItemHandler, this::addSlot);
			RocketPartGridPlacer.placeContainer(40, 108, 1, placer::placeBottom, ModInnet.ROCKET_PART_ENGINE.get(), partsItemHandler, this::addSlot);

			this.partSlotEnd = this.slots.size();

			ContainerHelper.addInventorySlots(this, inv, 8, 142, this::addSlot);
		}

		private void onExtractResult(ItemStack stack) {
			CustomTileEntity tileEntity = this.getTileEntity();

			if (!stack.isEmpty() && tileEntity.cacheRecipes() != null) {
				tileEntity.consumeIngredient();
			}
		}

		@Override
		public void broadcastChanges() {
			super.broadcastChanges();

			WorkbenchingRecipe recipe = this.getTileEntity().cacheRecipes();

			this.resultSlot.set(recipe != null ? recipe.getOutput() : ItemStack.EMPTY);
		}

		@Override
		public boolean stillValid(Player p_38874_) {
			return !this.getTileEntity().isRemoved();
		}

		@Override
		public ItemStack quickMoveStack(Player playerIn, int slotNumber) {
			if (this.partSlotStart <= slotNumber && slotNumber < this.partSlotEnd) {
				return ContainerHelper.transferStackInSlot(this, playerIn, slotNumber, slotNumber - this.partSlotStart, this.getTileEntity(), this::moveItemStackTo);
			} else if (slotNumber == this.resultSlot.index) {
				Slot slot = this.getSlot(slotNumber);
				ItemStack prev = slot.getItem().copy();
				ItemStack itemStack = ContainerHelper.transferStackInSlot(this, playerIn, slotNumber, this.getTileEntity(), this::moveItemStackTo);

				if (slotNumber == this.resultSlot.index) {
					ItemStack next = slot.getItem().copy();

					if (!prev.isEmpty()) {
						int nextSize = next.isEmpty() ? 0 : next.getCount();

						if (nextSize > 0) {
							playerIn.drop(next, false);
							slot.set(ItemStack.EMPTY);
						}
					}
					this.onExtractResult(prev);
				}

				return itemStack;
			} else {
				return ContainerHelper.transferStackInSlot(this, playerIn, slotNumber, this.getTileEntity(), this::moveItemStackTo);
			}
		}

		public CustomTileEntity getTileEntity() {
			return this.tileEntity;
		}

		public ResultContainer getResultInventory() {
			return this.resultInventory;
		}

		public Slot getResultSlot() {
			return this.resultSlot;
		}
	}
}
