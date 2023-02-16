package earth.terrarium.ad_astra.client.model.block.generators;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.machine.entity.SolarPanelBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class SolarPanelBlockModel extends DefaultedBlockGeoModel<SolarPanelBlockEntity> {
    /**
     * Create a new instance of this model class.<br>
     * The asset path should be the truncated relative path from the base folder.<br>
     * E.G.
     * <pre>{@code
     * 	new ResourceLocation("myMod", "workbench/sawmill")
     * }</pre>
     *
     * @param assetSubpath
     */
    public SolarPanelBlockModel() {
        super(new ResourceLocation(AdAstra.MOD_ID, "generators/solar_panel"));
    }

    @Override
    public ResourceLocation getTextureResource(SolarPanelBlockEntity animatable) {
        return buildFormattedTexturePath(new ResourceLocation(AdAstra.MOD_ID, "generators/" + BuiltInRegistries.BLOCK.getKey(animatable.getBlockState().getBlock()).getPath()));
    }
}
