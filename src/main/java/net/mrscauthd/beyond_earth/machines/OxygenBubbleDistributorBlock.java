package net.mrscauthd.beyond_earth.machines;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.machines.tile.OxygenBubbleDistributorBlockEntity;

public class OxygenBubbleDistributorBlock extends AbstractMachineBlock<OxygenBubbleDistributorBlockEntity> {

	public OxygenBubbleDistributorBlock(BlockBehaviour.Properties properties) {
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
	public void appendHoverText(ItemStack itemstack, BlockGetter level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, list, flag);

		int min = OxygenBubbleDistributorBlockEntity.RANGE_MIN * 2 + 1;
		int max = OxygenBubbleDistributorBlockEntity.RANGE_MAX * 2 + 1;
		list.add(new TranslatableComponent("tooltip." + BeyondEarthMod.MODID + ".oxygen_bubble_distributor", min, max).setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
	}

	@Override
	public OxygenBubbleDistributorBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new OxygenBubbleDistributorBlockEntity(pos, state);
	}

}
