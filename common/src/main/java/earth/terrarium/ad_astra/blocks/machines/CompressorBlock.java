package earth.terrarium.ad_astra.blocks.machines;

import earth.terrarium.ad_astra.blocks.machines.entity.CompressorBlockEntity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class CompressorBlock extends AbstractMachineBlock {

	public CompressorBlock(Settings settings) {
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
	public CompressorBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new CompressorBlockEntity(pos, state);
	}
}