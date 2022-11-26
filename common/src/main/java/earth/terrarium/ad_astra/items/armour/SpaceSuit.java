package earth.terrarium.ad_astra.items.armour;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.config.SpaceSuitConfig;
import earth.terrarium.ad_astra.items.FluidContainingItem;
import earth.terrarium.ad_astra.registry.ModItems;
import earth.terrarium.ad_astra.registry.ModTags;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.StreamSupport;

public class SpaceSuit extends DyeableArmorItem implements FluidContainingItem, ModArmourItem {

    public SpaceSuit(ArmorMaterial material, EquipmentSlot slot, Item.Properties settings) {
        super(material, slot, settings);
    }

    public static boolean hasFullSet(LivingEntity entity) {
        return StreamSupport.stream(entity.getArmorSlots().spliterator(), false).allMatch(s -> s.getItem() instanceof SpaceSuit);
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
            if (chest.isDirty()) entity.setItemSlot(EquipmentSlot.CHEST, chest.getStack());
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
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return AdAstra.MOD_ID + ("overlay".equals(type) ? ":textures/entity/armour/space_suit_overlay.png" : ":textures/entity/armour/space_suit.png");
    }
}