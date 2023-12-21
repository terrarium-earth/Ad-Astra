package earth.terrarium.adastra.common.compat.rei;

import earth.terrarium.adastra.client.screens.PlanetsScreen;
import earth.terrarium.adastra.client.screens.machines.*;
import earth.terrarium.adastra.common.compat.rei.categories.*;
import earth.terrarium.adastra.common.compat.rei.displays.*;
import earth.terrarium.adastra.common.recipes.machines.*;
import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.OverlayDecider;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.InteractionResult;

public class AdAstraReiPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new CompressingCategory());
        registry.add(new OxygenLoadingCategory());
        registry.add(new RefiningCategory());
        registry.add(new CryoFreezingCategory());
        registry.add(new NasaWorkbenchCategory());

        registry.addWorkstations(CompressingCategory.ID, EntryStacks.of(ModBlocks.COMPRESSOR.get()));
        registry.addWorkstations(OxygenLoadingCategory.ID, EntryStacks.of(ModBlocks.OXYGEN_LOADER.get()));
        registry.addWorkstations(OxygenLoadingCategory.ID, EntryStacks.of(ModBlocks.OXYGEN_DISTRIBUTOR.get()));
        registry.addWorkstations(RefiningCategory.ID, EntryStacks.of(ModBlocks.FUEL_REFINERY.get()));
        registry.addWorkstations(CryoFreezingCategory.ID, EntryStacks.of(ModBlocks.CRYO_FREEZER.get()));
        registry.addWorkstations(NasaWorkbenchCategory.ID, EntryStacks.of(ModBlocks.NASA_WORKBENCH.get()));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(CompressingRecipe.class, ModRecipeTypes.COMPRESSING.get(), CompressingDisplay::new);
        registry.registerRecipeFiller(OxygenLoadingRecipe.class, ModRecipeTypes.OXYGEN_LOADING.get(), OxygenLoadingDisplay::new);
        registry.registerRecipeFiller(RefiningRecipe.class, ModRecipeTypes.REFINING.get(), RefiningDisplay::new);
        registry.registerRecipeFiller(CryoFreezingRecipe.class, ModRecipeTypes.CRYO_FREEZING.get(), CryoFreezingDisplay::new);
        registry.registerRecipeFiller(NasaWorkbenchRecipe.class, ModRecipeTypes.NASA_WORKBENCH.get(), NasaWorkbenchDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(s -> new Rectangle(s.leftPos() + CompressorScreen.CLICK_AREA.getX(), s.topPos() + CompressorScreen.CLICK_AREA.getY(), CompressorScreen.CLICK_AREA.getWidth(), CompressorScreen.CLICK_AREA.getHeight()), CompressorScreen.class, CompressingCategory.ID);
        registry.registerClickArea(s -> new Rectangle(s.leftPos() + OxygenLoaderScreen.CLICK_AREA.getX(), s.topPos() + OxygenLoaderScreen.CLICK_AREA.getY(), OxygenLoaderScreen.CLICK_AREA.getWidth(), OxygenLoaderScreen.CLICK_AREA.getHeight()), OxygenLoaderScreen.class, OxygenLoadingCategory.ID);
        registry.registerClickArea(s -> new Rectangle(s.leftPos() + OxygenDistributorScreen.CLICK_AREA.getX(), s.topPos() + OxygenDistributorScreen.CLICK_AREA.getY(), OxygenDistributorScreen.CLICK_AREA.getWidth(), OxygenDistributorScreen.CLICK_AREA.getHeight()), OxygenDistributorScreen.class, OxygenLoadingCategory.ID);
        registry.registerClickArea(s -> new Rectangle(s.leftPos() + FuelRefineryScreen.CLICK_AREA.getX(), s.topPos() + FuelRefineryScreen.CLICK_AREA.getY(), FuelRefineryScreen.CLICK_AREA.getWidth(), FuelRefineryScreen.CLICK_AREA.getHeight()), FuelRefineryScreen.class, RefiningCategory.ID);
        registry.registerClickArea(s -> new Rectangle(s.leftPos() + CryoFreezerScreen.CLICK_AREA.getX(), s.topPos() + CryoFreezerScreen.CLICK_AREA.getY(), CryoFreezerScreen.CLICK_AREA.getWidth(), CryoFreezerScreen.CLICK_AREA.getHeight()), CryoFreezerScreen.class, CryoFreezingCategory.ID);
        registry.registerClickArea(s -> new Rectangle(s.leftPos() + NasaWorkbenchScreen.CLICK_AREA.getX(), s.topPos() + NasaWorkbenchScreen.CLICK_AREA.getY(), NasaWorkbenchScreen.CLICK_AREA.getWidth(), NasaWorkbenchScreen.CLICK_AREA.getHeight()), NasaWorkbenchScreen.class, NasaWorkbenchCategory.ID);

        // Hide planets screen from REI
        registry.registerDecider(new OverlayDecider() {
            @Override
            public <R extends Screen> boolean isHandingScreen(Class<R> screen) {
                return screen == PlanetsScreen.class;
            }

            @Override
            public <R extends Screen> InteractionResult shouldScreenBeOverlaid(R screen) {
                return InteractionResult.FAIL;
            }
        });
    }


}
