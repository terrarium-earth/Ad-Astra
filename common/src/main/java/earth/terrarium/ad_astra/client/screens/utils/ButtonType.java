package earth.terrarium.ad_astra.client.screens.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public enum ButtonType {
	LARGE(75, 20), NORMAL(71, 20), SMALL(37, 20), STEEL(71, 20);

	private final int width;
	private final int height;

	ButtonType(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
}
