package earth.terrarium.ad_astra.compat.jei;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.screen.*;
import earth.terrarium.ad_astra.compat.jei.category.*;
import earth.terrarium.ad_astra.compat.jei.guihandler.*;
import earth.terrarium.ad_astra.registry.ModItems;
import earth.terrarium.ad_astra.registry.ModRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

@JeiPlugin
public class AdAstraJeiPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(AdAstra.MOD_ID, "jei");
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new CompressorCategory(guiHelper));
        registration.addRecipeCategories(new NasaWorkbenchCategory(guiHelper));
        registration.addRecipeCategories(new FuelConversionCategory(guiHelper));
        registration.addRecipeCategories(new OxygenConversionCategory(guiHelper));
        registration.addRecipeCategories(new CryoFuelConversionCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Level level = Minecraft.getInstance().level;
        assert level != null;
        registration.addRecipes(CompressorCategory.RECIPE, level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.COMPRESSING_RECIPE.get()));
        registration.addRecipes(NasaWorkbenchCategory.RECIPE, level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.NASA_WORKBENCH_RECIPE.get()));
        registration.addRecipes(FuelConversionCategory.RECIPE, level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.FUEL_CONVERSION_RECIPE.get()));
        registration.addRecipes(OxygenConversionCategory.RECIPE, level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.OXYGEN_CONVERSION_RECIPE.get()));
        registration.addRecipes(CryoFuelConversionCategory.RECIPE, level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.CRYO_FUEL_CONVERSION_RECIPE.get()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModItems.COMPRESSOR.get().getDefaultInstance(), CompressorCategory.RECIPE);
        registration.addRecipeCatalyst(ModItems.NASA_WORKBENCH.get().getDefaultInstance(), NasaWorkbenchCategory.RECIPE);
        registration.addRecipeCatalyst(ModItems.FUEL_REFINERY.get().getDefaultInstance(), FuelConversionCategory.RECIPE);
        registration.addRecipeCatalyst(ModItems.OXYGEN_LOADER.get().getDefaultInstance(), OxygenConversionCategory.RECIPE);
        registration.addRecipeCatalyst(ModItems.OXYGEN_DISTRIBUTOR.get().getDefaultInstance(), OxygenConversionCategory.RECIPE);
        registration.addRecipeCatalyst(ModItems.CRYO_FREEZER.get().getDefaultInstance(), CryoFuelConversionCategory.RECIPE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGuiContainerHandler(CompressorScreen.class, new CompressorGuiContainerHandler());
        registration.addGuiContainerHandler(NasaWorkbenchScreen.class, new NasaWorkbenchGuiContainerHandler());
        registration.addGuiContainerHandler(ConversionScreen.class, new FuelConversionGuiContainerHandler());
        registration.addGuiContainerHandler(ConversionScreen.class, new OxygenConversionGuiContainerHandler());
        registration.addGuiContainerHandler(OxygenDistributorScreen.class, new OxygenDistributorGuiContainerHandler());
        registration.addGuiContainerHandler(CryoFreezerScreen.class, new CryoFreezerGuiContainerHandler());
    }
}
