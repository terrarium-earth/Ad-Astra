package com.github.alexnijjar.ad_astra.blocks.machines;

import com.github.alexnijjar.ad_astra.blocks.machines.entity.FuelRefineryBlockEntity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class FuelRefineryBlock extends AbstractMachineBlock {

	public FuelRefineryBlock(Settings settings) {
		super(settings);
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
	public FuelRefineryBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new FuelRefineryBlockEntity(pos, state);
	}
}