package earth.terrarium.ad_astra.client.renderer.entity.vehicle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.rover.RoverModel;
import earth.terrarium.ad_astra.common.entity.vehicle.Vehicle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public abstract class VehicleRenderer<T extends Vehicle, M extends EntityModel<T>> extends EntityRenderer<T> implements RenderLayerParent<T, M> {

    protected final M model;

    protected VehicleRenderer(EntityRendererProvider.Context context, M model, float shadowRadius) {
        super(context);
        this.model = model;
        this.shadowRadius = shadowRadius;
    }

    public static void renderItem(EntityModel<?> model, ResourceLocation texture, ModelLayerLocation layer, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {

        poseStack.pushPose();

        poseStack.mulPose(Axis.ZP.rotationDegrees(180));
        poseStack.translate(0.0, -1.501, 0.0);

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCullZOffset(texture));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0f, 1.0f, 1.0f, 1.0f);

        poseStack.popPose();
    }

    public static void renderRocketItem(ResourceLocation texture, ModelLayerLocation layer, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        EntityModel<?> model = new VehicleModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(layer), "rocket");
        renderItem(model, texture, layer, poseStack, buffer, packedLight, packedOverlay);
    }

    public static void renderRoverItem(ResourceLocation texture, ModelLayerLocation layer, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        EntityModel<?> model = new RoverModel(Minecraft.getInstance().getEntityModels().bakeLayer(layer));
        renderItem(model, texture, layer, poseStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public M getModel() {
        return this.model;
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        poseStack.mulPose(Axis.ZP.rotationDegrees(180));
        poseStack.mulPose(Axis.YP.rotationDegrees(this.getYawOffset()));
        poseStack.translate(0.0, -1.501, 0.0);

        if (entity.isFullyFrozen()) {
            this.shakeVehicle(entity, tickDelta, poseStack);
        }

        this.model.setupAnim(entity, tickDelta, 0.0f, -0.1f, 0.0f, 0.0f);
        VertexConsumer vertexConsumer = buffer.getBuffer(this.model.renderType(getTextureLocation(entity)));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);

        poseStack.popPose();

        super.render(entity, yaw, tickDelta, poseStack, buffer, packedLight);
    }

    private void shakeVehicle(T entity, float tickDelta, PoseStack poseStack) {
        if (!Minecraft.getInstance().isPaused()) {
            double shakeDirection1 = (tickDelta * (entity.level().random.nextBoolean() ? 1 : -1)) / 150;
            double shakeDirection2 = (tickDelta * (entity.level().random.nextBoolean() ? 1 : -1)) / 150;
            double shakeDirection3 = (tickDelta * (entity.level().random.nextBoolean() ? 1 : -1)) / 150;
            poseStack.translate(shakeDirection1, shakeDirection2, shakeDirection3);
        }
    }

    public int getYawOffset() {
        return 180;
    }
}
