package earth.terrarium.ad_astra.client.renderer.entity.vehicles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.rover.RoverEntityModel;
import earth.terrarium.ad_astra.entities.vehicles.VehicleEntity;
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
public abstract class VehicleEntityRenderer<T extends VehicleEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements RenderLayerParent<T, M> {

    protected final M model;

    protected VehicleEntityRenderer(EntityRendererProvider.Context context, M model, float shadowRadius) {
        super(context);
        this.model = model;
        this.shadowRadius = shadowRadius;
    }

    public static void renderItem(EntityModel<?> model, ResourceLocation texture, ModelLayerLocation layer, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {

        matrices.pushPose();

        matrices.mulPose(Vector3f.ZP.rotationDegrees(180));
        matrices.translate(0.0, -1.501, 0.0);

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderType.entityCutoutNoCullZOffset(texture));
        model.renderToBuffer(matrices, vertexConsumer, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);

        matrices.popPose();
    }

    public static void renderRocketItem(ResourceLocation texture, ModelLayerLocation layer, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        EntityModel<?> model = new VehicleEntityModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(layer), "rocket");
        renderItem(model, texture, layer, matrices, vertexConsumers, light, overlay);
    }

    public static void renderRoverItem(ResourceLocation texture, ModelLayerLocation layer, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        EntityModel<?> model = new RoverEntityModel(Minecraft.getInstance().getEntityModels().bakeLayer(layer));
        renderItem(model, texture, layer, matrices, vertexConsumers, light, overlay);
    }

    @Override
    public M getModel() {
        return this.model;
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        matrices.pushPose();

        matrices.mulPose(Vector3f.ZP.rotationDegrees(180));
        matrices.mulPose(Vector3f.YP.rotationDegrees(this.getYawOffset()));
        matrices.translate(0.0, -1.501, 0.0);

        if (entity.isFullyFrozen()) {
            this.shakeVehicle(entity, tickDelta, matrices);
        }

        this.model.setupAnim(entity, tickDelta, 0.0f, -0.1f, 0.0f, 0.0f);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.model.renderType(getTextureLocation(entity)));
        this.model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);

        matrices.popPose();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    private void shakeVehicle(T entity, float tickDelta, PoseStack matrices) {
        if (!Minecraft.getInstance().isPaused()) {
            double shakeDirection1 = (tickDelta * (entity.level.random.nextBoolean() ? 1 : -1)) / 150;
            double shakeDirection2 = (tickDelta * (entity.level.random.nextBoolean() ? 1 : -1)) / 150;
            double shakeDirection3 = (tickDelta * (entity.level.random.nextBoolean() ? 1 : -1)) / 150;
            matrices.translate(shakeDirection1, shakeDirection2, shakeDirection3);
        }
    }

    public int getYawOffset() {
        return 180;
    }
}
