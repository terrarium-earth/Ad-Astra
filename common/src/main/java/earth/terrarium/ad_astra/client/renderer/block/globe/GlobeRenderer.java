package earth.terrarium.ad_astra.client.renderer.block.globe;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.registry.ModBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class GlobeRenderer {

    // Textures
    public static final ResourceLocation EARTH_GLOBE = new ResourceLocation(AdAstra.MOD_ID, "textures/block/globes/earth_globe.png");
    public static final ResourceLocation MOON_GLOBE = new ResourceLocation(AdAstra.MOD_ID, "textures/block/globes/moon_globe.png");
    public static final ResourceLocation MARS_GLOBE = new ResourceLocation(AdAstra.MOD_ID, "textures/block/globes/mars_globe.png");
    public static final ResourceLocation MERCURY_GLOBE = new ResourceLocation(AdAstra.MOD_ID, "textures/block/globes/mercury_globe.png");
    public static final ResourceLocation VENUS_GLOBE = new ResourceLocation(AdAstra.MOD_ID, "textures/block/globes/venus_globe.png");
    public static final ResourceLocation GLACIO_GLOBE = new ResourceLocation(AdAstra.MOD_ID, "textures/block/globes/glacio_globe.png");

    // Render model
    public static void render(ResourceLocation id, GlobeModel model, Direction direction, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {

        // Get the texture
        VertexConsumer vertexConsumer;
        if (id.equals(Registry.BLOCK.getKey(ModBlocks.EARTH_GLOBE.get()))) {
            vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCullZOffset(EARTH_GLOBE));
        } else if (id.equals(Registry.BLOCK.getKey(ModBlocks.MOON_GLOBE.get()))) {
            vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCullZOffset(MOON_GLOBE));
        } else if (id.equals(Registry.BLOCK.getKey(ModBlocks.MARS_GLOBE.get()))) {
            vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCullZOffset(MARS_GLOBE));
        } else if (id.equals(Registry.BLOCK.getKey(ModBlocks.MERCURY_GLOBE.get()))) {
            vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCullZOffset(MERCURY_GLOBE));
        } else if (id.equals(Registry.BLOCK.getKey(ModBlocks.VENUS_GLOBE.get()))) {
            vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCullZOffset(VENUS_GLOBE));
        } else {
            vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCullZOffset(GLACIO_GLOBE));
        }

        poseStack.pushPose();

        poseStack.translate(0.5f, 1.5f, 0.5f);
        poseStack.scale(-1.0f, -1.0f, 1.0f);
        poseStack.mulPose(direction.getRotation());

        // Turn upright
        poseStack.mulPose(Vector3f.XN.rotationDegrees(90));

        model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0f, 1.0f, 1.0f, 1.0f);

        poseStack.popPose();
    }
}