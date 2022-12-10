package earth.terrarium.ad_astra.compat.jei.guihandler;

import java.awt.Rectangle;

import earth.terrarium.ad_astra.block.machine.entity.OxygenLoaderBlockEntity;
import earth.terrarium.ad_astra.client.screen.ConversionScreen;
import earth.terrarium.ad_astra.compat.jei.category.OxygenConversionCategory;
import mezz.jei.api.recipe.RecipeType;

public class OxygenConversionGuiContainerHandler extends BaseGuiContainerHandler<ConversionScreen> {

    @Override
    public boolean testRecipeClickable(ConversionScreen screen) {
        return super.testRecipeClickable(screen) && screen.getMenu().getMachine() instanceof OxygenLoaderBlockEntity;
    }

    @Override
    public Rectangle getRecipeClickableAreaBounds(ConversionScreen screen) {
        return screen.getRecipeBounds();
    }

    @Override
    protected RecipeType<?> getRecipeType(ConversionScreen screen) {
        return OxygenConversionCategory.RECIPE;
    }
}
