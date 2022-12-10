package earth.terrarium.ad_astra.compat.jei.guihandler;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import earth.terrarium.ad_astra.client.screen.AbstractMachineScreen;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;

public abstract class BaseGuiContainerHandler<T extends AbstractMachineScreen<?, ?>> implements IGuiContainerHandler<T> {

    public static final Component SHOW_RECIPES = Component.translatable("jei.tooltip.show.recipes");

    @Override
    public Collection<IGuiClickableArea> getGuiClickableAreas(T containerScreen, double guiMouseX, double guiMouseY) {
        return this.getRecipeClickableAreas(containerScreen, guiMouseX, guiMouseY);
    }

    public Collection<IGuiClickableArea> getRecipeClickableAreas(T containerScreen, double guiMouseX, double guiMouseY) {
        if (this.testRecipeClickable(containerScreen)) {
            return Collections.singletonList(new IGuiClickableArea() {
                @Override
                public Rect2i getArea() {
                    Rectangle bounds = getRecipeClickableAreaBounds(containerScreen);
                    int guiLeft = containerScreen.getLeftPos();
                    int guiTop = containerScreen.getTopPos();
                    return new Rect2i(bounds.x - guiLeft, bounds.y - guiTop, bounds.width, bounds.height);
                }

                @Override
                public List<Component> getTooltipStrings() {
                    List<Component> tooltip = new ArrayList<>();
                    tooltip.addAll(getRecipeTooltip(containerScreen));
                    tooltip.add(SHOW_RECIPES);
                    return tooltip;
                }

                @Override
                public void onClick(IFocusFactory focusFactory, IRecipesGui recipesGui) {
                    recipesGui.showTypes(getRecipeTypes(containerScreen));
                }
            });
        } else {
            return Collections.emptyList();
        }
    }

    public boolean testRecipeClickable(T screen) {
        return true;
    }

    public abstract Rectangle getRecipeClickableAreaBounds(T screen);

    public List<RecipeType<?>> getRecipeTypes(T screen) {
        RecipeType<?> recipeType = this.getRecipeType(screen);
        if (recipeType != null) {
            return Collections.singletonList(recipeType);
        } else {
            return Collections.emptyList();
        }
    }

    protected RecipeType<?> getRecipeType(T screen) {
        return null;
    }

    public List<Component> getRecipeTooltip(T screen) {
        return Collections.emptyList();
    }
}
