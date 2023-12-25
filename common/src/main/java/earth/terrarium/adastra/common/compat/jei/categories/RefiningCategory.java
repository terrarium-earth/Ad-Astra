package earth.terrarium.adastra.common.compat.jei.categories;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.machines.FuelRefineryScreen;
import earth.terrarium.adastra.common.compat.jei.drawables.EnergyBarDrawable;
import earth.terrarium.adastra.common.compat.jei.drawables.FluidBarDrawable;
import earth.terrarium.adastra.common.recipes.machines.RefiningRecipe;
import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.adastra.common.registry.ModItems;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
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

public class RefiningCategory implements IRecipeCategory<RefiningRecipe> {
    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "refining");
    public static final RecipeType<RefiningRecipe> RECIPE = new RecipeType<>(ID, RefiningRecipe.class);

    private final IGuiHelper guiHelper;

    public RefiningCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
    }

    @Override
    public RecipeType<RefiningRecipe> getRecipeType() {
        return RECIPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(ModBlocks.FUEL_REFINERY.get().getDescriptionId());
    }

    @Override
    public IDrawable getBackground() {
        return guiHelper.createBlankDrawable(180, 105);
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableItemStack(ModItems.FUEL_REFINERY.get().getDefaultInstance());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RefiningRecipe recipe, IFocusGroup focuses) {
        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addIngredients(Ingredient.of(ModItems.OXYGEN_LOADER.get()));
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 14, 18);
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 14, 48);
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 129, 18);
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 129, 48);
    }

    @Override
    public void draw(RefiningRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        graphics.blit(FuelRefineryScreen.TEXTURE, 2, -4, 0, 0, 177, 100, 177, 184);
        graphics.blit(FuelRefineryScreen.TEXTURE, 2, 96, 0, 177, 177, 7, 177, 184);

        new EnergyBarDrawable(mouseX, mouseY, -recipe.energy(), 20_000, 500, 0).draw(graphics, 146, 50);

        int cookTime = recipe.cookingTime();
        long capacity = FluidHooks.buckets(6);
        new FluidBarDrawable(mouseX, mouseY, false, capacity, cookTime, recipe.ingredient()).draw(graphics, 39, 49);
        new FluidBarDrawable(mouseX, mouseY, true, capacity, cookTime, recipe.resultFluid()).draw(graphics, 96, 49);
    }
}
