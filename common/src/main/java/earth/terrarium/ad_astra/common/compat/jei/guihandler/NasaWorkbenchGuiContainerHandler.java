package earth.terrarium.ad_astra.common.compat.jei.guihandler;

import earth.terrarium.ad_astra.client.screen.NasaWorkbenchScreen;
import earth.terrarium.ad_astra.common.compat.jei.category.NasaWorkbenchCategory;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.MethodsReturnNonnullByDefault;

import java.awt.*;

@MethodsReturnNonnullByDefault
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
