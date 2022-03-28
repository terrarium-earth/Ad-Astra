package net.mrscauthd.beyond_earth.client.renderer.globe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;
import net.mrscauthd.beyond_earth.registry.ModBlocks;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class GlobeRenderer {

    // Textures
    public static final Identifier EARTH_GLOBE = new ModIdentifier("textures/blocks/globes/earth_globe.png");
    public static final Identifier MOON_GLOBE = new ModIdentifier("textures/blocks/globes/moon_globe.png");
    public static final Identifier MARS_GLOBE = new ModIdentifier("textures/blocks/globes/mars_globe.png");
    public static final Identifier MERCURY_GLOBE = new ModIdentifier("textures/blocks/globes/mercury_globe.png");
    public static final Identifier VENUS_GLOBE = new ModIdentifier("textures/blocks/globes/venus_globe.png");
    public static final Identifier GLACIO_GLOBE = new ModIdentifier("textures/blocks/globes/glacio_globe.png");

    // Item.
    public static void render(Identifier id, GlobeModel model, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        render(id, model, Direction.NORTH, matrices, vertexConsumers, light, overlay);
    }

    // Render model.
    public static void render(Identifier id, GlobeModel model, Direction direction, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        // Get texture.
        VertexConsumer vertexConsumer;
        if (id.equals(Registry.BLOCK.getId(ModBlocks.EARTH_GLOBE))) {
            vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCullZOffset(EARTH_GLOBE));
        } else if (id.equals(Registry.BLOCK.getId(ModBlocks.MOON_GLOBE))) {
            vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCullZOffset(MOON_GLOBE));
        } else if (id.equals(Registry.BLOCK.getId(ModBlocks.MARS_GLOBE))) {
            vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCullZOffset(MARS_GLOBE));
        } else if (id.equals(Registry.BLOCK.getId(ModBlocks.MERCURY_GLOBE))) {
            vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCullZOffset(MERCURY_GLOBE));
        } else if (id.equals(Registry.BLOCK.getId(ModBlocks.VENUS_GLOBE))) {
            vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCullZOffset(VENUS_GLOBE));
        } else {
            vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCullZOffset(GLACIO_GLOBE));
        }

        matrices.push();

        matrices.translate(0.5f, 1.5f, 0.5f);
        matrices.scale(-1.0f, -1.0f, 1.0f);
        matrices.multiply(direction.getRotationQuaternion());

        // Turn upright.
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90));

        model.render(matrices, vertexConsumer, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);

        matrices.pop();
    }

    // Get model from client.
    public static GlobeModel getModel() {
        return new GlobeModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(GlobeModel.LAYER_LOCATION));
    }
}
