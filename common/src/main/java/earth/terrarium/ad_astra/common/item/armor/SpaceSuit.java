package earth.terrarium.ad_astra.common.item.armor;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.config.SpaceSuitConfig;
import earth.terrarium.ad_astra.common.item.FluidContainingItem;
import earth.terrarium.ad_astra.common.registry.ModItems;
import earth.terrarium.ad_astra.common.registry.ModTags;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.Range;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiPredicate;

public class SpaceSuit extends DyeableArmorItem implements FluidContainingItem, ModArmourItem {

    public SpaceSuit(ArmorMaterial material, ArmorItem.Type type, net.minecraft.world.item.Item.Properties properties) {
        super(material, type, properties);
    }

    public static boolean hasFullSet(LivingEntity entity) {
        int slotCount = 0;
        int armorCount = 0;
        for (ItemStack stack : entity.getArmorSlots()) {
            slotCount++;
            if (stack.getItem() instanceof SpaceSuit) {
                armorCount++;
            }
        }
        return slotCount > 0 && armorCount == slotCount;
    }

    /**
     * Checks if the entity is wearing a space suit and if that space suit has oxygen.
     *
     * @param entity The entity wearing the space suit
     * @return Whether the entity has oxygen or not
     */
    public static boolean hasOxygenatedSpaceSuit(LivingEntity entity) {
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        if (chest.getItem() instanceof SpaceSuit suit) {
            return suit.getFluidAmount(chest) > 0;
        }

        return false;
    }

    public static void consumeSpaceSuitOxygen(LivingEntity entity, long amount) {
        ItemStackHolder chest = new ItemStackHolder(entity.getItemBySlot(EquipmentSlot.CHEST));
        if (chest.getStack().getItem() instanceof SpaceSuit suit) {
            suit.extract(chest, FluidHooks.newFluidHolder(suit.getFluid(chest.getStack()), amount, null));
            var wasSilent = entity.isSilent();
            if (!wasSilent) entity.setSilent(true);
            if (chest.isDirty()) entity.setItemSlot(EquipmentSlot.CHEST, chest.getStack());
            if (!wasSilent) entity.setSilent(false);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag context) {
        if (stack.is(ModItems.SPACE_SUIT.get()) || stack.is(ModItems.NETHERITE_SPACE_SUIT.get()) || stack.is(ModItems.JET_SUIT.get())) {
            long oxygen = FluidHooks.toMillibuckets(FluidHooks.getItemFluidManager(stack).getFluidInTank(0).getFluidAmount());
            tooltip.add(Component.translatable("tooltip.ad_astra.space_suit", oxygen, FluidHooks.toMillibuckets(getTankSize())).setStyle(Style.EMPTY.withColor(oxygen > 0 ? ChatFormatting.GREEN : ChatFormatting.RED)));
        }
    }

    @Override
    public long getTankSize() {
        return SpaceSuitConfig.spaceSuitTankSize;
    }

    @Override
    public BiPredicate<Integer, FluidHolder> getFilter() {
        return (i, f) -> f.getFluid().is(ModTags.OXYGEN);
    }

    public Range<Integer> getTemperatureThreshold() {
        return Range.between(-300, 60);
    }

    @Override
    public int getColor(@NotNull ItemStack stack) {
        int colour = super.getColor(stack);
        return colour == 10511680 ? 0xFFFFFF : colour;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return new ResourceLocation(AdAstra.MOD_ID, "textures/entity/armour/space_suit.png").toString();
    }
}