package earth.terrarium.ad_astra.client.renderer.block.generators;

import earth.terrarium.ad_astra.client.model.block.generators.SteamGeneratorBlockModel;
import earth.terrarium.ad_astra.common.block.machine.entity.SteamGeneratorBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class SteamGeneratorBlockRenderer extends GeoBlockRenderer<SteamGeneratorBlockEntity> {
    public SteamGeneratorBlockRenderer() {
        super(new SteamGeneratorBlockModel());
    }
}
