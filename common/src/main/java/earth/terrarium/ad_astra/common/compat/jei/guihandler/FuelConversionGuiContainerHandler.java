package earth.terrarium.ad_astra.common.compat.jei.guihandler;

import earth.terrarium.ad_astra.client.screen.ConversionScreen;
import earth.terrarium.ad_astra.common.block.machine.entity.FuelRefineryBlockEntity;
import earth.terrarium.ad_astra.common.compat.jei.category.FuelConversionCategory;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.MethodsReturnNonnullByDefault;

import java.awt.*;

@MethodsReturnNonnullByDefault
public class FuelConversionGuiContainerHandler extends BaseGuiContainerHandler<ConversionScreen> {

    @Override
    public boolean testRecipeClickable(ConversionScreen screen) {
        return super.testRecipeClickable(screen) && screen.getMenu().getMachine() instanceof FuelRefineryBlockEntity;
    }

    @Override
    public Rectangle getRecipeClickableAreaBounds(ConversionScreen screen) {
        return screen.getRecipeBounds();
    }

    @Override
    protected RecipeType<?> getRecipeType(ConversionScreen screen) {
        return FuelConversionCategory.RECIPE;
    }
}
