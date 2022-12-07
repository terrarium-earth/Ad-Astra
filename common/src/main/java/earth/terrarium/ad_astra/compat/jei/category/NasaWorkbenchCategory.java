package earth.terrarium.ad_astra.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.screen.GuiUtil;
import earth.terrarium.ad_astra.recipe.NasaWorkbenchRecipe;
import earth.terrarium.ad_astra.registry.ModItems;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

public class NasaWorkbenchCategory extends BaseCategory<NasaWorkbenchRecipe> {
    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "nasa_workbench");
    public static final RecipeType<NasaWorkbenchRecipe> RECIPE = new RecipeType<>(ID, NasaWorkbenchRecipe.class);
    private final IDrawable slot;
    private final IDrawable arrow;

    public NasaWorkbenchCategory(IGuiHelper guiHelper) {
        super(guiHelper,
                RECIPE,
                Component.translatable(ModItems.NASA_WORKBENCH.get().getDescriptionId()),
                guiHelper.createBlankDrawable(144, 110),
                guiHelper.createDrawableItemStack(ModItems.NASA_WORKBENCH.get().getDefaultInstance())
        );
        slot = guiHelper.getSlotDrawable();
        arrow = guiHelper.drawableBuilder(GuiUtil.ARROW_TEXTURE, 0, 0, GuiUtil.ARROW_WIDTH, GuiUtil.ARROW_HEIGHT).setTextureSize(GuiUtil.ARROW_WIDTH, GuiUtil.ARROW_HEIGHT).build();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, NasaWorkbenchRecipe recipe, IFocusGroup focuses) {
        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addIngredients(Ingredient.of(ModItems.NASA_WORKBENCH.get()));
        builder.addSlot(RecipeIngredientRole.INPUT, 40 + 1, 0 + 1).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 31 + 1, 18 + 1).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 49 + 1, 18 + 1).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 31 + 1, 18 * 2 + 1).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.INPUT, 49 + 1, 18 * 2 + 1).addIngredients(recipe.getIngredients().get(4));
        builder.addSlot(RecipeIngredientRole.INPUT, 31 + 1, 18 * 3 + 1).addIngredients(recipe.getIngredients().get(5));
        builder.addSlot(RecipeIngredientRole.INPUT, 49 + 1, 18 * 3 + 1).addIngredients(recipe.getIngredients().get(6));
        builder.addSlot(RecipeIngredientRole.INPUT, 31 + 1 - 18, 18 * 4 + 1).addIngredients(recipe.getIngredients().get(7));
        builder.addSlot(RecipeIngredientRole.INPUT, 31 + 1, 18 * 4 + 1).addIngredients(recipe.getIngredients().get(8));
        builder.addSlot(RecipeIngredientRole.INPUT, 49 + 1, 18 * 4 + 1).addIngredients(recipe.getIngredients().get(9));
        builder.addSlot(RecipeIngredientRole.INPUT, 49 + 1 + 18, 18 * 4 + 1).addIngredients(recipe.getIngredients().get(10));
        builder.addSlot(RecipeIngredientRole.INPUT, 31 + 1 - 18, 18 * 5 + 1).addIngredients(recipe.getIngredients().get(11));
        builder.addSlot(RecipeIngredientRole.INPUT, 40 + 1, 18 * 5 + 1).addIngredients(recipe.getIngredients().get(12));
        builder.addSlot(RecipeIngredientRole.INPUT, 49 + 1 + 18, 18 * 5 + 1).addIngredients(recipe.getIngredients().get(13));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 123 + 1, 90 + 1).addIngredients(Ingredient.of(recipe.getResultItem()));
    }

    @Override
    public void draw(NasaWorkbenchRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        slot.draw(poseStack, 40, 0);
        slot.draw(poseStack, 31, 18);
        slot.draw(poseStack, 49, 18);
        slot.draw(poseStack, 31, 18 * 2);
        slot.draw(poseStack, 49, 18 * 2);
        slot.draw(poseStack, 31, 18 * 3);
        slot.draw(poseStack, 49, 18 * 3);
        slot.draw(poseStack, 31 - 18, 18 * 4);
        slot.draw(poseStack, 31, 18 * 4);
        slot.draw(poseStack, 49, 18 * 4);
        slot.draw(poseStack, 49 + 18, 18 * 4);
        slot.draw(poseStack, 31 - 18, 18 * 5);
        slot.draw(poseStack, 40, 18 * 5);
        slot.draw(poseStack, 49 + 18, 18 * 5);
        arrow.draw(poseStack, 90, 90);
        slot.draw(poseStack, 123, 90);
    }
}
