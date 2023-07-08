package earth.terrarium.adastra.client.renderers.items.armor;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.items.armor.AerolyteSpaceSuitItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class AerolyteSpaceSuitRenderer extends GeoArmorRenderer<AerolyteSpaceSuitItem> {
    public static final ResourceLocation MODEL = new ResourceLocation(AdAstra.MOD_ID, "armor/aerolyte_space_suit");

    public AerolyteSpaceSuitRenderer() {
        super(new DefaultedItemGeoModel<>(MODEL));
    }

    @Override
    public RenderType getRenderType(AerolyteSpaceSuitItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
