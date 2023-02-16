package earth.terrarium.ad_astra.client.renderer.block.machines;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.machine.entity.EtrionicPressBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class EtrionicPressBlockRenderer extends GeoBlockRenderer<EtrionicPressBlockEntity> {
    public EtrionicPressBlockRenderer() {
        super(new DefaultedBlockGeoModel<>(new ResourceLocation(AdAstra.MOD_ID, "machines/etrionic_press")));
    }
}
