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

public class NasaWorkbenchCategory implements IRecipeCategory<NasaWorkbenchRecipe> {
    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "nasa_workbench");
    public static final RecipeType<NasaWorkbenchRecipe> RECIPE = new RecipeType<>(ID, NasaWorkbenchRecipe.class);

    private final IGuiHelper guiHelper;

    public NasaWorkbenchCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
    }

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
        builder.addSlot(RecipeIngredientRole.INPUT, 57, 16).addIngredients(recipe.ingredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 48, 34).addIngredients(recipe.ingredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 66, 34).addIngredients(recipe.ingredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 48, 52).addIngredients(recipe.ingredients().get(3));
        builder.addSlot(RecipeIngredientRole.INPUT, 66, 52).addIngredients(recipe.ingredients().get(4));
        builder.addSlot(RecipeIngredientRole.INPUT, 48, 70).addIngredients(recipe.ingredients().get(5));
        builder.addSlot(RecipeIngredientRole.INPUT, 66, 70).addIngredients(recipe.ingredients().get(6));
        builder.addSlot(RecipeIngredientRole.INPUT, 30, 88).addIngredients(recipe.ingredients().get(7));
        builder.addSlot(RecipeIngredientRole.INPUT, 48, 88).addIngredients(recipe.ingredients().get(8));
        builder.addSlot(RecipeIngredientRole.INPUT, 66, 88).addIngredients(recipe.ingredients().get(9));
        builder.addSlot(RecipeIngredientRole.INPUT, 84, 88).addIngredients(recipe.ingredients().get(10));
        builder.addSlot(RecipeIngredientRole.INPUT, 30, 106).addIngredients(recipe.ingredients().get(11));
        builder.addSlot(RecipeIngredientRole.INPUT, 57, 106).addIngredients(recipe.ingredients().get(12));
        builder.addSlot(RecipeIngredientRole.INPUT, 84, 106).addIngredients(recipe.ingredients().get(13));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 130, 52).addItemStack(recipe.result());
    }

    @Override
    public void draw(NasaWorkbenchRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        graphics.blit(NasaWorkbenchScreen.TEXTURE, 1, -4, 00, 0, 177, 140, 177, 224);
        graphics.blit(NasaWorkbenchScreen.TEXTURE, 1, 136, 0, 217, 177, 7, 177, 224);
    }
}
