package com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles;

import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rover.RoverEntityModel;
import com.github.alexnijjar.ad_astra.entities.vehicles.VehicleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public abstract class VehicleEntityRenderer<T extends VehicleEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> {

	protected VehicleEntityRenderer(EntityRendererFactory.Context context, M model, float shadowRadius) {
		super(context);
		this.model = model;
		this.shadowRadius = shadowRadius;
	}

	protected M model;

	@Override
	public M getModel() {
		return this.model;
	}

	@Override
	public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		matrices.push();

		matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180));
		matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(this.getYawOffset()));
		matrices.translate(0.0, -1.501, 0.0);

		if (entity.isFrozen()) {
			this.shakeVehicle(entity, tickDelta, matrices);
		}

		this.model.setAngles(entity, tickDelta, 0.0f, -0.1f, 0.0f, 0.0f);
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(getTexture(entity)));
		this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);

		matrices.pop();

		super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
	}

	private void shakeVehicle(T entity, float tickDelta, MatrixStack matrices) {
		if (!MinecraftClient.getInstance().isPaused()) {
			double shakeDirection1 = (tickDelta * (entity.world.random.nextBoolean() ? 1 : -1)) / 150;
			double shakeDirection2 = (tickDelta * (entity.world.random.nextBoolean() ? 1 : -1)) / 150;
			double shakeDirection3 = (tickDelta * (entity.world.random.nextBoolean() ? 1 : -1)) / 150;
			matrices.translate(shakeDirection1, shakeDirection2, shakeDirection3);
		}
	}

	public static void renderItem(EntityModel<?> model, Identifier texture, EntityModelLayer layer, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

		matrices.push();

		matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180));
		matrices.translate(0.0, -1.501, 0.0);

		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCullZOffset(texture));
		model.render(matrices, vertexConsumer, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);

		matrices.pop();
	}

	public static void renderRocketItem(Identifier texture, EntityModelLayer layer, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		EntityModel<?> model = new VehicleEntityModel<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(layer), "rocket");
		renderItem(model, texture, layer, matrices, vertexConsumers, light, overlay);
	}

	public static void renderRoverItem(Identifier texture, EntityModelLayer layer, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		EntityModel<?> model = new RoverEntityModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(layer));
		renderItem(model, texture, layer, matrices, vertexConsumers, light, overlay);
	}

	public int getYawOffset() {
		return 180;
	}
}
