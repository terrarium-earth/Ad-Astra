package earth.terrarium.ad_astra.common.compat.jei.guihandler;

import earth.terrarium.ad_astra.client.screen.CryoFreezerScreen;
import earth.terrarium.ad_astra.common.compat.jei.category.CryoFuelConversionCategory;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.MethodsReturnNonnullByDefault;

import java.awt.*;

@MethodsReturnNonnullByDefault
public class CryoFreezerGuiContainerHandler extends BaseGuiContainerHandler<CryoFreezerScreen> {

    @Override
    public Rectangle getRecipeClickableAreaBounds(CryoFreezerScreen screen) {
        return screen.getSnowFlakeBounds();
    }

    @Override
    protected RecipeType<?> getRecipeType(CryoFreezerScreen screen) {
        return CryoFuelConversionCategory.RECIPE;
    }
}
