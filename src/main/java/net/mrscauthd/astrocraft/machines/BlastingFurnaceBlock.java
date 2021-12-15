package net.mrscauthd.astrocraft.machines;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.mrscauthd.astrocraft.machines.tile.BlastingFurnaceBlockEntity;

public class BlastingFurnaceBlock extends AbstractMachineBlock<BlastingFurnaceBlockEntity> {

	public BlastingFurnaceBlock(BlockBehaviour.Properties properties) {
		super(properties);
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
	public BlastingFurnaceBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new BlastingFurnaceBlockEntity(pos, state);
	}

}
