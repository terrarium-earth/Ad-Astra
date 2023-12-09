package earth.terrarium.adastra.common.compat.jei.categories;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.machines.SeparatorScreen;
import earth.terrarium.adastra.common.compat.jei.drawables.EnergyBarDrawable;
import earth.terrarium.adastra.common.compat.jei.drawables.FluidBarDrawable;
import earth.terrarium.adastra.common.recipes.machines.SeparatingRecipe;
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
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

@MethodsReturnNonnullByDefault
public class SeparatorCategory implements IRecipeCategory<SeparatingRecipe> {
    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "separating");
    public static final RecipeType<SeparatingRecipe> RECIPE = new RecipeType<>(ID, SeparatingRecipe.class);

    private final IGuiHelper guiHelper;

    public SeparatorCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
    }

    @Override
    public RecipeType<SeparatingRecipe> getRecipeType() {
        return RECIPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(ModBlocks.SEPARATOR.get().getDescriptionId());
    }

    @Override
    public IDrawable getBackground() {
        return guiHelper.createBlankDrawable(170, 131);
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableItemStack(ModItems.SEPARATOR.get().getDefaultInstance());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, @NotNull SeparatingRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addIngredients(Ingredient.of(ModItems.SEPARATOR.get()));
        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addFluidStack(recipe.ingredient().getFluid(), recipe.ingredient().getFluidAmount());
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addFluidStack(recipe.resultFluid1().getFluid(), recipe.resultFluid1().getFluidAmount());
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addFluidStack(recipe.resultFluid2().getFluid(), recipe.resultFluid2().getFluidAmount());
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 12, 101);
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 30, 101);
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 52, 101);
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 70, 101);
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 92, 101);
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 110, 101);
    }

    @Override
    public void draw(@NotNull SeparatingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.blit(SeparatorScreen.TEXTURE, -7, -4, 0, 33, 184, 134, 184, 255);
        guiGraphics.blit(SeparatorScreen.TEXTURE, -7, 126, 0, 246, 184, 9, 184, 255);

        new EnergyBarDrawable(mouseX, mouseY, -recipe.energy(), 100_000, 2_500, 0).draw(guiGraphics, 139, 67);

        int cookTime = recipe.cookingTime();
        long capacity = FluidHooks.buckets(10);
        new FluidBarDrawable(mouseX, mouseY, false, capacity, cookTime, recipe.ingredient()).draw(guiGraphics, 55, 56);
        new FluidBarDrawable(mouseX, mouseY, true, capacity, cookTime, recipe.resultFluid1()).draw(guiGraphics, 15, 68);
        new FluidBarDrawable(mouseX, mouseY, true, capacity, cookTime, recipe.resultFluid2()).draw(guiGraphics, 95, 68);
    }
}
