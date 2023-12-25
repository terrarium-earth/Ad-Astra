package earth.terrarium.adastra.common.compat.jei.categories;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.machines.CompressorScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.compat.jei.drawables.EnergyBarDrawable;
import earth.terrarium.adastra.common.compat.jei.drawables.EtaDrawable;
import earth.terrarium.adastra.common.recipes.machines.CompressingRecipe;
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

public class CompressingCategory implements IRecipeCategory<CompressingRecipe> {
    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "compressing");
    public static final RecipeType<CompressingRecipe> RECIPE = new RecipeType<>(ID, CompressingRecipe.class);

    private final IGuiHelper guiHelper;

    public CompressingCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
    }

    @Override
    public RecipeType<CompressingRecipe> getRecipeType() {
        return RECIPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(ModBlocks.COMPRESSOR.get().getDescriptionId());
    }

    @Override
    public IDrawable getBackground() {
        return guiHelper.createBlankDrawable(180, 110);
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableItemStack(ModItems.COMPRESSOR.get().getDefaultInstance());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CompressingRecipe recipe, IFocusGroup focuses) {
        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addIngredients(Ingredient.of(ModItems.COMPRESSOR.get()));
        builder.addSlot(RecipeIngredientRole.INPUT, 55, 52).addIngredients(recipe.ingredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 102, 52).addItemStack(recipe.result());
    }

    @Override
    public void draw(CompressingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        graphics.blit(CompressorScreen.TEXTURE, 2, -4, 0, 0, 177, 110, 177, 196);
        graphics.blit(CompressorScreen.TEXTURE, 2, 100, 0, 189, 177, 7, 177, 196);

        new EtaDrawable(mouseX, mouseY, recipe.cookingTime(), GuiUtils.HAMMER, 13, 13).draw(graphics, 80, 53);
        new EnergyBarDrawable(mouseX, mouseY, -recipe.energy(), 10_000, 250, 0).draw(graphics, 143, 57);
    }
}
