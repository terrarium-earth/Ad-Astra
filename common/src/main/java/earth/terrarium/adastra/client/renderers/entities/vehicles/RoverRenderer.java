package earth.terrarium.adastra.client.renderers.entities.vehicles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.models.entities.vehicles.RoverModel;
import earth.terrarium.adastra.common.entities.vehicles.Rover;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class RoverRenderer extends EntityRenderer<Rover> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/rover/tier_1_rover.png");

    protected final EntityModel<Rover> model;

    public RoverRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 1.0f;
        this.model = new RoverModel(context.bakeLayer(RoverModel.LAYER));
    }

    @Override
    public void render(Rover entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
        try (var pose = new CloseablePoseStack(poseStack)) {
            pose.translate(0.0F, 1.55F, 0.0F);
            pose.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));
            float xRot = Mth.lerp(partialTick, entity.xRotO, entity.getXRot());
            pose.mulPose(Axis.ZP.rotationDegrees(-xRot));
            pose.scale(-1.0F, -1.0F, 1.0F);
            model.setupAnim(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            VertexConsumer consumer = buffer.getBuffer(model.renderType(getTextureLocation(entity)));
            model.renderToBuffer(pose, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(Rover entity) {
        return TEXTURE;
    }

    public static class ItemRenderer extends BlockEntityWithoutLevelRenderer {
        private EntityModel<?> model;

        public ItemRenderer() {
            super(Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                Minecraft.getInstance().getEntityModels());
        }

        @Override
        public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
            if (model == null) {
                model = new RoverModel(Minecraft.getInstance().getEntityModels().bakeLayer(RoverModel.LAYER));
            }
            var consumer = buffer.getBuffer(RenderType.entityCutoutNoCullZOffset(TEXTURE));
            try (var pose = new CloseablePoseStack(poseStack)) {
                pose.mulPose(Axis.ZP.rotationDegrees(180));
                pose.translate(0.0, -1.501, 0.0);
                model.renderToBuffer(pose, consumer, packedLight, packedOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }
}
