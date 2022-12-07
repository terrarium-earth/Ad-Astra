package earth.terrarium.ad_astra.compat.emi.recipes;

import dev.emi.emi.EmiPort;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.stack.FluidEmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import earth.terrarium.ad_astra.compat.emi.EmiCategories;
import earth.terrarium.ad_astra.compat.emi.EmiTextures;
import earth.terrarium.ad_astra.config.FuelRefineryConfig;
import earth.terrarium.ad_astra.recipe.OxygenConversionRecipe;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.material.Fluid;

import java.util.List;
import java.util.stream.Collectors;

public class EmiOxygenConversionRecipe implements EmiRecipe {
	private final ResourceLocation id;
	OxygenConversionRecipe recipe;
	private final EmiStack input;
	private final EmiStack output;

	public EmiOxygenConversionRecipe(OxygenConversionRecipe recipe) {
		this.id = recipe.getId();
		this.recipe = recipe;
		this.input = new FluidEmiStack(recipe.getFluidInput().stream().map(Holder::value).filter(f -> f.isSource(f.defaultFluidState())).map(FluidVariant::of).collect(Collectors.toList()).get(0), FluidConstants.BUCKET);
		this.output = EmiStack.of(recipe.getFluidOutput());
	}

	@Override
	public EmiRecipeCategory getCategory() {
		return EmiCategories.OXYGEN_CONVERSION_CATEGORY;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public List<EmiIngredient> getInputs() {
		return List.of(this.input);
	}

	@Override
	public List<EmiStack> getOutputs() {
		return List.of(this.output);
	}

	@Override
	public int getDisplayWidth() {
		return 144;
	}

	@Override
	public int getDisplayHeight() {
		return 90;
	}

	@Override
	public void addWidgets(WidgetHolder widgets) {
		int xOffset = 20;
		int yOffset = 30;

		widgets.addFillingArrow(26 + xOffset, 1 + yOffset, 20 * 50);

		EmiTextures.createFluidWidget(widgets, -10 + xOffset, -17 + yOffset, FluidVariant.of((Fluid) this.input.getKey()), 150 * 50, false);
		EmiTextures.createFluidWidget(widgets, 60 + xOffset, -17 + yOffset, FluidVariant.of((Fluid) this.output.getKey()), 150 * 50, true);

		EmiTextures.createEnergyWidget(widgets, 100 + xOffset, -20 + yOffset, (int) FuelRefineryConfig.energyPerTick, false);

		FormattedCharSequence ratioText = EmiPort.ordered(EmiPort.translatable("rei.text.ad_astra.conversion_ratio", this.recipe.getConversionRatio() * 100.0));
		widgets.addText(ratioText, -17 + xOffset, 50 + yOffset, 0xFF404040, false);
	}
}
