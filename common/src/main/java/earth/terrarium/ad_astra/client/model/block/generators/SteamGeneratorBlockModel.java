package earth.terrarium.ad_astra.client.model.block.generators;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.machine.entity.SteamGeneratorBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class SteamGeneratorBlockModel extends DefaultedBlockGeoModel<SteamGeneratorBlockEntity> {
    /**
     * Create a new instance of this model class.<br>
     * The asset path should be the truncated relative path from the base folder.<br>
     * E.G.
     * <pre>{@code
     * 	new ResourceLocation("myMod", "workbench/sawmill")
     * }</pre>
     */
    public SteamGeneratorBlockModel() {
        super(new ResourceLocation(AdAstra.MOD_ID, "generators/steam_generator"));
    }

    @Override
    public ResourceLocation getTextureResource(SteamGeneratorBlockEntity animatable) {
        return buildFormattedTexturePath(new ResourceLocation(AdAstra.MOD_ID, "generators/steam_generator" + (animatable.isLit() ? "_on" : "")));
    }
}
