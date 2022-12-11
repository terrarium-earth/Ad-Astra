package earth.terrarium.ad_astra.common.compat.jei.category;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

/**
 * This class was largely inspired by or taken from the Resourceful Bees repository with
 * the expressed permission from @ThatGravyBoat.
 * @author Team Resourceful
 */
public abstract class BaseCategory<T> implements IRecipeCategory<T> {

    public final IGuiHelper guiHelper;
    private final RecipeType<T> recipeType;
    private final Component localizedName;
    private final IDrawable background;
    private final IDrawable icon;

    protected BaseCategory(IGuiHelper guiHelper, RecipeType<T> recipeType, Component localizedName, IDrawable background, IDrawable icon) {
        this.guiHelper = guiHelper;
        this.recipeType = recipeType;
        this.localizedName = localizedName;
        this.background = background;
        this.icon = icon;
    }

    @Override
    public @NotNull RecipeType<T> getRecipeType() {
        return this.recipeType;
    }

    @Override
    public @NotNull Component getTitle() {
        return localizedName;
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return icon;
    }
}