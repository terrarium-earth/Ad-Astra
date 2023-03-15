package earth.terrarium.ad_astra.common.compat.jei.guihandler;

import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;

import earth.terrarium.ad_astra.client.screen.CompressorScreen;
import earth.terrarium.ad_astra.common.compat.jei.category.CompressorCategory;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;

public class CompressorGuiContainerHandler extends BaseGuiContainerHandler<CompressorScreen> {

    @Override
    public Rectangle getRecipeClickableAreaBounds(CompressorScreen screen) {
        return screen.getHammerBounds();
    }

    @Override
    protected RecipeType<?> getRecipeType(CompressorScreen screen) {
        return CompressorCategory.RECIPE;
    }

    @Override
    public List<Component> getRecipeTooltip(CompressorScreen screen) {
        return Collections.singletonList(screen.getHammerTooltip());
    }
}
