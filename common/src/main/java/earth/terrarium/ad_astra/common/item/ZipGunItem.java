package earth.terrarium.ad_astra.common.item;

import earth.terrarium.ad_astra.common.registry.ModTags;
import earth.terrarium.ad_astra.common.util.FluidUtils;
import earth.terrarium.ad_astra.common.util.LangUtils;
import earth.terrarium.botarium.common.fluid.base.FluidAttachment;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedItemFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ZipGunItem extends Item implements FluidAttachment.Item {
    private final double propelForce;
    private final long tankSize;

    public ZipGunItem(double propelForce, long tankSize, Properties properties) {
        super(properties);
        this.propelForce = propelForce;
        this.tankSize = tankSize;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        ItemStackHolder holder = new ItemStackHolder(stack);
        FluidHolder extracted = FluidUtils.extract(holder, FluidHooks.newFluidHolder(FluidUtils.getFluid(holder.getStack()), FluidHooks.buckets(0.002f), null));
        if (!holder.isDirty()) return InteractionResultHolder.pass(stack);
        stack = holder.getStack();
        if (extracted.getFluidAmount() > 0) {
            Vec3 propelRot = player.getLookAngle().scale(propelForce / 20);
            player.addDeltaMovement(propelRot);
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        long fuel = FluidHooks.toMillibuckets(FluidHooks.getItemFluidManager(stack).getFluidInTank(0).getFluidAmount());
        tooltipComponents.add(Component.translatable(LangUtils.FLUID_TANK, fuel, FluidHooks.toMillibuckets(tankSize)).setStyle(Style.EMPTY.withColor(fuel > 0 ? ChatFormatting.GREEN : ChatFormatting.RED)));
    }

    @Override
    public WrappedItemFluidContainer getFluidContainer(ItemStack holder) {
        return new WrappedItemFluidContainer(holder, new SimpleFluidContainer(tankSize, 1, (i, f) -> f.getFluid().is(ModTags.Fluids.OXYGEN) || f.getFluid().is(ModTags.Fluids.HYDROGEN)));
    }
}
