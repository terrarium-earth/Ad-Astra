package earth.terrarium.ad_astra.compat.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.EmiStack;
import earth.terrarium.ad_astra.compat.emi.recipes.*;
import earth.terrarium.ad_astra.config.EnergizerConfig;
import earth.terrarium.ad_astra.item.OxygenTankItem;
import earth.terrarium.ad_astra.item.armor.JetSuit;
import earth.terrarium.ad_astra.item.armor.NetheriteSpaceSuit;
import earth.terrarium.ad_astra.item.armor.SpaceSuit;
import earth.terrarium.ad_astra.item.vehicle.RocketItem;
import earth.terrarium.ad_astra.item.vehicle.RoverItem;
import earth.terrarium.ad_astra.recipe.*;
import earth.terrarium.ad_astra.registry.ModFluids;
import earth.terrarium.ad_astra.registry.ModItems;
import earth.terrarium.ad_astra.registry.ModRecipeTypes;
import earth.terrarium.botarium.api.energy.EnergyHooks;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.world.item.crafting.RecipeManager;

public class AdAstraEmiPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        this.appendCustomStacks(registry);

        registry.addCategory(EmiCategories.COMPRESSOR_CATEGORY);
        registry.addCategory(EmiCategories.FUEL_CONVERSION_CATEGORY);
        registry.addCategory(EmiCategories.OXYGEN_CONVERSION_CATEGORY);
        registry.addCategory(EmiCategories.CRYO_FREEZER_CONVERSION_CATEGORY);
        registry.addCategory(EmiCategories.NASA_WORKBENCH_CATEGORY);
        registry.addCategory(EmiCategories.SPACE_STATION_CATEGORY);

        registry.addWorkstation(EmiCategories.COMPRESSOR_CATEGORY, EmiStack.of(ModItems.COMPRESSOR.get()));
        registry.addWorkstation(EmiCategories.FUEL_CONVERSION_CATEGORY, EmiStack.of(ModItems.FUEL_REFINERY.get()));
        registry.addWorkstation(EmiCategories.OXYGEN_CONVERSION_CATEGORY, EmiStack.of(ModItems.OXYGEN_LOADER.get()));
        registry.addWorkstation(EmiCategories.OXYGEN_CONVERSION_CATEGORY, EmiStack.of(ModItems.OXYGEN_DISTRIBUTOR.get()));
        registry.addWorkstation(EmiCategories.CRYO_FREEZER_CONVERSION_CATEGORY, EmiStack.of(ModItems.CRYO_FREEZER.get()));
        registry.addWorkstation(EmiCategories.NASA_WORKBENCH_CATEGORY, EmiStack.of(ModItems.NASA_WORKBENCH.get()));

        RecipeManager manager = registry.getRecipeManager();

        for (CompressingRecipe recipe : manager.getAllRecipesFor(ModRecipeTypes.COMPRESSING_RECIPE.get())) {
            registry.addRecipe(new EmiCompressingRecipe(recipe));
        }

        for (FuelConversionRecipe recipe : manager.getAllRecipesFor(ModRecipeTypes.FUEL_CONVERSION_RECIPE.get())) {
            registry.addRecipe(new EmiFluidConversionRecipe(recipe));
        }

        for (OxygenConversionRecipe recipe : manager.getAllRecipesFor(ModRecipeTypes.OXYGEN_CONVERSION_RECIPE.get())) {
            registry.addRecipe(new EmiOxygenConversionRecipe(recipe));
        }

        for (CryoFuelConversionRecipe recipe : manager.getAllRecipesFor(ModRecipeTypes.CRYO_FUEL_CONVERSION_RECIPE.get())) {
            registry.addRecipe(new EmiCryoFreezerConversionRecipe(recipe));
        }

        for (NasaWorkbenchRecipe recipe : manager.getAllRecipesFor(ModRecipeTypes.NASA_WORKBENCH_RECIPE.get())) {
            registry.addRecipe(new EmiNasaWorkbenchRecipe(recipe));
        }

        for (SpaceStationRecipe recipe : manager.getAllRecipesFor(ModRecipeTypes.SPACE_STATION_RECIPE.get())) {
            registry.addRecipe(new EmiSpaceStationRecipe(recipe));
        }
    }

    // Add stacks with custom nbt
    public void appendCustomStacks(EmiRegistry registry) {
        ItemStackHolder tier1Rocket = new ItemStackHolder(ModItems.TIER_1_ROCKET.get().getDefaultInstance());
        ((RocketItem<?>) tier1Rocket.getStack().getItem()).insert(tier1Rocket, FluidHooks.newFluidHolder(ModFluids.FUEL.get(), Long.MAX_VALUE, null));

        ItemStackHolder tier2Rocket = new ItemStackHolder(ModItems.TIER_2_ROCKET.get().getDefaultInstance());
        ((RocketItem<?>) tier2Rocket.getStack().getItem()).insert(tier2Rocket, FluidHooks.newFluidHolder(ModFluids.FUEL.get(), Long.MAX_VALUE, null));

        ItemStackHolder tier3Rocket = new ItemStackHolder(ModItems.TIER_3_ROCKET.get().getDefaultInstance());
        ((RocketItem<?>) tier3Rocket.getStack().getItem()).insert(tier3Rocket, FluidHooks.newFluidHolder(ModFluids.FUEL.get(), Long.MAX_VALUE, null));

        ItemStackHolder tier4Rocket = new ItemStackHolder(ModItems.TIER_4_ROCKET.get().getDefaultInstance());
        ((RocketItem<?>) tier4Rocket.getStack().getItem()).insert(tier4Rocket, FluidHooks.newFluidHolder(ModFluids.FUEL.get(), Long.MAX_VALUE, null));

        ItemStackHolder tier1Rover = new ItemStackHolder(ModItems.TIER_1_ROVER.get().getDefaultInstance());
        ((RoverItem) tier1Rover.getStack().getItem()).insert(tier1Rover, FluidHooks.newFluidHolder(ModFluids.FUEL.get(), Long.MAX_VALUE, null));

        ItemStackHolder spaceSuit = new ItemStackHolder(ModItems.SPACE_SUIT.get().getDefaultInstance());
        ((SpaceSuit) spaceSuit.getStack().getItem()).insert(spaceSuit, FluidHooks.newFluidHolder(ModFluids.OXYGEN.get(), Long.MAX_VALUE, null));

        ItemStackHolder netheriteSpaceSuit = new ItemStackHolder(ModItems.NETHERITE_SPACE_SUIT.get().getDefaultInstance());
        ((NetheriteSpaceSuit) netheriteSpaceSuit.getStack().getItem()).insert(netheriteSpaceSuit, FluidHooks.newFluidHolder(ModFluids.OXYGEN.get(), Long.MAX_VALUE, null));

        ItemStackHolder jetSuit = new ItemStackHolder(ModItems.JET_SUIT.get().getDefaultInstance());
        ((JetSuit) jetSuit.getStack().getItem()).insert(jetSuit, FluidHooks.newFluidHolder(ModFluids.OXYGEN.get(), Long.MAX_VALUE, null));
        EnergyHooks.getItemEnergyManager(jetSuit.getStack()).insert(jetSuit, EnergizerConfig.maxEnergy, false);

        ItemStackHolder energizer = new ItemStackHolder(ModItems.ENERGIZER.get().getDefaultInstance());
        energizer.getStack().getOrCreateTag().putLong("Energy", EnergizerConfig.maxEnergy);

        registry.addEmiStackAfter(EmiStack.of(tier1Rocket.getStack()), EmiStack.of(ModItems.TIER_1_ROCKET.get()));
        registry.addEmiStackAfter(EmiStack.of(tier2Rocket.getStack()), EmiStack.of(ModItems.TIER_2_ROCKET.get()));
        registry.addEmiStackAfter(EmiStack.of(tier3Rocket.getStack()), EmiStack.of(ModItems.TIER_3_ROCKET.get()));
        registry.addEmiStackAfter(EmiStack.of(tier4Rocket.getStack()), EmiStack.of(ModItems.TIER_4_ROCKET.get()));
        registry.addEmiStackAfter(EmiStack.of(tier1Rover.getStack()), EmiStack.of(ModItems.TIER_1_ROVER.get()));
        registry.addEmiStackAfter(EmiStack.of(spaceSuit.getStack()), EmiStack.of(ModItems.SPACE_SUIT.get()));
        registry.addEmiStackAfter(EmiStack.of(netheriteSpaceSuit.getStack()), EmiStack.of(ModItems.NETHERITE_SPACE_SUIT.get()));
        registry.addEmiStackAfter(EmiStack.of(jetSuit.getStack()), EmiStack.of(ModItems.JET_SUIT.get()));
        registry.addEmiStackAfter(EmiStack.of(energizer.getStack()), EmiStack.of(ModItems.ENERGIZER.get()));
        registry.addEmiStackAfter(EmiStack.of(OxygenTankItem.createOxygenatedTank()), EmiStack.of(ModItems.OXYGEN_TANK.get()));
    }
}
