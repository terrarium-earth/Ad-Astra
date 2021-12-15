package net.mrscauthd.astrocraft.machines;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.mrscauthd.astrocraft.machines.tile.OxygenLoaderBlockEntity;

public class OxygenLoaderBlock extends AbstractMachineBlock<OxygenLoaderBlockEntity> {

	public OxygenLoaderBlock(BlockBehaviour.Properties properties) {
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
	public OxygenLoaderBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new OxygenLoaderBlockEntity(pos, state);
	}

}
