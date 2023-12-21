package earth.terrarium.adastra.client.renderers.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import earth.terrarium.adastra.client.renderers.textures.FlagTexture;
import earth.terrarium.adastra.common.blockentities.FlagBlockEntity;
import earth.terrarium.adastra.common.blocks.FlagBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class FlagBlockEntityRenderer implements BlockEntityRenderer<FlagBlockEntity> {

    @Override
    public void render(FlagBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        var state = entity.getBlockState();
        var direction = state.getValue(FlagBlock.FACING);
        var minecraft = Minecraft.getInstance();

        try (var pose = new CloseablePoseStack(poseStack)) {
            pose.translate(0.5, 0.5, 0.5);
            pose.mulPose(Axis.YP.rotationDegrees(direction.asRotation()));
            pose.translate(-0.5, 0, -0.5);

            if (state.getValue(FlagBlock.HALF).equals(DoubleBlockHalf.LOWER)) {
                var model = minecraft.getBlockRenderer().getBlockModel(state);
                minecraft.getBlockRenderer().getModelRenderer().renderModel(poseStack.last(),
                    buffer.getBuffer(Sheets.cutoutBlockSheet()),
                    state,
                    model,
                    1f, 1f, 1f,
                    packedLight, packedOverlay);
            } else {
                String url = entity.getUrl();
                if (url == null) {
                    pose.translate(-0.75, 0.19, 0.495);
                    pose.scale(1, 1, 0.1f / 16f);
                    SkullModelBase model = new SkullModel(minecraft.getEntityModels().bakeLayer(ModelLayers.PLAYER_HEAD));
                    RenderType renderType = SkullBlockRenderer.getRenderType(SkullBlock.Types.PLAYER, entity.getOwner());
                    SkullBlockRenderer.renderSkull(null, 0, 0, pose, buffer, packedLight, model, renderType);

                    pose.translate(0.5, 0, 0.5);
                    pose.mulPose(Axis.YP.rotationDegrees(180));
                    pose.translate(-0.5, 0, -0.5);
                    pose.translate(0, 0, -1);

                    SkullBlockRenderer.renderSkull(null, 0, 0, pose, buffer, packedLight, model, renderType);
                } else {
                    var consumer = buffer.getBuffer(getFlagImage(entity.getUrl()));
                    Matrix4f matrix4f = poseStack.last().pose();
                    Matrix3f matrix3fNormal = poseStack.last().normal();
                    Vec3i normal = direction.normal();

                    pose.translate(0.5, 0, 0.5);
                    pose.mulPose(Axis.XP.rotationDegrees(180));
                    pose.translate(-0.5, 0, -0.5);

                    pose.scale(1 + 5.8f / 16f, 1, 1);

                    pose.translate(-11 / 16f, -15 / 16f, 0.495);
                    renderQuad(matrix4f, matrix3fNormal, normal, consumer, 1, 1, 0, 0, 1, 1, packedLight, packedOverlay);

                    pose.translate(0.5, 0, 0.5);
                    pose.mulPose(Axis.YP.rotationDegrees(180));
                    pose.translate(-0.5, 0, -0.5);

                    pose.translate(0, 0, 0.99);

                    renderQuad(matrix4f, matrix3fNormal, normal, consumer, 1, 1, 0, 0, 1, 1, packedLight, packedOverlay);
                }
            }
        }
    }

    private static void renderQuad(Matrix4f pose, Matrix3f matrix3fNormal, Vec3i normal, VertexConsumer consumer, float width, float height, float u, float v, float uWidth, float vHeight, int light, int overlay) {
        consumer.vertex(pose, 0, 0, 0).color(255, 255, 255, 255).uv(u, v).overlayCoords(overlay).uv2(light).normal(matrix3fNormal, normal.getX(), normal.getY(), normal.getZ()).endVertex();
        consumer.vertex(pose, 0, height, 0).color(255, 255, 255, 255).uv(u, v + vHeight).overlayCoords(overlay).uv2(light).normal(matrix3fNormal, normal.getX(), normal.getY(), normal.getZ()).endVertex();
        consumer.vertex(pose, width, height, 0).color(255, 255, 255, 255).uv(u + uWidth, v + vHeight).overlayCoords(overlay).uv2(light).normal(matrix3fNormal, normal.getX(), normal.getY(), normal.getZ()).endVertex();
        consumer.vertex(pose, width, 0, 0).color(255, 255, 255, 255).uv(u + uWidth, v).overlayCoords(overlay).uv2(light).normal(matrix3fNormal, normal.getX(), normal.getY(), normal.getZ()).endVertex();
    }

    private static RenderType getFlagImage(String url) {
        ResourceLocation id = FlagTexture.getTextureId(url);
        TextureManager manager = Minecraft.getInstance().getTextureManager();
        AbstractTexture texture = manager.getTexture(id, MissingTextureAtlasSprite.getTexture());
        if (texture == MissingTextureAtlasSprite.getTexture()) manager.register(id, new FlagTexture(url));
        return RenderType.entitySolid(id);
    }
}
