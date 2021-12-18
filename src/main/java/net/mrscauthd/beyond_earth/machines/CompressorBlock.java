package net.mrscauthd.beyond_earth.machines;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.mrscauthd.beyond_earth.machines.tile.CompressorBlockEntity;

public class CompressorBlock extends AbstractMachineBlock<CompressorBlockEntity> {

	public CompressorBlock(BlockBehaviour.Properties properties) {
		super(properties);
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
	public CompressorBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CompressorBlockEntity(pos, state);
	}
}
