package earth.terrarium.ad_astra.common.item.vehicle;

import earth.terrarium.ad_astra.common.item.FluidContainingItem;
import earth.terrarium.ad_astra.common.item.HoldableOverHead;
import earth.terrarium.ad_astra.common.item.ModRenderedItem;
import earth.terrarium.ad_astra.common.registry.ModTags;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.BiPredicate;

public abstract class VehicleItem extends ModRenderedItem implements FluidContainingItem, HoldableOverHead {

    public VehicleItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag context) {
        long fuel = FluidHooks.getItemFluidManager(stack).getFluidInTank(0).getFluidAmount();
        tooltip.add(Component.translatable("tooltip.ad_astra.vehicle_fuel", FluidHooks.toMillibuckets(fuel), FluidHooks.toMillibuckets(this.getTankSize())).setStyle(Style.EMPTY.withColor(fuel > 0 ? ChatFormatting.GREEN : ChatFormatting.RED)));
    }

    @Override
    public BiPredicate<Integer, FluidHolder> getFilter() {
        return (i, f) -> f.getFluid().is(ModTags.FUELS);
    }
}
