package earth.terrarium.adastra.common.compat.jei.categories;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.machines.NasaWorkbenchScreen;
import earth.terrarium.adastra.common.recipes.machines.NasaWorkbenchRecipe;
import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.adastra.common.registry.ModItems;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

public record NasaWorkbenchCategory(IGuiHelper guiHelper) implements IRecipeCategory<NasaWorkbenchRecipe> {
    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "nasa_workbench");
    public static final RecipeType<NasaWorkbenchRecipe> RECIPE = new RecipeType<>(ID, NasaWorkbenchRecipe.class);

    @Override
    public RecipeType<NasaWorkbenchRecipe> getRecipeType() {
        return RECIPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(ModBlocks.NASA_WORKBENCH.get().getDescriptionId());
    }

    @Override
    public IDrawable getBackground() {
        return guiHelper.createBlankDrawable(180, 145);
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableItemStack(ModItems.NASA_WORKBENCH.get().getDefaultInstance());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, NasaWorkbenchRecipe recipe, IFocusGroup focuses) {
        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addIngredients(Ingredient.of(ModItems.NASA_WORKBENCH.get()));
        slot(builder, recipe, 57, 16, 0);
        slot(builder, recipe, 48, 34, 1);
        slot(builder, recipe, 66, 34, 2);
        slot(builder, recipe, 48, 52, 3);
        slot(builder, recipe, 66, 52, 4);
        slot(builder, recipe, 48, 70, 5);
        slot(builder, recipe, 66, 70, 6);
        slot(builder, recipe, 30, 88, 7);
        slot(builder, recipe, 48, 88, 8);
        slot(builder, recipe, 66, 88, 9);
        slot(builder, recipe, 84, 88, 10);
        slot(builder, recipe, 30, 106, 11);
        slot(builder, recipe, 57, 106, 12);
        slot(builder, recipe, 84, 106, 13);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 130, 52).addItemStack(recipe.result());
    }

    private void slot(IRecipeLayoutBuilder builder, NasaWorkbenchRecipe display, int x, int y, int index) {
        var slot = builder.addSlot(RecipeIngredientRole.INPUT, x, y);
        if (index < display.ingredients().size()) {
            slot.addIngredients(display.ingredients().get(index));
        }
    }

    @Override
    public void draw(NasaWorkbenchRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        graphics.blit(NasaWorkbenchScreen.TEXTURE, 1, -4, 0, 0, 177, 140, 177, 224);
        graphics.blit(NasaWorkbenchScreen.TEXTURE, 1, 136, 0, 217, 177, 7, 177, 224);
    }
}
