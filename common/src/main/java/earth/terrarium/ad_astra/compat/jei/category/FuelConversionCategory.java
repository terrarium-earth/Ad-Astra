package earth.terrarium.ad_astra.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.screen.GuiUtil;
import earth.terrarium.ad_astra.compat.jei.EnergyBarDrawable;
import earth.terrarium.ad_astra.compat.jei.FluidBarDrawable;
import earth.terrarium.ad_astra.config.FuelRefineryConfig;
import earth.terrarium.ad_astra.recipe.FuelConversionRecipe;
import earth.terrarium.ad_astra.registry.ModItems;
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

public class FuelConversionCategory extends BaseCategory<FuelConversionRecipe> {
    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "fuel_conversion");
    public static final RecipeType<FuelConversionRecipe> RECIPE = new RecipeType<>(ID, FuelConversionRecipe.class);
    private final IDrawableAnimated arrow;

    public FuelConversionCategory(IGuiHelper guiHelper) {
        super(guiHelper,
                RECIPE,
                Component.translatable(ModItems.FUEL_REFINERY.get().getDescriptionId()),
                guiHelper.createBlankDrawable(144, 90),
                guiHelper.createDrawableItemStack(ModItems.FUEL_REFINERY.get().getDefaultInstance())
        );
        arrow = guiHelper.drawableBuilder(GuiUtil.ARROW_TEXTURE, 0, 0, GuiUtil.ARROW_WIDTH, GuiUtil.ARROW_HEIGHT).setTextureSize(GuiUtil.ARROW_WIDTH, GuiUtil.ARROW_HEIGHT).buildAnimated(100, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FuelConversionRecipe recipe, IFocusGroup focuses) {
        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addIngredients(Ingredient.of(ModItems.FUEL_REFINERY.get()));
        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addFluidStack(recipe.getFluidInput().get(0).value(), FluidHooks.buckets(1));
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addFluidStack(recipe.getFluidOutput(), FluidHooks.buckets(1));
    }

    @Override
    public void draw(FuelConversionRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        new FluidBarDrawable(FluidHooks.newFluidHolder(recipe.getFluidInput().get(0).value(), FluidHooks.buckets(1), null), false, 5000).draw(poseStack, 15, 15);
        new FluidBarDrawable(FluidHooks.newFluidHolder(recipe.getFluidOutput(), FluidHooks.buckets(1), null), true, 5000).draw(poseStack, 80, 15);
        new EnergyBarDrawable(false, 10000).draw(poseStack, 120, 15);
        arrow.draw(poseStack, 40, 30);
    }

    @Override
    public List<Component> getTooltipStrings(FuelConversionRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        List<Component> tooltips = new ArrayList<>();
        if (mouseX > 118 && mouseY > 13 && mouseX < 118 + GuiUtil.ENERGY_WIDTH && mouseY < 13 + GuiUtil.ENERGY_HEIGHT) {
            tooltips.add(Component.translatable("rei.tooltip.ad_astra.energy_using", FuelRefineryConfig.energyPerTick));
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
