package earth.terrarium.ad_astra.compat.rei.utils;

import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class REIUtils {
	public static final ResourceLocation ENERGY_EMPTY_TEXTURE = new ModResourceLocation("textures/gui/energy_bar_empty.png");
	public static final ResourceLocation FLUID_TANK_BACK_TEXTURE = new ModResourceLocation("textures/gui/fluid_tank_back.png");
}