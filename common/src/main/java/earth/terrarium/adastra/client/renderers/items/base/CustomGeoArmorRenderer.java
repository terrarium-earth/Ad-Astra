package earth.terrarium.adastra.client.renderers.items.base;

import earth.terrarium.adastra.common.items.base.CustomGeoArmorItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class CustomGeoArmorRenderer extends GeoArmorRenderer<CustomGeoArmorItem> {

    public CustomGeoArmorRenderer(GeoModel<CustomGeoArmorItem> model) {
        super(model);
    }

    @Override
    public RenderType getRenderType(CustomGeoArmorItem animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
