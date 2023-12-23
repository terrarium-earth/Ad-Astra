package earth.terrarium.adastra.common.items.armor;

import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.common.items.armor.base.CustomDyeableArmorItem;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.adastra.common.tags.ModFluidTags;
import earth.terrarium.adastra.common.tags.ModItemTags;
import earth.terrarium.adastra.common.utils.FluidUtils;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidItem;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedItemFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.StreamSupport;

public class SpaceSuitItem extends CustomDyeableArmorItem implements BotariumFluidItem<WrappedItemFluidContainer> {
    private final long tankSize;

    public SpaceSuitItem(ArmorMaterial material, Type type, long buckets, Properties properties) {
        super(material, type, properties);
        this.tankSize = buckets;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        tooltipComponents.add(TooltipUtils.getFluidComponent(
            FluidUtils.getTank(stack),
            FluidHooks.buckets(tankSize),
            ModFluids.OXYGEN.get()));
    }

    @Override
    public WrappedItemFluidContainer getFluidContainer(ItemStack holder) {
        return new WrappedItemFluidContainer(
            holder,
            new SimpleFluidContainer(
                FluidHooks.buckets(tankSize),
                1,
                (t, f) -> f.is(ModFluidTags.OXYGEN)));
    }

    public static boolean hasFullSet(LivingEntity entity) {
        return hasFullSet(entity, ModItemTags.SPACE_SUITS);
    }

    public static boolean hasFullNetheriteSet(LivingEntity entity) {
        return hasFullSet(entity, ModItemTags.NETHERITE_SPACE_SUITS);
    }

    public static boolean hasFullJetSuitSet(LivingEntity entity) {
        return hasFullSet(entity, ModItemTags.JET_SUITS);
    }

    public static boolean hasFullSet(LivingEntity entity, TagKey<Item> spaceSuitTag) {
        return StreamSupport.stream(entity.getArmorSlots().spliterator(), false)
            .allMatch(stack -> stack.is(spaceSuitTag));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (level.isClientSide()) return;
        if (!(entity instanceof LivingEntity livingEntity)) return;
        if (livingEntity instanceof Player player && player.isCreative()) return;
        if (livingEntity.getItemBySlot(EquipmentSlot.CHEST) != stack) return;

        // Every 12 ticks = 10 minutes per 1,000 mB (1 bucket) oxygen
        if (level.getGameTime() % 12 == 0 && hasOxygen(entity)) {
            if (!OxygenApi.API.hasOxygen(entity)) {
                consumeOxygen(stack, 0.001f);
            }
            // Allow the entity to breathe in water
            if (entity.isEyeInFluid(FluidTags.WATER)) {
                consumeOxygen(stack, 0.001f);
                livingEntity.setAirSupply(Math.min(livingEntity.getMaxAirSupply(), livingEntity.getAirSupply() + 4 * 10));
            }
        }
    }

    public void consumeOxygen(ItemStack stack, float amount) {
        ItemStackHolder holder = new ItemStackHolder(stack);
        FluidHolder extracted = FluidUtils.extract(holder, FluidHooks.newFluidHolder(FluidUtils.getFluid(stack), FluidHooks.buckets(amount), null));
        if (holder.isDirty() || extracted.getFluidAmount() > 0) {
            stack.setTag(holder.getStack().getTag());
        }
    }

    public static long getOxygenAmount(Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) return 0;
        var stack = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
        if (!(stack.getItem() instanceof SpaceSuitItem suit)) return 0;
        return suit.getFluidContainer(stack).getFluids().get(0).getFluidAmount();
    }

    public static boolean hasOxygen(Entity entity) {
        return getOxygenAmount(entity) > FluidHooks.buckets(0.001);
    }
}
