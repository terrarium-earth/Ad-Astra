package com.github.alexnijjar.beyond_earth.items.armour;

import java.util.List;
import java.util.stream.StreamSupport;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.items.FluidContainingItem;
import com.github.alexnijjar.beyond_earth.registry.ModFluids;
import com.github.alexnijjar.beyond_earth.registry.ModItems;
import com.github.alexnijjar.beyond_earth.util.FluidUtils;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class SpaceSuit extends ArmorItem implements FluidContainingItem {

    public static final long TANK_SIZE = BeyondEarth.CONFIG.spaceSuit.spaceSuitTankSize;

    public SpaceSuit(ArmorMaterial material, EquipmentSlot slot, Item.Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public List<Fluid> getInputFluids() {
        return List.of(ModFluids.OXYGEN_STILL);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        if (stack.isOf(ModItems.SPACE_SUIT) || stack.isOf(ModItems.NETHERITE_SPACE_SUIT) || stack.isOf(ModItems.JET_SUIT)) {
            long oxygen = FluidUtils.dropletsToMillibuckets(this.getAmount(stack));
            tooltip.add(new TranslatableText("tooltip.beyond_earth.space_suit", oxygen, FluidUtils.dropletsToMillibuckets(getTankSize())).setStyle(Style.EMPTY.withColor(oxygen > 0 ? Formatting.GREEN : Formatting.RED)));
        }
    }

    @Override
    public long getTankSize() {
        return TANK_SIZE;
    }

    public static boolean hasFullSet(LivingEntity entity) {
        return StreamSupport.stream(entity.getArmorItems().spliterator(), false).allMatch(s -> s.getItem() instanceof SpaceSuit);
    }

    /**
     * Checks if the entity is wearing a space suit and if that space suit has oxygen.
     * @param entity The entity wearing the space suit
     * @return Whether the entity has oxygen or not
    
     */
    public static boolean hasOxygenatedSpaceSuit(LivingEntity entity) {
        ItemStack chest = entity.getEquippedStack(EquipmentSlot.CHEST);
        if (chest.getItem() instanceof SpaceSuit suit) {
            return suit.getAmount(chest) > 0;
        }

        return false;
    }
}