package com.github.alexnijjar.ad_astra.items.armour;

import java.util.List;
import java.util.stream.StreamSupport;

import earth.terrarium.botarium.api.fluid.FluidHooks;
import org.apache.commons.lang3.Range;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.items.FluidContainingItem;
import com.github.alexnijjar.ad_astra.registry.ModFluids;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.util.FluidUtils;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SpaceSuit extends DyeableArmorItem implements FluidContainingItem, ModArmourItem {

	public SpaceSuit(ArmorMaterial material, EquipmentSlot slot, Item.Settings settings) {
		super(material, slot, settings);
	}

	@Override
	public List<Fluid> getInputFluids() {
		return List.of(ModFluids.OXYGEN_STILL.get());
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		if (stack.isOf(ModItems.SPACE_SUIT.get()) || stack.isOf(ModItems.NETHERITE_SPACE_SUIT.get()) || stack.isOf(ModItems.JET_SUIT.get())) {
			long oxygen = FluidHooks.toMillibuckets(this.getAmount(stack));
			tooltip.add(Text.translatable("tooltip.ad_astra.space_suit", oxygen, FluidHooks.toMillibuckets(getTankSize())).setStyle(Style.EMPTY.withColor(oxygen > 0 ? Formatting.GREEN : Formatting.RED)));
		}
	}

	@Override
	public long getTankSize() {
		return AdAstra.CONFIG.spaceSuit.spaceSuitTankSize;
	}

	public Range<Integer> getTemperatureThreshold() {
		return Range.between(-300, 60);
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
			return suit.getAmount(chest) > 0;
		}

		return false;
	}

	public static void consumeSpaceSuitOxygen(LivingEntity entity, long amount) {
		ItemStack chest = entity.getEquippedStack(EquipmentSlot.CHEST);
		if (chest.getItem() instanceof SpaceSuit suit) {
			suit.setAmount(chest, suit.getAmount(chest) - amount);
		}
	}

	@Override
	public int getColor(ItemStack stack) {
		int colour = super.getColor(stack);
		return colour == 10511680 ? 0xFFFFFF : colour;
	}

	@Override
	public @Nullable String getArmorTexture(ItemStack stack, LivingEntity entity, EquipmentSlot slot, String type) {
		return AdAstra.MOD_ID + ":textures/entity/armour/space_suit.png";
	}
}