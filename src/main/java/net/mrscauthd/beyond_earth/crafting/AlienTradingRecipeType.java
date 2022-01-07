package net.mrscauthd.beyond_earth.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlienTradingRecipeType<T extends AlienTradingRecipe> extends BeyondEarthRecipeType<T> {

	private static final List<AlienTradingRecipeType<?>> TYPES = new ArrayList<>();

	public static List<AlienTradingRecipeType<?>> getTypes() {
		return Collections.unmodifiableList(TYPES);
	}

	public AlienTradingRecipeType(String name) {
		super(name);
		TYPES.add(this);
	}

}
