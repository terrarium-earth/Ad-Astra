package earth.terrarium.ad_astra.client.renderer.block.flag;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import earth.terrarium.ad_astra.blocks.flags.FlagBlock;
import earth.terrarium.ad_astra.blocks.flags.FlagBlockEntity;
import earth.terrarium.ad_astra.client.AdAstraClient;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.Registry;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class FlagBlockEntityRenderer implements BlockEntityRenderer<FlagBlockEntity> {

    public FlagBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {}

    @Override
    public void render(FlagBlockEntity entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (entity.getBlockState().getValue(FlagBlock.HALF).equals(DoubleBlockHalf.LOWER)) {
            ResourceLocation texture = new ModResourceLocation("block/flag/" + Registry.BLOCK.getKey(entity.getBlockState().getBlock()).getPath());
            poseStack.pushPose();

            poseStack.translate(0.5D, 0.5D, 0.5D);
            poseStack.mulPose(Vector3f.YP.rotationDegrees(entity.getBlockState().getValue(FlagBlock.FACING).asRotation()));
            poseStack.translate(-0.5D, 0, -0.5D);

            AdAstraClient.renderBlock(texture, poseStack, buffer, packedLight, packedOverlay);

            poseStack.popPose();
        } else {
            String url = entity.getUrl();
            if (url != null) {
                renderFullTexture(entity, poseStack, buffer, packedLight, packedOverlay, false);
                renderFullTexture(entity, poseStack, buffer, packedLight, packedOverlay, true);
            } else {
                renderFace(entity, poseStack, buffer, packedLight, packedOverlay, false);
                renderFace(entity, poseStack, buffer, packedLight, packedOverlay, true);
            }
        }
    }

    private static void renderFullTexture(FlagBlockEntity entity, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, boolean flip) {
        poseStack.pushPose();
        poseStack.translate(0.5, 1, 0.5);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(entity.getBlockState().getValue(FlagBlock.FACING).asRotation()));
        poseStack.mulPose(Vector3f.XP.rotationDegrees(180));
        poseStack.translate(-1.375, -0.375, flip ? -0.01 : 0.01);

        if (!flip) flipY(poseStack, 1.25f);

        VertexConsumer vertexConsumer = buffer.getBuffer(getFlagImage(entity.getUrl()));
        renderQuad(poseStack, vertexConsumer, 1.25f, 0.875f, 0, 0, 1f, 1f, packedLight, packedOverlay);
        poseStack.popPose();
    }

    private static void renderFace(FlagBlockEntity entity, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int overlay, boolean flip) {
        poseStack.pushPose();
        poseStack.translate(0.5, 1, 0.5);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(entity.getBlockState().getValue(FlagBlock.FACING).asRotation()));
        poseStack.mulPose(Vector3f.XP.rotationDegrees(180));
        poseStack.translate(-1, -0.25, flip ? -0.01 : 0.01);

        if (flip) flipY(poseStack, 0.5f);
        VertexConsumer vertexConsumer = buffer.getBuffer(getRenderLayer(entity.getOwner()));
        renderQuad(poseStack, vertexConsumer, 0.5f, 0.5f, 8f/64f, 8f/64f, 8f/64f, 8f/64f, packedLight, overlay);
        renderQuad(poseStack, vertexConsumer, 0.5f, 0.5f, 32f/64f, 8f/64f, 8f/64f, 8f/64f, packedLight, overlay);
        poseStack.popPose();
    }

    private static void renderQuad(PoseStack poseStack, VertexConsumer consumer, float width, float height, float u, float v, float uWidth, float vHeight, int light, int overlay) {
        consumer.vertex(poseStack.last().pose(), 0, 0, 0).color(255, 255, 255, 255).uv(u, v).overlayCoords(overlay).uv2(light).normal(0, 0, 1).endVertex();
        consumer.vertex(poseStack.last().pose(), 0, height, 0).color(255, 255, 255, 255).uv(u, v + vHeight).overlayCoords(overlay).uv2(light).normal(0, 0, 1).endVertex();
        consumer.vertex(poseStack.last().pose(), width, height, 0).color(255, 255, 255, 255).uv(u + uWidth, v + vHeight).overlayCoords(overlay).uv2(light).normal(0, 0, 1).endVertex();
        consumer.vertex(poseStack.last().pose(), width, 0, 0).color(255, 255, 255, 255).uv(u + uWidth, v).overlayCoords(overlay).uv2(light).normal(0, 0, 1).endVertex();
    }

    private static void flipY(PoseStack poseStack, float width) {
        Vector4f vec3f = new Vector4f(0, 0, 0, 0);
        vec3f.transform(poseStack.last().pose());
        poseStack.translate(-vec3f.x(), -vec3f.y(), -vec3f.z());
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180));
        poseStack.translate(vec3f.x(), vec3f.y(), vec3f.z());
        poseStack.translate(-width, 0, 0);
    }

    public static RenderType getRenderLayer(@Nullable GameProfile profile) {
        if (profile == null) {
            // Default zombie head texture on the flag if the player head can not be found.
            return RenderType.entityCutoutNoCullZOffset(new ResourceLocation("textures/entity/zombie/zombie.png"));
        }
        Minecraft minecraftClient = Minecraft.getInstance();
        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraftClient.getSkinManager().getInsecureSkinInformation(profile);
        if (map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
            return RenderType.entityTranslucent(minecraftClient.getSkinManager().registerTexture(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN));
        }
        return RenderType.entityCutoutNoCull(DefaultPlayerSkin.getDefaultSkin(UUIDUtil.getOrCreatePlayerUUID(profile)));
    }

    public static RenderType getFlagImage(String url) {
        ResourceLocation id = FlagTexture.getTextureId(url);
        TextureManager manager = Minecraft.getInstance().getTextureManager();
        AbstractTexture texture = manager.getTexture(id, MissingTextureAtlasSprite.getTexture());
        if (texture == MissingTextureAtlasSprite.getTexture()) manager.register(id, new FlagTexture(url));
        return RenderType.entityTranslucent(id);
    }
}