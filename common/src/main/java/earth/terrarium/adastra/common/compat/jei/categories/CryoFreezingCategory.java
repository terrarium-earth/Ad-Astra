package earth.terrarium.adastra.common.compat.jei.categories;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.machines.CryoFreezerScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.compat.jei.drawables.EnergyBarDrawable;
import earth.terrarium.adastra.common.compat.jei.drawables.EtaDrawable;
import earth.terrarium.adastra.common.compat.jei.drawables.FluidBarDrawable;
import earth.terrarium.adastra.common.config.MachineConfig;
import earth.terrarium.adastra.common.recipes.machines.CryoFreezingRecipe;
import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.adastra.common.registry.ModItems;
import earth.terrarium.botarium.common.fluid.FluidConstants;
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

public record CryoFreezingCategory(IGuiHelper guiHelper) implements IRecipeCategory<CryoFreezingRecipe> {
    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "cryo_freezing");
    public static final RecipeType<CryoFreezingRecipe> RECIPE = new RecipeType<>(ID, CryoFreezingRecipe.class);

    @Override
    public RecipeType<CryoFreezingRecipe> getRecipeType() {
        return RECIPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(ModBlocks.CRYO_FREEZER.get().getDescriptionId());
    }

    @Override
    public IDrawable getBackground() {
        return guiHelper.createBlankDrawable(180, 100);
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableItemStack(ModItems.CRYO_FREEZER.get().getDefaultInstance());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CryoFreezingRecipe recipe, IFocusGroup focuses) {
        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addIngredients(Ingredient.of(ModItems.CRYO_FREEZER.get()));
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 66).addIngredients(recipe.input());
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 114, 38);
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 114, 66);
    }

    @Override
    public void draw(CryoFreezingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        graphics.blit(CryoFreezerScreen.TEXTURE, 1, -4, 0, 0, 177, 96, 177, 181);
        graphics.blit(CryoFreezerScreen.TEXTURE, 1, 92, 0, 174, 177, 7, 177, 181);

        new EnergyBarDrawable(mouseX, mouseY, -recipe.energy(), MachineConfig.ostrumTierEnergyCapacity, MachineConfig.ostrumTierMaxEnergyInOut, 0).draw(graphics, 144, 55);

        new EtaDrawable(mouseX, mouseY, recipe.cookingTime(), GuiUtils.SNOWFLAKE, 13, 13).draw(graphics, 55, 67);
        int cookTime = recipe.cookingTime();
        long capacity = FluidConstants.fromMillibuckets(MachineConfig.ostrumTierFluidCapacity);
        new FluidBarDrawable(mouseX, mouseY, true, capacity, cookTime, recipe.result()).draw(graphics, 81, 65);
    }
}
