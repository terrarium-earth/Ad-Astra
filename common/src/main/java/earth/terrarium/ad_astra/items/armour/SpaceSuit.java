package earth.terrarium.ad_astra.items.armour;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.items.FluidContainingItem;
import earth.terrarium.ad_astra.registry.ModItems;
import earth.terrarium.ad_astra.registry.ModTags;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.apache.commons.lang3.Range;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.StreamSupport;

public class SpaceSuit extends DyeableArmorItem implements FluidContainingItem, ModArmourItem {

    public SpaceSuit(ArmorMaterial material, EquipmentSlot slot, Item.Settings settings) {
        super(material, slot, settings);
    }

    public static boolean hasFullSet(LivingEntity entity) {
        return StreamSupport.stream(entity.getArmorItems().spliterator(), false).allMatch(s -> s.getItem() instanceof SpaceSuit);
    }

    /**
     * Checks if the entity is wearing a space suit and if that space suit has oxygen.
     *
     * @param entity The entity wearing the space suit
     * @return Whether the entity has oxygen or not
     */
    public static boolean hasOxygenatedSpaceSuit(LivingEntity entity) {
        ItemStack chest = entity.getEquippedStack(EquipmentSlot.CHEST);
        if (chest.getItem() instanceof SpaceSuit suit) {
            return suit.getFluidAmount(chest) > 0;
        }

        return false;
    }

    public static void consumeSpaceSuitOxygen(LivingEntity entity, long amount) {
        ItemStack chest = entity.getEquippedStack(EquipmentSlot.CHEST);
        if (chest.getItem() instanceof SpaceSuit suit) {
            suit.setFluidAmount(chest, suit.getFluidAmount(chest) - amount);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        if (stack.isOf(ModItems.SPACE_SUIT.get()) || stack.isOf(ModItems.NETHERITE_SPACE_SUIT.get()) || stack.isOf(ModItems.JET_SUIT.get())) {
            long oxygen = FluidHooks.toMillibuckets(FluidHooks.getItemFluidManager(stack).getFluidInTank(0).getFluidAmount());
            tooltip.add(Text.translatable("tooltip.ad_astra.space_suit", oxygen, FluidHooks.toMillibuckets(getTankSize())).setStyle(Style.EMPTY.withColor(oxygen > 0 ? Formatting.GREEN : Formatting.RED)));
        }
    }

    @Override
    public long getTankSize() {
        return AdAstra.CONFIG.spaceSuit.spaceSuitTankSize;
    }

    @Override
    public BiPredicate<Integer, FluidHolder> getFilter() {
        return (i, f) -> f.getFluid().isIn(ModTags.OXYGEN);
    }

    public Range<Integer> getTemperatureThreshold() {
        return Range.between(-300, 60);
    }

    @Override
    public int getColor(ItemStack stack) {
        int colour = super.getColor(stack);
        return colour == 10511680 ? 0xFFFFFF : colour;
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return AdAstra.MOD_ID + ("overlay".equals(type) ? ":textures/entity/armour/space_suit_overlay.png" : ":textures/entity/armour/space_suit.png");
    }
}