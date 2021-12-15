package net.mrscauthd.astrocraft.machines;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.mrscauthd.astrocraft.gauge.GaugeTextHelper;
import net.mrscauthd.astrocraft.gauge.GaugeValueHelper;
import net.mrscauthd.astrocraft.machines.tile.SolarPanelBlockEntity;

public class SolarPanelBlock extends AbstractMachineBlock<SolarPanelBlockEntity> {

	public SolarPanelBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack itemstack, BlockGetter level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);
		list.add(GaugeTextHelper.buildBlockTooltip(GaugeTextHelper.getGeneratingPerTickText(GaugeValueHelper.getEnergy(SolarPanelBlockEntity.ENERGY_PER_TICK))));
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
