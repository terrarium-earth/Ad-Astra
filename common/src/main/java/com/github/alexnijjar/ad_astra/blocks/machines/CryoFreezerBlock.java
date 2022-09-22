package com.github.alexnijjar.ad_astra.blocks.machines;

import com.github.alexnijjar.ad_astra.blocks.machines.entity.CryoFreezerBlockEntity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class CryoFreezerBlock extends AbstractMachineBlock {

	public CryoFreezerBlock(Settings settings) {
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
	public CryoFreezerBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new CryoFreezerBlockEntity(pos, state);
	}
}