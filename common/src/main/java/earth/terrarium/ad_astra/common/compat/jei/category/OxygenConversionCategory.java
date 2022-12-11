package earth.terrarium.ad_astra.common.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.screen.GuiUtil;
import earth.terrarium.ad_astra.common.registry.ModItems;
import earth.terrarium.ad_astra.common.compat.jei.EnergyBarDrawable;
import earth.terrarium.ad_astra.common.compat.jei.FluidBarDrawable;
import earth.terrarium.ad_astra.common.config.OxygenLoaderConfig;
import earth.terrarium.ad_astra.common.recipe.OxygenConversionRecipe;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
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

public class OxygenConversionCategory extends BaseCategory<OxygenConversionRecipe> {
    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "oxygen_conversion");
    public static final RecipeType<OxygenConversionRecipe> RECIPE = new RecipeType<>(ID, OxygenConversionRecipe.class);
    private final IDrawableAnimated arrow;

    public OxygenConversionCategory(IGuiHelper guiHelper) {
        super(guiHelper,
                RECIPE,
                Component.translatable(ModItems.OXYGEN_LOADER.get().getDescriptionId()),
                guiHelper.createBlankDrawable(144, 90),
                guiHelper.createDrawableItemStack(ModItems.OXYGEN_LOADER.get().getDefaultInstance())
        );
        arrow = guiHelper.drawableBuilder(GuiUtil.ARROW_TEXTURE, 0, 0, GuiUtil.ARROW_WIDTH, GuiUtil.ARROW_HEIGHT).setTextureSize(GuiUtil.ARROW_WIDTH, GuiUtil.ARROW_HEIGHT).buildAnimated(100, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, OxygenConversionRecipe recipe, IFocusGroup focuses) {
        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addIngredients(Ingredient.of(ModItems.OXYGEN_LOADER.get()));
        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addFluidStack(recipe.getFluidInput().get(0).value(), FluidHooks.buckets(1));
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addFluidStack(recipe.getFluidOutput(), FluidHooks.buckets(1));
    }

    @Override
    public void draw(OxygenConversionRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        new FluidBarDrawable(FluidHooks.newFluidHolder(recipe.getFluidInput().get(0).value(), FluidHooks.buckets(1), null), false, 10000).draw(poseStack, 15, 15);
        new FluidBarDrawable(FluidHooks.newFluidHolder(recipe.getFluidOutput(), FluidHooks.buckets(1), null), true, 10000).draw(poseStack, 80, 15);
        guiHelper.createDrawable(GuiUtil.HAMMER_TEXTURE, 13, 13, 13, 13);
        new EnergyBarDrawable(false, 10000).draw(poseStack, 120, 15);
        arrow.draw(poseStack, 40, 30);
    }

    @Override
    public List<Component> getTooltipStrings(OxygenConversionRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        List<Component> tooltips = new ArrayList<>();
        if (mouseX > 118 && mouseY > 13 && mouseX < 118 + GuiUtil.ENERGY_WIDTH && mouseY < 13 + GuiUtil.ENERGY_HEIGHT) {
            tooltips.add(Component.translatable("rei.tooltip.ad_astra.energy_using", OxygenLoaderConfig.energyPerTick));
        }
        if (mouseX > 15 && mouseY > 13 && mouseX < 15 + GuiUtil.FLUID_TANK_WIDTH && mouseY < 13 + GuiUtil.FLUID_TANK_HEIGHT) {
            tooltips.add(GuiUtil.getFluidTranslation(recipe.getFluidInput().get(0).value()));
        }
        if (mouseX > 80 && mouseY > 13 && mouseX < 80 + GuiUtil.FLUID_TANK_WIDTH && mouseY < 13 + GuiUtil.FLUID_TANK_HEIGHT) {
            tooltips.add(GuiUtil.getFluidTranslation(recipe.getFluidOutput()));
        }
        return tooltips;
    }
}
