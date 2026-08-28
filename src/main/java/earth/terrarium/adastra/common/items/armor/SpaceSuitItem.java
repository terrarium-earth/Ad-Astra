package earth.terrarium.adastra.common.items.armor;

import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.client.ClientPlatformUtils;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.items.armor.base.CustomDyeableArmorItem;
import earth.terrarium.adastra.common.registry.ModDataManagers;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.adastra.common.tags.ModFluidTags;
import earth.terrarium.adastra.common.tags.ModItemTags;
import earth.terrarium.adastra.common.utils.FluidUtils;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import earth.terrarium.common_storage_lib.context.ItemContext;
import earth.terrarium.common_storage_lib.context.impl.ModifyOnlyContext;
import earth.terrarium.common_storage_lib.fluid.FluidApi;
import earth.terrarium.common_storage_lib.fluid.impl.SimpleFluidStorage;
import earth.terrarium.common_storage_lib.fluid.util.FluidProvider;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.resources.fluid.util.FluidAmounts;
import earth.terrarium.common_storage_lib.storage.base.CommonStorage;
import net.minecraft.core.Holder;
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

import java.util.List;

public class SpaceSuitItem extends CustomDyeableArmorItem implements FluidProvider.Item {

    protected final long tankSize;

    public SpaceSuitItem(Holder<ArmorMaterial> material, Type type, long tankSize, Properties properties, int durabilityFactor) {
        super(material, type, properties, durabilityFactor);
        this.tankSize = tankSize;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(TooltipUtils.getFluidComponent(
            FluidUtils.getTank(stack),
            FluidAmounts.toPlatformAmount(tankSize),
            ModFluids.OXYGEN.get()));
        TooltipUtils.addDescriptionComponent(tooltipComponents, ConstantComponents.SPACE_SUIT_INFO);
    }

    @Override
    public CommonStorage<FluidResource> getFluids(ItemStack itemStack, ItemContext context) {
        return new SimpleFluidStorage(context, ModDataManagers.FLUID_CONTENTS.componentType(), 1, FluidAmounts.toPlatformAmount(tankSize))
            .filter(0, f -> f.is(ModFluidTags.OXYGEN));
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
        for (var stack : entity.getArmorSlots()) {
            if (!stack.is(spaceSuitTag)) return false;
        }
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (level.isClientSide()) return;
        if (!(entity instanceof LivingEntity livingEntity)) return;
        if (livingEntity instanceof Player player && (player.isCreative() || player.isSpectator())) return;
        if (livingEntity.getItemBySlot(EquipmentSlot.CHEST) != stack) return;
        livingEntity.setTicksFrozen(0);
        // Every 12 ticks = 10 minutes per 1,000 mB (1 bucket) oxygen
        if (livingEntity.tickCount % 12 == 0 && hasOxygen(entity)) {
            if (!OxygenApi.API.hasOxygen(entity)) {
                consumeOxygen(stack, 1);
            }
            // Allow the entity to breathe in water
            if (entity.isEyeInFluid(FluidTags.WATER)) {
                consumeOxygen(stack, 1);
                livingEntity.setAirSupply(Math.min(livingEntity.getMaxAirSupply(), livingEntity.getAirSupply() + 4 * 10));
            }
        }
    }

    public void consumeOxygen(ItemStack stack, long amount) {
        var container = new ModifyOnlyContext(stack).find(FluidApi.ITEM);
        if (container == null) return;
        long extracted = container.extract(container.getResource(0), FluidAmounts.toPlatformAmount(amount), false);
        if (extracted > 0) {
//            stack.setTag(holder.getStack().getTag());
        }
    }

    public static long getOxygenAmount(Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) return 0;
        var stack = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
        if (!(stack.getItem() instanceof SpaceSuitItem suit)) return 0;
        var fluidContainer = new ModifyOnlyContext(stack).find(FluidApi.ITEM);
        return fluidContainer.getAmount(0);
    }

    public static boolean hasOxygen(Entity entity) {
        return getOxygenAmount(entity) > FluidAmounts.toPlatformAmount(1);
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return FluidUtils.hasFluid(stack);
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        var fluidContainer = new ModifyOnlyContext(stack).find(FluidApi.ITEM);
        return (int) (((double) fluidContainer.getAmount(0) / fluidContainer.getLimit(0, FluidResource.BLANK)) * 13);
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return ClientPlatformUtils.getFluidColor(FluidUtils.getTank(stack));
    }
}
