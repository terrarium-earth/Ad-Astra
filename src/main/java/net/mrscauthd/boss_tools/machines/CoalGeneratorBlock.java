package net.mrscauthd.boss_tools.machines;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gui.screens.coalgenerator.CoalGeneratorGui;
import net.mrscauthd.boss_tools.machines.tile.GeneratorTileEntity;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemFuelGeneratingRecipe;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemRegistry;

public class CoalGeneratorBlock {
	public static final int SLOT_FUEL = 0;
	public static final int ENERGY_PER_TICK = 2;

	public static class CustomBlock extends AbstractMachineBlock<CustomTileEntity> {

		public CustomBlock() {
			super(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 1f).lightLevel(s -> 0).requiresCorrectToolForDrops());
		}

		@Override
		public void appendHoverText(ItemStack itemstack, BlockGetter level, List<Component> list, TooltipFlag flag) {
			super.appendHoverText(itemstack, level, list, flag);
			list.add(GaugeTextHelper.buildBlockTooltip(GaugeTextHelper.getGeneratingPerTickText(GaugeValueHelper.getEnergy(ENERGY_PER_TICK))));
		}
		
		@Override
		protected boolean useFacing() {
			return true;
		}
		
		@Override
		protected boolean useLit() {
			return true;
		}

		@Override
		public CustomTileEntity newBlockEntity(BlockPos pos, BlockState state) {
			return new CustomTileEntity(pos, state);
		}

	}

	public static class CustomTileEntity extends GeneratorTileEntity {

		private PowerSystemFuelGeneratingRecipe powerSystemGenerating;

		public CustomTileEntity(BlockPos pos, BlockState state) {
			super(ModInnet.COAL_GENERATOR.get(), pos, state);
		}

		@Override
		public AbstractContainerMenu createMenu(int id, Inventory inventory) {
			return new CoalGeneratorGui.GuiContainer(id, inventory, this);
		}

		@Override
		public int getMaxGeneration() {
			return ENERGY_PER_TICK;
		}

		protected int getGenerationInTick() {
			return this.getMaxGeneration();
		}

		@Override
		protected boolean canGenerateEnergy() {
			return true;
		}

		@Override
		protected void generateEnergy() {
			this.generateEnergy(this.getGenerationInTick());
		}

		@Override
		protected List<Direction> getEjectDirections() {
			List<Direction> list = super.getEjectDirections();
			list.add(Direction.UP);
			list.add(Direction.DOWN);
			return list;
		}

		@Override
		protected void createPowerSystems(PowerSystemRegistry map) {
			super.createPowerSystems(map);
			map.put(this.powerSystemGenerating = new PowerSystemFuelGeneratingRecipe(this, this.getFuelSlot()));
		}

		public PowerSystemFuelGeneratingRecipe getPowerSystemGenerating() {
			return this.powerSystemGenerating;
		}

		public int getFuelSlot() {
			return SLOT_FUEL;
		}
	}
}
