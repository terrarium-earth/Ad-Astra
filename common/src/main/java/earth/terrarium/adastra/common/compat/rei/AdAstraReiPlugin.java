package earth.terrarium.adastra.common.compat.rei;

import earth.terrarium.adastra.client.screens.SeparatorScreen;
import earth.terrarium.adastra.common.compat.rei.categories.SeparatorCategory;
import earth.terrarium.adastra.common.compat.rei.displays.SeparatorDisplay;
import earth.terrarium.adastra.common.recipes.SeparatingRecipe;
import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;

public class AdAstraReiPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new SeparatorCategory());

        registry.addWorkstations(SeparatorCategory.ID, EntryStacks.of(ModBlocks.SEPARATOR.get()));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(SeparatingRecipe.class, ModRecipeTypes.SEPARATING.get(), SeparatorDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(s -> new Rectangle(s.leftPos() + SeparatorScreen.CLICK_AREA.getX(), s.topPos() + SeparatorScreen.CLICK_AREA.getY(), SeparatorScreen.CLICK_AREA.getWidth(), SeparatorScreen.CLICK_AREA.getHeight()), SeparatorScreen.class, SeparatorCategory.ID);
    }
}
