package com.github.alexnijjar.ad_astra.items;

import java.util.List;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.registry.ModFluids;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.util.FluidUtils;

import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.fluid.PlatformFluidHandler;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class OxygenTankItem extends Item implements FluidContainingItem {

	public OxygenTankItem(Settings settings) {
		super(settings);
	}

	@Override
	public List<Fluid> getInputFluids() {
		return List.of(ModFluids.OXYGEN_STILL);
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		long oxygen = FluidUtils.dropletsToMillibuckets(this.getFluidContainer(stack).getFluids().get(0).getFluidAmount());
		tooltip.add(Text.translatable("tooltip.ad_astra.consumable"));
		tooltip.add(Text.translatable("tooltip.ad_astra.space_suit", oxygen, FluidUtils.dropletsToMillibuckets(getTankSize())).setStyle(Style.EMPTY.withColor(oxygen > 0 ? Formatting.GREEN : Formatting.RED)));
	}


	// Consume the tank and give the player oxygen.
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (!world.isClient) {
			ItemStack tank = user.getStackInHand(hand);
			ItemStack chest = user.getEquippedStack(EquipmentSlot.CHEST);
			if (chest.isOf(ModItems.SPACE_SUIT.get()) || chest.isOf(ModItems.NETHERITE_SPACE_SUIT.get()) || chest.isOf(ModItems.JET_SUIT.get())) {


				PlatformFluidHandler from = FluidHooks.getItemFluidManager(tank);
				ItemStack to = user.getInventory().getArmorStack(2);

				if (FluidHooks.moveItemToItemFluid(tank, to, from.getFluidInTank(0)) > 0) {
					world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.PLAYERS, 1, 1);
					return TypedActionResult.consume(tank);
				}
			}
		}
		return super.use(world, user, hand);
	}

	public static ItemStack createOxygenatedTank() {
		ItemStack oxygenTank = ModItems.OXYGEN_TANK.get().getDefaultStack();
		((OxygenTankItem) oxygenTank.getItem()).setAmount(oxygenTank, AdAstra.CONFIG.general.oxygenTankSize);
		((OxygenTankItem) oxygenTank.getItem()).setFluid(oxygenTank, ModFluids.OXYGEN_STILL);
		
		return oxygenTank;
	}

	@Override
	public long getTankSize() {
		return AdAstra.CONFIG.general.oxygenTankSize;
	}
}