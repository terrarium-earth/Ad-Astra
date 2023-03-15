package earth.terrarium.ad_astra.common.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.screen.GuiUtil;
import earth.terrarium.ad_astra.common.compat.jei.EnergyBarDrawable;
import earth.terrarium.ad_astra.common.config.CompressorConfig;
import earth.terrarium.ad_astra.common.recipe.CompressingRecipe;
import earth.terrarium.ad_astra.common.registry.ModItems;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class CompressorCategory extends BaseCategory<CompressingRecipe> {
    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "compressing");
    public static final RecipeType<CompressingRecipe> RECIPE = new RecipeType<>(ID, CompressingRecipe.class);
    private final IDrawable slot;
    private final IDrawable hammer;

    public CompressorCategory(IGuiHelper guiHelper) {
        super(guiHelper,
                RECIPE,
                Component.translatable(ModItems.COMPRESSOR.get().getDescriptionId()),
                guiHelper.createBlankDrawable(144, 90),
                guiHelper.createDrawableItemStack(ModItems.COMPRESSOR.get().getDefaultInstance())
        );
        slot = guiHelper.getSlotDrawable();
        hammer = guiHelper.drawableBuilder(GuiUtil.HAMMER_TEXTURE, 0, 0, GuiUtil.HAMMER_WIDTH, GuiUtil.HAMMER_HEIGHT).setTextureSize(GuiUtil.HAMMER_WIDTH, GuiUtil.HAMMER_HEIGHT).buildAnimated(100, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CompressingRecipe recipe, IFocusGroup focuses) {
        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addIngredients(Ingredient.of(ModItems.COMPRESSOR.get()));
        builder.addSlot(RecipeIngredientRole.INPUT, 10 + 1, 35 + 1).addIngredients(recipe.getInputIngredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 70 + 1, 35 + 1).addIngredients(Ingredient.of(recipe.getOutput()));
    }

    @Override
    public void draw(CompressingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        slot.draw(poseStack, 10, 35);
        slot.draw(poseStack, 70, 35);
        hammer.draw(poseStack, 40, 35);
        new EnergyBarDrawable(false, 10000).draw(poseStack, 120, 15);
    }

    @Override
    public List<Component> getTooltipStrings(CompressingRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        List<Component> tooltips = new ArrayList<>();
        if (mouseX > 118 && mouseY > 13 && mouseX < 118 + GuiUtil.ENERGY_WIDTH && mouseY < 13 + GuiUtil.ENERGY_HEIGHT) {
            tooltips.add(Component.translatable("rei.tooltip.ad_astra.energy_using", CompressorConfig.energyPerTick));
        }
        return tooltips;
    }
}
