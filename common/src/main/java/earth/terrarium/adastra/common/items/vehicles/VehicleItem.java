package earth.terrarium.adastra.common.items.vehicles;

import earth.terrarium.adastra.common.items.rendered.RenderedItem;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.adastra.common.tags.ModFluidTags;
import earth.terrarium.adastra.common.utils.FluidUtils;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidItem;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedItemFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Supplier;

public abstract class VehicleItem extends RenderedItem implements BotariumFluidItem<WrappedItemFluidContainer> {
    private final Supplier<EntityType<?>> type;

    public VehicleItem(Supplier<EntityType<?>> type, Properties properties) {
        super(properties);
        this.type = type;
    }

    public EntityType<?> type() {
        return type.get();
    }

    @Override
    public WrappedItemFluidContainer getFluidContainer(ItemStack holder) {
        return new WrappedItemFluidContainer(
            holder,
            new SimpleFluidContainer(
                FluidHooks.buckets(3),
                1,
                (t, f) -> f.is(ModFluidTags.FUEL)));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        tooltipComponents.add(TooltipUtils.getFluidComponent(
            FluidUtils.getTank(stack),
            FluidUtils.getTankCapacity(stack),
            ModFluids.FUEL.get()));
    }
}
