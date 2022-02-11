package net.mrscauthd.beyond_earth.machines.tile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.gui.screens.solarpanel.SolarPanelGui;

public class SolarPanelBlockEntity extends GeneratorBlockEntity {

	public static final int ENERGY_PER_TICK = 5;

	public SolarPanelBlockEntity(BlockPos pos, BlockState state) {
		super(ModInit.SOLAR_PANEL.get(), pos, state);
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

		return level.isDay() && level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) Math.floor(blockPos.getX()), (int) Math.floor(blockPos.getZ())) <= Math.floor(blockPos.getY()) + 1;
	}

	@Override
	protected void generateEnergy() {
		this.generateEnergy(this.getGenerationInTick());
	}

	@Override
	protected List<Direction> getEjectDirections() {
		List<Direction> list = super.getEjectDirections();
		list.addAll(Arrays.stream(Direction.values()).filter(d -> d != Direction.UP).toList());
		return list;
	}
}