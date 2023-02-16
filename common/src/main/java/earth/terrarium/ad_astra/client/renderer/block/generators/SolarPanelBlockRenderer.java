package earth.terrarium.ad_astra.client.renderer.block.generators;

import earth.terrarium.ad_astra.client.model.block.generators.SolarPanelBlockModel;
import earth.terrarium.ad_astra.common.block.machine.entity.SolarPanelBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class SolarPanelBlockRenderer extends GeoBlockRenderer<SolarPanelBlockEntity> {
    public SolarPanelBlockRenderer() {
        super(new SolarPanelBlockModel());
    }
}
