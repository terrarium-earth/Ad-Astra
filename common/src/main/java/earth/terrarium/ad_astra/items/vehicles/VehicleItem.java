package earth.terrarium.ad_astra.items.vehicles;

import earth.terrarium.ad_astra.items.FluidContainingItem;
import earth.terrarium.ad_astra.items.HoldableOverHead;
import earth.terrarium.ad_astra.items.ModRenderedItem;
import earth.terrarium.ad_astra.registry.ModTags;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.BiPredicate;

public abstract class VehicleItem extends ModRenderedItem implements FluidContainingItem, HoldableOverHead {

    public VehicleItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        long fuel = FluidHooks.toMillibuckets(FluidHooks.getItemFluidManager(stack).getFluidInTank(0).getFluidAmount());
        tooltip.add(Text.translatable("tooltip.ad_astra.vehicle_fuel", FluidHooks.toMillibuckets(fuel), FluidHooks.toMillibuckets(this.getTankSize())).setStyle(Style.EMPTY.withColor(fuel > 0 ? Formatting.GREEN : Formatting.RED)));
    }

    @Override
    public BiPredicate<Integer, FluidHolder> getFilter() {
        return (i, f) -> f.getFluid().isIn(ModTags.FUELS);
    }
}
