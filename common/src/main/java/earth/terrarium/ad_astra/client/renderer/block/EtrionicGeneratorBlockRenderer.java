package earth.terrarium.ad_astra.client.renderer.block;

import earth.terrarium.ad_astra.client.model.block.EtrionicGeneratorBlockModel;
import earth.terrarium.ad_astra.common.block.machine.entity.EtrionicGeneratorBlockEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class EtrionicGeneratorBlockRenderer extends GeoBlockRenderer<EtrionicGeneratorBlockEntity> {
    public EtrionicGeneratorBlockRenderer() {
        super(new EtrionicGeneratorBlockModel());
    }
}
