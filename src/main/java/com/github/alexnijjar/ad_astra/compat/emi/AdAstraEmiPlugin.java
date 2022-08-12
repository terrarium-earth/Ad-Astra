package com.github.alexnijjar.ad_astra.compat.emi;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.compat.emi.recipes.*;
import com.github.alexnijjar.ad_astra.items.OxygenTankItem;
import com.github.alexnijjar.ad_astra.items.armour.JetSuit;
import com.github.alexnijjar.ad_astra.items.armour.NetheriteSpaceSuit;
import com.github.alexnijjar.ad_astra.items.armour.SpaceSuit;
import com.github.alexnijjar.ad_astra.items.vehicles.VehicleItem;
import com.github.alexnijjar.ad_astra.recipes.*;
import com.github.alexnijjar.ad_astra.registry.ModFluids;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.registry.ModRecipes;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.EmiStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeManager;

public class AdAstraEmiPlugin implements EmiPlugin, EmiCategories {

	@Override
	public void register(EmiRegistry registry) {
		this.appendCustomStacks(registry);

		registry.addCategory(COAL_GENERATOR_CATEGORY);
		registry.addCategory(COMPRESSOR_CATEGORY);
		registry.addCategory(FUEL_CONVERSION_CATEGORY);
		registry.addCategory(OXYGEN_CONVERSION_CATEGORY);
		registry.addCategory(CRYO_FREEZER_CONVERSION_CATEGORY);
		registry.addCategory(NASA_WORKBENCH_CATEGORY);
		registry.addCategory(SPACE_STATION_CATEGORY);

		registry.addWorkstation(COAL_GENERATOR_CATEGORY, EmiStack.of(ModItems.COAL_GENERATOR));
		registry.addWorkstation(COMPRESSOR_CATEGORY, EmiStack.of(ModItems.COMPRESSOR));
		registry.addWorkstation(FUEL_CONVERSION_CATEGORY, EmiStack.of(ModItems.FUEL_REFINERY));
		registry.addWorkstation(OXYGEN_CONVERSION_CATEGORY, EmiStack.of(ModItems.OXYGEN_LOADER));
		registry.addWorkstation(OXYGEN_CONVERSION_CATEGORY, EmiStack.of(ModItems.OXYGEN_DISTRIBUTOR));
		registry.addWorkstation(CRYO_FREEZER_CONVERSION_CATEGORY, EmiStack.of(ModItems.CRYO_FREEZER));
		registry.addWorkstation(NASA_WORKBENCH_CATEGORY, EmiStack.of(ModItems.NASA_WORKBENCH));

		RecipeManager manager = registry.getRecipeManager();

		for (GeneratingRecipe recipe : manager.listAllOfType(ModRecipes.GENERATING_RECIPE)) {
			registry.addRecipe(new EmiGeneratingRecipe(recipe));
		}

		for (CompressingRecipe recipe : manager.listAllOfType(ModRecipes.COMPRESSING_RECIPE)) {
			registry.addRecipe(new EmiCompressingRecipe(recipe));
		}

		for (FuelConversionRecipe recipe : manager.listAllOfType(ModRecipes.FUEL_CONVERSION_RECIPE)) {
			registry.addRecipe(new EmiFuelConversionRecipe(recipe));
		}

		for (OxygenConversionRecipe recipe : manager.listAllOfType(ModRecipes.OXYGEN_CONVERSION_RECIPE)) {
			registry.addRecipe(new EmiOxygenConversionRecipe(recipe));
		}

		for (CryoFuelConversionRecipe recipe : manager.listAllOfType(ModRecipes.CRYO_FUEL_CONVERSION_RECIPE)) {
			registry.addRecipe(new EmiCryoFreezerConversionRecipe(recipe));
		}

		for (NasaWorkbenchRecipe recipe : manager.listAllOfType(ModRecipes.NASA_WORKBENCH_RECIPE)) {
			registry.addRecipe(new EmiNasaWorkbenchRecipe(recipe));
		}

		for (SpaceStationRecipe recipe : manager.listAllOfType(ModRecipes.SPACE_STATION_RECIPE)) {
			registry.addRecipe(new EmiSpaceStationRecipe(recipe));
		}
	}

