package earth.terrarium.adastra.client.renderers.items.armor;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.renderers.items.base.CustomGeoArmorRenderer;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class AerolyteSpaceSuitRenderer extends CustomGeoArmorRenderer {
    public static final ResourceLocation MODEL = new ResourceLocation(AdAstra.MOD_ID, "armor/aerolyte_space_suit");

    public AerolyteSpaceSuitRenderer() {
        super(new DefaultedItemGeoModel<>(MODEL));
    }
}
