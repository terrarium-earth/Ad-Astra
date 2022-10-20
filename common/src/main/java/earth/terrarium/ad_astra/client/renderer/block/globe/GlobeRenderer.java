package earth.terrarium.ad_astra.client.renderer.block.globe;

import earth.terrarium.ad_astra.registry.ModBlocks;
import earth.terrarium.ad_astra.util.ModIdentifier;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class GlobeRenderer {

	// Textures
	public static final Identifier EARTH_GLOBE = new ModIdentifier("textures/block/globes/earth_globe.png");
	public static final Identifier MOON_GLOBE = new ModIdentifier("textures/block/globes/moon_globe.png");
	public static final Identifier MARS_GLOBE = new ModIdentifier("textures/block/globes/mars_globe.png");
	public static final Identifier MERCURY_GLOBE = new ModIdentifier("textures/block/globes/mercury_globe.png");
	public static final Identifier VENUS_GLOBE = new ModIdentifier("textures/block/globes/venus_globe.png");
	public static final Identifier GLACIO_GLOBE = new ModIdentifier("textures/block/globes/glacio_globe.png");

	// Render model
	public static void render(Identifier id, GlobeModel model, Direction direction, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

		// Get the texture
		VertexConsumer vertexConsumer;
		if (id.equals(Registry.BLOCK.getId(ModBlocks.EARTH_GLOBE.get()))) {
			vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCullZOffset(EARTH_GLOBE));
		} else if (id.equals(Registry.BLOCK.getId(ModBlocks.MOON_GLOBE.get()))) {
			vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCullZOffset(MOON_GLOBE));
		} else if (id.equals(Registry.BLOCK.getId(ModBlocks.MARS_GLOBE.get()))) {
			vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCullZOffset(MARS_GLOBE));
		} else if (id.equals(Registry.BLOCK.getId(ModBlocks.MERCURY_GLOBE.get()))) {
			vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCullZOffset(MERCURY_GLOBE));
		} else if (id.equals(Registry.BLOCK.getId(ModBlocks.VENUS_GLOBE.get()))) {
			vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCullZOffset(VENUS_GLOBE));
		} else {
			vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCullZOffset(GLACIO_GLOBE));
		}

		matrices.push();

		matrices.translate(0.5f, 1.5f, 0.5f);
		matrices.scale(-1.0f, -1.0f, 1.0f);
		matrices.multiply(direction.getRotationQuaternion());

		// Turn upright
		matrices.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(90));

		model.render(matrices, vertexConsumer, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);

		matrices.pop();
	}
}