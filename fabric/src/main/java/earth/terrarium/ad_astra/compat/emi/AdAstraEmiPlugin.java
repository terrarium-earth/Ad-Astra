package earth.terrarium.ad_astra.compat.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.EmiStack;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.compat.emi.recipes.*;
import earth.terrarium.ad_astra.items.OxygenTankItem;
import earth.terrarium.ad_astra.items.armour.JetSuit;
import earth.terrarium.ad_astra.items.armour.NetheriteSpaceSuit;
import earth.terrarium.ad_astra.items.armour.SpaceSuit;
import earth.terrarium.ad_astra.items.vehicles.VehicleItem;
import earth.terrarium.ad_astra.recipes.*;
import earth.terrarium.ad_astra.registry.ModFluids;
import earth.terrarium.ad_astra.registry.ModItems;
import earth.terrarium.ad_astra.registry.ModRecipeTypes;
import earth.terrarium.botarium.api.energy.EnergyHooks;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

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

        registry.addWorkstation(COAL_GENERATOR_CATEGORY, EmiStack.of(ModItems.COAL_GENERATOR.get()));
        registry.addWorkstation(COMPRESSOR_CATEGORY, EmiStack.of(ModItems.COMPRESSOR.get()));
        registry.addWorkstation(FUEL_CONVERSION_CATEGORY, EmiStack.of(ModItems.FUEL_REFINERY.get()));
        registry.addWorkstation(OXYGEN_CONVERSION_CATEGORY, EmiStack.of(ModItems.OXYGEN_LOADER.get()));
        registry.addWorkstation(OXYGEN_CONVERSION_CATEGORY, EmiStack.of(ModItems.OXYGEN_DISTRIBUTOR.get()));
        registry.addWorkstation(CRYO_FREEZER_CONVERSION_CATEGORY, EmiStack.of(ModItems.CRYO_FREEZER.get()));
        registry.addWorkstation(NASA_WORKBENCH_CATEGORY, EmiStack.of(ModItems.NASA_WORKBENCH.get()));

        RecipeManager manager = registry.getRecipeManager();

        for (CompressingRecipe recipe : manager.getAllRecipesFor(ModRecipeTypes.COMPRESSING_RECIPE.get())) {
            registry.addRecipe(new EmiCompressingRecipe(recipe));
        }

        for (FluidConversionRecipe recipe : manager.getAllRecipesFor(ModRecipeTypes.FUEL_CONVERSION_RECIPE.get())) {
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
        ItemStack tier1Rocket = ModItems.TIER_1_ROCKET.get().getDefaultInstance();
        ((VehicleItem) tier1Rocket.getItem()).setFluidAmount(tier1Rocket, ((VehicleItem) tier1Rocket.getItem()).getTankSize());
        ((VehicleItem) tier1Rocket.getItem()).setFluid(tier1Rocket, ModFluids.FUEL_STILL.get());

        ItemStack tier2Rocket = ModItems.TIER_2_ROCKET.get().getDefaultInstance();
        ((VehicleItem) tier2Rocket.getItem()).setFluidAmount(tier2Rocket, ((VehicleItem) tier2Rocket.getItem()).getTankSize());
        ((VehicleItem) tier2Rocket.getItem()).setFluid(tier2Rocket, ModFluids.FUEL_STILL.get());

        ItemStack tier3Rocket = ModItems.TIER_3_ROCKET.get().getDefaultInstance();
        ((VehicleItem) tier3Rocket.getItem()).setFluidAmount(tier3Rocket, ((VehicleItem) tier3Rocket.getItem()).getTankSize());
        ((VehicleItem) tier3Rocket.getItem()).setFluid(tier3Rocket, ModFluids.FUEL_STILL.get());

        ItemStack tier4Rocket = ModItems.TIER_4_ROCKET.get().getDefaultInstance();
        ((VehicleItem) tier4Rocket.getItem()).setFluidAmount(tier4Rocket, ((VehicleItem) tier4Rocket.getItem()).getTankSize());
        ((VehicleItem) tier4Rocket.getItem()).setFluid(tier4Rocket, ModFluids.FUEL_STILL.get());

        ItemStack tier1Rover = ModItems.TIER_1_ROVER.get().getDefaultInstance();
        ((VehicleItem) tier1Rover.getItem()).setFluidAmount(tier1Rover, ((VehicleItem) tier1Rover.getItem()).getTankSize());
        ((VehicleItem) tier1Rover.getItem()).setFluid(tier1Rover, ModFluids.FUEL_STILL.get());

        ItemStack spaceSuit = ModItems.SPACE_SUIT.get().getDefaultInstance();
        ((SpaceSuit) spaceSuit.getItem()).setFluidAmount(spaceSuit, ((SpaceSuit) spaceSuit.getItem()).getTankSize());
        ((SpaceSuit) spaceSuit.getItem()).setFluid(spaceSuit, ModFluids.OXYGEN_STILL.get());

        ItemStack netheriteSpaceSuit = ModItems.NETHERITE_SPACE_SUIT.get().getDefaultInstance();
        ((NetheriteSpaceSuit) netheriteSpaceSuit.getItem()).setFluidAmount(netheriteSpaceSuit, ((NetheriteSpaceSuit) netheriteSpaceSuit.getItem()).getTankSize());
        ((SpaceSuit) netheriteSpaceSuit.getItem()).setFluid(netheriteSpaceSuit, ModFluids.OXYGEN_STILL.get());

        ItemStack jetSuit = ModItems.JET_SUIT.get().getDefaultInstance();
        ((JetSuit) jetSuit.getItem()).setFluidAmount(jetSuit, ((JetSuit) jetSuit.getItem()).getTankSize());
        ((SpaceSuit) jetSuit.getItem()).setFluid(jetSuit, ModFluids.OXYGEN_STILL.get());
        ItemStackHolder jetSuitHolder = new ItemStackHolder(jetSuit);
        EnergyHooks.getItemEnergyManager(jetSuit).insert(jetSuitHolder, AdAstra.CONFIG.spaceSuit.jetSuitMaxEnergy, false);
        if (jetSuitHolder.isDirty()) jetSuit = jetSuitHolder.getStack();

        ItemStack energizer = ModItems.ENERGIZER.get().getDefaultInstance();
        energizer.getOrCreateTag().putLong("energy", AdAstra.CONFIG.energizer.maxEnergy);

        registry.addEmiStackAfter(EmiStack.of(tier1Rocket), EmiStack.of(ModItems.TIER_1_ROCKET.get()));
        registry.addEmiStackAfter(EmiStack.of(tier2Rocket), EmiStack.of(ModItems.TIER_2_ROCKET.get()));
        registry.addEmiStackAfter(EmiStack.of(tier3Rocket), EmiStack.of(ModItems.TIER_3_ROCKET.get()));
        registry.addEmiStackAfter(EmiStack.of(tier4Rocket), EmiStack.of(ModItems.TIER_4_ROCKET.get()));
        registry.addEmiStackAfter(EmiStack.of(tier1Rover), EmiStack.of(ModItems.TIER_1_ROVER.get()));
        registry.addEmiStackAfter(EmiStack.of(spaceSuit), EmiStack.of(ModItems.SPACE_SUIT.get()));
        registry.addEmiStackAfter(EmiStack.of(netheriteSpaceSuit), EmiStack.of(ModItems.NETHERITE_SPACE_SUIT.get()));
        registry.addEmiStackAfter(EmiStack.of(jetSuit), EmiStack.of(ModItems.JET_SUIT.get()));
        registry.addEmiStackAfter(EmiStack.of(energizer), EmiStack.of(ModItems.ENERGIZER.get()));
        registry.addEmiStackAfter(EmiStack.of(OxygenTankItem.createOxygenatedTank()), EmiStack.of(ModItems.OXYGEN_TANK.get()));
    }
}
