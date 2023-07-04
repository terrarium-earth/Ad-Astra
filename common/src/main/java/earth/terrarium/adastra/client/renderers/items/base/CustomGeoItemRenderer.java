package earth.terrarium.adastra.client.renderers.items.base;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import earth.terrarium.adastra.common.items.base.CustomGeoBlockItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class CustomGeoItemRenderer extends GeoItemRenderer<CustomGeoBlockItem> {
    public CustomGeoItemRenderer(GeoModel<CustomGeoBlockItem> model) {
        super(model);
    }

    @Override
    public void actuallyRender(PoseStack poseStack, CustomGeoBlockItem animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        try (var pose = new CloseablePoseStack(poseStack)) {
            pose.translate(0.0f, -0.5, 0.0f);
            super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        }
    }
}
