package earth.terrarium.ad_astra.common.compat.jei.guihandler;

import earth.terrarium.ad_astra.client.screen.ConversionScreen;
import earth.terrarium.ad_astra.common.block.machine.entity.OxygenLoaderBlockEntity;
import earth.terrarium.ad_astra.common.compat.jei.category.OxygenConversionCategory;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.MethodsReturnNonnullByDefault;

import java.awt.*;

@MethodsReturnNonnullByDefault
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
