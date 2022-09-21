package com.github.alexnijjar.ad_astra.blocks.machines;

import com.github.alexnijjar.ad_astra.blocks.machines.entity.OxygenLoaderBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class OxygenLoaderBlock extends AbstractMachineBlock {

	public OxygenLoaderBlock(Settings settings) {
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
	public OxygenLoaderBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new OxygenLoaderBlockEntity(pos, state);
	}
}