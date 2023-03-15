package earth.terrarium.ad_astra.common.compat.rei.space_station;

import earth.terrarium.ad_astra.common.compat.rei.REICategories;
import earth.terrarium.ad_astra.common.recipe.SpaceStationRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.item.ItemStack;

import java.util.List;

@Environment(EnvType.CLIENT)
public record SpaceStationDisplay(SpaceStationRecipe recipe) implements Display {

	@Override
	public List<EntryIngredient> getInputEntries() {
		return EntryIngredients.ofIngredients(recipe.getIngredients());
	}

	@Override
	public List<EntryIngredient> getOutputEntries() {
		return List.of(EntryIngredients.of(ItemStack.EMPTY));
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return REICategories.SPACE_STATION_CATEGORY;
	}
}