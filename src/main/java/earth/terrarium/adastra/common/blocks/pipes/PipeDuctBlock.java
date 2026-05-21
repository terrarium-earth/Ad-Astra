package earth.terrarium.adastra.common.blocks.pipes;

import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import earth.terrarium.common_storage_lib.resources.fluid.util.FluidAmounts;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class PipeDuctBlock extends PipeBlock {

    public PipeDuctBlock(long transferRate, Type type, Properties properties) {
        super(transferRate, type, 0, properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.block();
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag isAdvanced) {
        if (type() == Type.ENERGY) {
            tooltip.add(Component.translatable("tooltip.ad_astra.energy_transfer_tick", transferRate()).withStyle(ChatFormatting.GOLD));
            TooltipUtils.addDescriptionComponent(tooltip, ConstantComponents.CABLE_DUCT);
        } else {
            tooltip.add(Component.translatable("tooltip.ad_astra.fluid_transfer_tick", FluidAmounts.toMillibuckets(transferRate())).withStyle(ChatFormatting.GOLD));
            TooltipUtils.addDescriptionComponent(tooltip, ConstantComponents.FLUID_DUCT_INFO);
        }
    }
}
