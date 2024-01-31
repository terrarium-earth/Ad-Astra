package earth.terrarium.adastra.common.compat.jei.categories;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.machines.EtrionicBlastFurnaceScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.compat.jei.drawables.EnergyBarDrawable;
import earth.terrarium.adastra.common.compat.jei.drawables.EtaDrawable;
import earth.terrarium.adastra.common.config.MachineConfig;
import earth.terrarium.adastra.common.recipes.machines.AlloyingRecipe;
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

public class AlloyingCategory implements IRecipeCategory<AlloyingRecipe> {
    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "alloying");
    public static final RecipeType<AlloyingRecipe> RECIPE = new RecipeType<>(ID, AlloyingRecipe.class);

    private final IGuiHelper guiHelper;

    public AlloyingCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
    }

    @Override
    public RecipeType<AlloyingRecipe> getRecipeType() {
        return RECIPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(ModBlocks.ETRIONIC_BLAST_FURNACE.get().getDescriptionId());
    }

    @Override
    public IDrawable getBackground() {
        return guiHelper.createBlankDrawable(180, 110);
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableItemStack(ModItems.ETRIONIC_BLAST_FURNACE.get().getDefaultInstance());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AlloyingRecipe recipe, IFocusGroup focuses) {
        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addIngredients(Ingredient.of(ModItems.ETRIONIC_BLAST_FURNACE.get()));
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 34).addIngredients(recipe.ingredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 45, 34).addIngredients(recipe.ingredients().get(1));
        if (recipe.ingredients().size() > 2) {
            builder.addSlot(RecipeIngredientRole.INPUT, 27, 53).addIngredients(recipe.ingredients().get(2));
        } else {
            builder.addSlot(RecipeIngredientRole.INPUT, 27, 53);
        }
        if (recipe.ingredients().size() > 3) {
            builder.addSlot(RecipeIngredientRole.INPUT, 45, 53).addIngredients(recipe.ingredients().get(3));
        } else {
            builder.addSlot(RecipeIngredientRole.INPUT, 45, 53);
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 99, 34).addItemStack(recipe.result());
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 117, 34);
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 99, 53);
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 117, 53);
    }

    @Override
    public void draw(AlloyingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        graphics.blit(EtrionicBlastFurnaceScreen.TEXTURE, -2, -4, 0, 0, 184, 110, 184, 201);
        graphics.blit(EtrionicBlastFurnaceScreen.TEXTURE, -2, 106, 0, 194, 184, 7, 184, 201);
        graphics.blit(EtrionicBlastFurnaceScreen.FURNACE_OVERLAY_FULL_TEXTURE, 28, 47, 0, 0, 32, 43, 32, 43);

        new EtaDrawable(mouseX, mouseY, recipe.cookingTime(), GuiUtils.ARROW, 20, 12).draw(graphics, 73, 46);
        new EnergyBarDrawable(mouseX, mouseY, -recipe.energy(), MachineConfig.STEEL.energyCapacity, MachineConfig.STEEL.maxEnergyInOut, 0).draw(graphics, 144, 63);
    }
}
