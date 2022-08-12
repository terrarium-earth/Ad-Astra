package com.github.alexnijjar.ad_astra.registry;

import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import net.minecraft.entity.decoration.painting.PaintingMotive;
import net.minecraft.util.registry.Registry;

public class ModPaintings {

	public static void register() {
		register("mercury", 16, 16);
		register("moon", 16, 16);
		register("pluto", 16, 16);
		register("earth", 32, 32);
		register("glacio", 32, 32);
		register("mars", 32, 32);
		register("venus", 32, 32);
		register("jupiter", 48, 48);
		register("neptune", 48, 48);
		register("uranus", 48, 48);
		register("saturn", 64, 48);
		register("the_milky_way", 64, 48);
		register("alpha_centaury_c", 64, 64);
		register("sun", 80, 80);
	}

	private static PaintingMotive register(String name, int width, int height) {
		return Registry.register(Registry.PAINTING_MOTIVE, new ModIdentifier(name), new PaintingMotive(width, height));
	}
}