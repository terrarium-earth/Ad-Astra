package earth.terrarium.ad_astra.items.vehicles;

import earth.terrarium.ad_astra.items.FluidContainingItem;
import earth.terrarium.ad_astra.items.HoldableOverHead;
import earth.terrarium.ad_astra.items.ModRenderedItem;
import earth.terrarium.ad_astra.registry.ModTags;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.BiPredicate;

public abstract class VehicleItem extends ModRenderedItem implements FluidContainingItem, HoldableOverHead {

    public VehicleItem(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag context) {
        long fuel = FluidHooks.toMillibuckets(FluidHooks.getItemFluidManager(stack).getFluidInTank(0).getFluidAmount());
        tooltip.add(Component.translatable("tooltip.ad_astra.vehicle_fuel", FluidHooks.toMillibuckets(fuel), FluidHooks.toMillibuckets(this.getTankSize())).setStyle(Style.EMPTY.withColor(fuel > 0 ? ChatFormatting.GREEN : ChatFormatting.RED)));
    }

    @Override
    public BiPredicate<Integer, FluidHolder> getFilter() {
        return (i, f) -> f.getFluid().is(ModTags.FUELS);
    }
}