	// Add stacks with custom nbt
	public void appendCustomStacks(EmiRegistry registry) {
		ItemStack tier1Rocket = ModItems.TIER_1_ROCKET.getDefaultStack();
		((VehicleItem) tier1Rocket.getItem()).setAmount(tier1Rocket, ((VehicleItem) tier1Rocket.getItem()).getTankSize());
		((VehicleItem) tier1Rocket.getItem()).setFluid(tier1Rocket, FluidVariant.of(ModFluids.FUEL_STILL));

		ItemStack tier2Rocket = ModItems.TIER_2_ROCKET.getDefaultStack();
		((VehicleItem) tier2Rocket.getItem()).setAmount(tier2Rocket, ((VehicleItem) tier2Rocket.getItem()).getTankSize());
		((VehicleItem) tier2Rocket.getItem()).setFluid(tier2Rocket, FluidVariant.of(ModFluids.FUEL_STILL));

		ItemStack tier3Rocket = ModItems.TIER_3_ROCKET.getDefaultStack();
		((VehicleItem) tier3Rocket.getItem()).setAmount(tier3Rocket, ((VehicleItem) tier3Rocket.getItem()).getTankSize());
		((VehicleItem) tier3Rocket.getItem()).setFluid(tier3Rocket, FluidVariant.of(ModFluids.FUEL_STILL));

		ItemStack tier4Rocket = ModItems.TIER_4_ROCKET.getDefaultStack();
		((VehicleItem) tier4Rocket.getItem()).setAmount(tier4Rocket, ((VehicleItem) tier4Rocket.getItem()).getTankSize());
		((VehicleItem) tier4Rocket.getItem()).setFluid(tier4Rocket, FluidVariant.of(ModFluids.FUEL_STILL));

		ItemStack tier1Rover = ModItems.TIER_1_ROVER.getDefaultStack();
		((VehicleItem) tier1Rover.getItem()).setAmount(tier1Rover, ((VehicleItem) tier1Rover.getItem()).getTankSize());
		((VehicleItem) tier1Rover.getItem()).setFluid(tier1Rover, FluidVariant.of(ModFluids.FUEL_STILL));

		ItemStack spaceSuit = ModItems.SPACE_SUIT.getDefaultStack();
		((SpaceSuit) spaceSuit.getItem()).setAmount(spaceSuit, ((SpaceSuit) spaceSuit.getItem()).getTankSize());
		((SpaceSuit) spaceSuit.getItem()).setFluid(spaceSuit, FluidVariant.of(ModFluids.OXYGEN_STILL));

		ItemStack netheriteSpaceSuit = ModItems.NETHERITE_SPACE_SUIT.getDefaultStack();
		((NetheriteSpaceSuit) netheriteSpaceSuit.getItem()).setAmount(netheriteSpaceSuit, ((NetheriteSpaceSuit) netheriteSpaceSuit.getItem()).getTankSize());
		((SpaceSuit) netheriteSpaceSuit.getItem()).setFluid(netheriteSpaceSuit, FluidVariant.of(ModFluids.OXYGEN_STILL));

		ItemStack jetSuit = ModItems.JET_SUIT.getDefaultStack();
		((JetSuit) jetSuit.getItem()).setAmount(jetSuit, ((JetSuit) jetSuit.getItem()).getTankSize());
		((SpaceSuit) jetSuit.getItem()).setFluid(jetSuit, FluidVariant.of(ModFluids.OXYGEN_STILL));
		((JetSuit) jetSuit.getItem()).setStoredEnergy(jetSuit, AdAstra.CONFIG.spaceSuit.jetSuitMaxEnergy);

		ItemStack energizer = ModItems.ENERGIZER.getDefaultStack();
		energizer.getOrCreateNbt().putLong("energy", AdAstra.CONFIG.energizer.maxEnergy);

		registry.addEmiStackAfter(EmiStack.of(tier1Rocket), EmiStack.of(ModItems.TIER_1_ROCKET));
		registry.addEmiStackAfter(EmiStack.of(tier2Rocket), EmiStack.of(ModItems.TIER_2_ROCKET));
		registry.addEmiStackAfter(EmiStack.of(tier3Rocket), EmiStack.of(ModItems.TIER_3_ROCKET));
		registry.addEmiStackAfter(EmiStack.of(tier4Rocket), EmiStack.of(ModItems.TIER_4_ROCKET));
		registry.addEmiStackAfter(EmiStack.of(tier1Rover), EmiStack.of(ModItems.TIER_1_ROVER));
		registry.addEmiStackAfter(EmiStack.of(spaceSuit), EmiStack.of(ModItems.SPACE_SUIT));
		registry.addEmiStackAfter(EmiStack.of(netheriteSpaceSuit), EmiStack.of(ModItems.NETHERITE_SPACE_SUIT));
		registry.addEmiStackAfter(EmiStack.of(jetSuit), EmiStack.of(ModItems.JET_SUIT));
		registry.addEmiStackAfter(EmiStack.of(energizer), EmiStack.of(ModItems.ENERGIZER));
		registry.addEmiStackAfter(EmiStack.of(OxygenTankItem.createOxygenatedTank()), EmiStack.of(ModItems.OXYGEN_TANK));
	}
}
