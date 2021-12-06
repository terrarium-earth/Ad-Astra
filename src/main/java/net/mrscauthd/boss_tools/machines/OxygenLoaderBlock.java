package net.mrscauthd.boss_tools.machines;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.capability.OxygenUtil;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeType;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.OxygenMakingRecipeAbstract;
import net.mrscauthd.boss_tools.gui.screens.oxygenloader.OxygenLoaderGui;
import net.mrscauthd.boss_tools.machines.tile.NamedComponentRegistry;
import net.mrscauthd.boss_tools.machines.tile.OxygenMakingTileEntity;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemEnergyCommon;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemRegistry;

public class OxygenLoaderBlock {
	public static final int ENERGY_PER_TICK = 1;
	public static final int SLOT_OUTPUT_SINK = 2;
	public static final int SLOT_OUTPUT_SOURCE = 3;

	public static class CustomBlock extends AbstractMachineBlock<CustomTileEntity> {

		public CustomBlock() {
			super(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 1f).lightLevel(s -> 0).requiresCorrectToolForDrops());
		}
		
		@Override
		protected boolean useLit() {
			return true;
		}
		
		@Override
		protected boolean useFacing() {
			return true;
		}

		@Override
		public CustomTileEntity newBlockEntity(BlockPos pos, BlockState state) {
			return new CustomTileEntity(pos, state);
		}

	}

	public static class CustomTileEntity extends OxygenMakingTileEntity {

		public CustomTileEntity(BlockPos pos, BlockState state) {
			super(ModInnet.OXYGEN_LOADER.get(), pos, state);
		}

		@Override
		protected void drainSources() {
			super.drainSources();
			OxygenUtil.drainSource(this.getItemHandler(), this.getOutputSourceSlot(), this.getOutputTank(), this.getTransferPerTick());
		}

		@Override
		protected void fillSinks() {
			super.fillSinks();
			OxygenUtil.fillSink(this.getItemHandler(), this.getOutputSinkSlot(), this.getOutputTank(), this.getTransferPerTick());
		}

		@Override
		protected boolean onCanPlaceItemThroughFace(int index, ItemStack stack, Direction direction) {
			if (index == this.getOutputSourceSlot()) {
				return OxygenUtil.canExtract(stack);
			} else if (index == this.getOutputSinkSlot()) {
				return OxygenUtil.canReceive(stack);
			}

			return super.onCanPlaceItemThroughFace(index, stack, direction);
		}

		@Override
		public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
			if (index == this.getOutputSourceSlot()) {
				return !OxygenUtil.canExtract(stack);
			} else if (index == this.getOutputSinkSlot()) {
				return !OxygenUtil.canReceive(stack);
			}

			return super.canTakeItemThroughFace(index, stack, direction);
		}

		@Override
		protected void getSlotsForFace(Direction direction, List<Integer> slots) {
			super.getSlotsForFace(direction, slots);
			slots.add(this.getOutputSourceSlot());
			slots.add(this.getOutputSinkSlot());
		}

		@Override
		public AbstractContainerMenu createMenu(int id, Inventory inventory) {
			return new OxygenLoaderGui.GuiContainer(id, inventory, this);
		}

		@Override
		protected void createEnergyStorages(NamedComponentRegistry<IEnergyStorage> registry) {
			super.createEnergyStorages(registry);
			registry.put(this.createEnergyStorageCommon());
		}

		@Override
		protected BooleanProperty getBlockActivatedProperty() {
			return CustomBlock.LIT;
		}

		@Override
		protected void createPowerSystems(PowerSystemRegistry map) {
			super.createPowerSystems(map);

			map.put(new PowerSystemEnergyCommon(this) {
				@Override
				public int getBasePowerForOperation() {
					return CustomTileEntity.this.getBasePowerForOperation();
				}
			});
		}

		public int getBasePowerForOperation() {
			return ENERGY_PER_TICK;
		}

		@Override
		public BossToolsRecipeType<? extends OxygenMakingRecipeAbstract> getRecipeType() {
			return BossToolsRecipeTypes.OXYGENLOADER;
		}

		@Override
		protected int getInitialInventorySize() {
			return super.getInitialInventorySize() + 2;
		}

		public boolean isSourceSlot(int slot) {
			return slot == this.getOutputSourceSlot() || super.isSourceSlot(slot);
		}

		public boolean isSinkSlot(int slot) {
			return slot == this.getOutputSinkSlot() || super.isSinkSlot(slot);
		}

		public IOxygenStorage slotToOxygenTank(int slot) {
			if (slot == this.getOutputSourceSlot() || slot == this.getOutputSinkSlot()) {
				return this.getOutputTank();
			} else {
				return super.slotToOxygenTank(slot);
			}
		}

		public ResourceLocation slotToTankName(int slot) {
			if (slot == this.getOutputSourceSlot() || slot == this.getOutputSinkSlot()) {
				return this.getOutputTankName();
			} else {
				return super.slotToTankName(slot);
			}
		}

		public int getOutputSourceSlot() {
			return SLOT_OUTPUT_SOURCE;
		}

		public int getOutputSinkSlot() {
			return SLOT_OUTPUT_SINK;
		}
	}
}
