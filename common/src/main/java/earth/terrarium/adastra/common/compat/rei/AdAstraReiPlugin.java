package earth.terrarium.adastra.common.compat.rei;

import earth.terrarium.adastra.common.compat.rei.categories.SeparatorCategory;
import earth.terrarium.adastra.common.compat.rei.displays.SeparatorDisplay;
import earth.terrarium.adastra.common.recipes.SeparatingRecipe;
import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
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
}
