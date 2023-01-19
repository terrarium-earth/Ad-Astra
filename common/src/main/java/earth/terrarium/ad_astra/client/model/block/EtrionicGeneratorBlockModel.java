package earth.terrarium.ad_astra.client.model.block;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.machine.entity.EtrionicGeneratorBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class EtrionicGeneratorBlockModel extends DefaultedBlockGeoModel<EtrionicGeneratorBlockEntity> {
    /**
     * Create a new instance of this model class.<br>
     * The asset path should be the truncated relative path from the base folder.<br>
     * E.G.
     * <pre>{@code
     * 	new ResourceLocation("myMod", "workbench/sawmill")
     * }</pre>
     */
    public EtrionicGeneratorBlockModel() {
        super(new ResourceLocation(AdAstra.MOD_ID, "generators/etrionic_generator"));
    }
}
