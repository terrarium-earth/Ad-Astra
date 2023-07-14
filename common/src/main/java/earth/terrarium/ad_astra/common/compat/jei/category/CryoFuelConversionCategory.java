package earth.terrarium.ad_astra.common.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.ClientPlatformUtils;
import earth.terrarium.ad_astra.client.screen.GuiUtil;
import earth.terrarium.ad_astra.common.registry.ModItems;
import earth.terrarium.ad_astra.common.compat.jei.EnergyBarDrawable;
import earth.terrarium.ad_astra.common.compat.jei.FluidBarDrawable;
import earth.terrarium.ad_astra.common.config.CryoFreezerConfig;
import earth.terrarium.ad_astra.common.recipe.CryoFuelConversionRecipe;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class CryoFuelConversionCategory extends BaseCategory<CryoFuelConversionRecipe> {
    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "cryo_fuel_conversion");
    public static final RecipeType<CryoFuelConversionRecipe> RECIPE = new RecipeType<>(ID, CryoFuelConversionRecipe.class);
    private final IDrawable slot;
    private final IDrawableAnimated arrow;

    public CryoFuelConversionCategory(IGuiHelper guiHelper) {
        super(guiHelper,
                RECIPE,
                Component.translatable(ModItems.CRYO_FREEZER.get().getDescriptionId()),
                guiHelper.createBlankDrawable(144, 90),
                guiHelper.createDrawableItemStack(ModItems.CRYO_FREEZER.get().getDefaultInstance())
        );
        slot = guiHelper.getSlotDrawable();
        arrow = guiHelper.drawableBuilder(GuiUtil.ARROW_TEXTURE, 0, 0, GuiUtil.ARROW_WIDTH, GuiUtil.ARROW_HEIGHT).setTextureSize(GuiUtil.ARROW_WIDTH, GuiUtil.ARROW_HEIGHT).buildAnimated(100, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CryoFuelConversionRecipe recipe, IFocusGroup focuses) {
        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addIngredients(Ingredient.of(ModItems.CRYO_FREEZER.get()));
        builder.addSlot(RecipeIngredientRole.INPUT, 10 + 1, 30 + 1).addIngredients(recipe.getIngredients().get(0));
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addFluidStack(recipe.getFluidOutput(), FluidHooks.buckets(1));
    }

    @Override
    public void draw(CryoFuelConversionRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        slot.draw(poseStack, 10, 30);
        new FluidBarDrawable(FluidHooks.newFluidHolder(recipe.getFluidOutput(), FluidHooks.buckets(1), null), true, 10000).draw(poseStack, 80, 15);
        new EnergyBarDrawable(false, 10000).draw(poseStack, 120, 15);
        arrow.draw(poseStack, 35, 30);
        Component ratioText = Component.translatable("rei.text.ad_astra.amount", 1000 * recipe.getConversionRatio());
        Font font = Minecraft.getInstance().font;
        font.draw(poseStack, ratioText, 70 - font.width(ratioText) / 2f, 80, 0x404040);

    }

    @Override
    public List<Component> getTooltipStrings(CryoFuelConversionRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        List<Component> tooltips = new ArrayList<>();
        if (mouseX > 118 && mouseY > 13 && mouseX < 118 + GuiUtil.ENERGY_WIDTH && mouseY < 13 + GuiUtil.ENERGY_HEIGHT) {
            tooltips.add(Component.translatable("rei.tooltip.ad_astra.energy_using", CryoFreezerConfig.energyPerTick));
        }
        if (mouseX > 80 && mouseY > 13 && mouseX < 80 + GuiUtil.FLUID_TANK_WIDTH && mouseY < 13 + GuiUtil.FLUID_TANK_HEIGHT) {
            tooltips.add(ClientPlatformUtils.getFluidTranslation(recipe.getFluidOutput()));
        }
        return tooltips;
    }
}
