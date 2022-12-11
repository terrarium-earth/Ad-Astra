package earth.terrarium.ad_astra.common.compat.rei.util;

import earth.terrarium.ad_astra.AdAstra;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class REIUtils {
	public static final ResourceLocation ENERGY_EMPTY_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/energy_bar_empty.png");
	public static final ResourceLocation FLUID_TANK_BACK_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/fluid_tank_back.png");
}