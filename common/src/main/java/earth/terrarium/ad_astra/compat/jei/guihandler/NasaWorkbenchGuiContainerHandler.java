package earth.terrarium.ad_astra.compat.jei.guihandler;

import java.awt.Rectangle;

import earth.terrarium.ad_astra.client.screen.NasaWorkbenchScreen;
import earth.terrarium.ad_astra.compat.jei.category.NasaWorkbenchCategory;
import mezz.jei.api.recipe.RecipeType;

public class NasaWorkbenchGuiContainerHandler extends BaseGuiContainerHandler<NasaWorkbenchScreen> {

    @Override
    public Rectangle getRecipeClickableAreaBounds(NasaWorkbenchScreen screen) {
        return screen.getRecipeBounds();
    }

    @Override
    protected RecipeType<?> getRecipeType(NasaWorkbenchScreen screen) {
        return NasaWorkbenchCategory.RECIPE;
    }
}
