package earth.terrarium.ad_astra.client.renderer.block.machines;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.machine.entity.EtrionicPressBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class EtrionicPressBlockRenderer extends GeoBlockRenderer<EtrionicPressBlockEntity> {
    private final ItemRenderer itemRenderer;

    public EtrionicPressBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new DefaultedBlockGeoModel<>(new ResourceLocation(AdAstra.MOD_ID, "machines/etrionic_press")));
        itemRenderer = context.getItemRenderer();
    }

    @Override
    public void postRender(PoseStack matrixStack, EtrionicPressBlockEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.postRender(matrixStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        matrixStack.pushPose();
        matrixStack.translate(0.5D, 0.47D, 0.5D);
        matrixStack.scale(0.55f, 0.55f, 0.55f);
        matrixStack.mulPose(Axis.XP.rotationDegrees(90));
        itemRenderer.renderStatic(animatable.getItem(0), ItemTransforms.TransformType.FIXED, packedLight, packedOverlay, matrixStack, bufferSource, 0);
        matrixStack.popPose();
    }
}
