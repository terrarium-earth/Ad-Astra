package net.mrscauthd.boss_tools.machines;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Material;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gui.screens.solarpanel.SolarPanelGui;
import net.mrscauthd.boss_tools.machines.tile.GeneratorBlockEntity;

public class SolarPanelBlock {
	public static final int ENERGY_PER_TICK = 5;

	public static class CustomBlock extends AbstractMachineBlock<SolarPanelBlockEntity> {

		public CustomBlock() {
			super(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 1f).lightLevel(s -> 1).requiresCorrectToolForDrops());
		}

		@Override
		public void appendHoverText(ItemStack itemstack, BlockGetter level, List<Component> list, TooltipFlag flag) {
			super.appendHoverText(itemstack, level, list, flag);
			list.add(GaugeTextHelper.buildBlockTooltip(GaugeTextHelper.getGeneratingPerTickText(GaugeValueHelper.getEnergy(ENERGY_PER_TICK))));
		}

		@Override
		public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
			return true;
		}

		@Override
		public SolarPanelBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
			return new SolarPanelBlockEntity(pos, state);
		}

	}

	public static class SolarPanelBlockEntity extends GeneratorBlockEntity {

		public SolarPanelBlockEntity(BlockPos pos, BlockState state) {
			super(ModInnet.SOLAR_PANEL.get(), pos, state);
		}

		@Override
		public AbstractContainerMenu createMenu(int id, Inventory inventory) {
			return new SolarPanelGui.GuiContainer(id, inventory, this);
		}

		protected int getGenerationInTick() {
			return this.getMaxGeneration();
		}

		@Override
		public int getMaxGeneration() {
			return ENERGY_PER_TICK;
		}

		@Override
		protected boolean canGenerateEnergy() {
			Level level = this.getLevel();
			BlockPos blockPos = this.getBlockPos();

			return level.isDay() && level.getHeight(Heightmap.Types. MOTION_BLOCKING_NO_LEAVES, (int) Math.floor(blockPos.getX()), (int) Math.floor(blockPos.getZ())) <= Math.floor(blockPos.getY()) + 1;
		}

		@Override
		protected void generateEnergy() {
			this.generateEnergy(this.getGenerationInTick());
		}

		@Override
		protected List<Direction> getEjectDirections() {
			List<Direction> list = super.getEjectDirections();
			list.addAll(Arrays.stream(Direction.values()).filter(d -> d != Direction.UP).collect(Collectors.toList()));
			return list;
		}
	}
}
