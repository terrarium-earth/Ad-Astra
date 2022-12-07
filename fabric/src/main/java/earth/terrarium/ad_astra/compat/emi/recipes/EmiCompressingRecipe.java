package earth.terrarium.ad_astra.compat.emi.recipes;

import dev.emi.emi.EmiPort;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import earth.terrarium.ad_astra.compat.emi.EmiCategories;
import earth.terrarium.ad_astra.compat.emi.EmiTextures;
import earth.terrarium.ad_astra.config.CompressorConfig;
import earth.terrarium.ad_astra.recipe.CompressingRecipe;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class EmiCompressingRecipe implements EmiRecipe {
	private final ResourceLocation id;
	CompressingRecipe recipe;
	private final EmiIngredient input;
	private final EmiStack output;

	public EmiCompressingRecipe(CompressingRecipe recipe) {
		this.id = recipe.getId();
		this.recipe = recipe;
		this.input = EmiIngredient.of(recipe.getIngredients().get(0));
		this.output = EmiStack.of(recipe.getResultItem());
	}

	@Override
	public EmiRecipeCategory getCategory() {
		return EmiCategories.COMPRESSOR_CATEGORY;
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

		widgets.addFillingArrow(26 + xOffset, 1 + yOffset, 50 * recipe.getCookTime()).tooltip((mx, my) -> List.of(ClientTooltipComponent.create(EmiPort.ordered(EmiPort.translatable("emi.cooking.time", recipe.getCookTime() / 20f)))));

		widgets.addSlot(this.input, xOffset, yOffset);
		widgets.addSlot(this.output, 58 + xOffset, yOffset).recipeContext(this);

		EmiTextures.createEnergyWidget(widgets, 100 + xOffset, -20 + yOffset, (int) CompressorConfig.energyPerTick, false);
	}
}
