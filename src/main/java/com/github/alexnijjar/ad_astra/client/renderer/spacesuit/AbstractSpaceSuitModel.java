package com.github.alexnijjar.beyond_earth.client.renderer.spacesuit;

import java.util.Collections;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public abstract class AbstractSpaceSuitModel extends BipedEntityModel<LivingEntity> {

	private BipedEntityModel<?> contextModel;

	private Identifier headTexture;
	public ModelPart head;
	public ModelPart body;
	public ModelPart rightArm;
	public ModelPart leftArm;
	public ModelPart rightLeg;
	public ModelPart leftLeg;

	public <T extends LivingEntity> AbstractSpaceSuitModel(ModelPart root, BipedEntityModel<T> contextModel, Identifier headTexture) {
		this(root, contextModel, headTexture, MinecraftClient.getInstance());
	}

	@SuppressWarnings("unchecked")
	public <T extends LivingEntity> AbstractSpaceSuitModel(ModelPart root, BipedEntityModel<T> contextModel, Identifier headTexture, MinecraftClient client) {
		super(new EntityRendererFactory.Context(client.getEntityRenderDispatcher(), client.getItemRenderer(), client.getBlockRenderManager(), client.getEntityRenderDispatcher().getHeldItemRenderer(), client.getResourceManager(), client.getEntityModelLoader(), client.textRenderer).getPart(EntityModelLayers.PLAYER_INNER_ARMOR));
		this.contextModel = contextModel;
		this.headTexture = headTexture;
		ModelPart empty = new ModelPart(Collections.EMPTY_LIST, Collections.EMPTY_MAP);
		this.head = empty;
		this.body = empty;
		this.rightArm = empty;
		this.leftArm = empty;
		this.rightLeg = empty;
		this.leftLeg = empty;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		
		this.handSwingProgress = this.contextModel.handSwingProgress;
		this.riding = this.contextModel.riding;
		this.child = this.contextModel.child;
		this.leftArmPose = this.contextModel.leftArmPose;
		this.rightArmPose = this.contextModel.rightArmPose;
		this.sneaking = this.contextModel.sneaking;
		this.head.copyTransform(this.contextModel.head);
		this.body.copyTransform(this.contextModel.body);
		this.rightArm.copyTransform(this.contextModel.rightArm);
		this.leftArm.copyTransform(this.contextModel.leftArm);
		this.rightLeg.copyTransform(this.contextModel.rightLeg);
		this.leftLeg.copyTransform(this.contextModel.leftLeg);

		matrices.push();
		if (this.child) {
			matrices.scale(0.5f, 0.5f, 0.5f);
			matrices.translate(0, 1.5f, 0);
		}

		this.body.render(matrices, vertices, light, overlay);
		this.rightArm.render(matrices, vertices, light, overlay);
		this.leftArm.render(matrices, vertices, light, overlay);
		this.rightLeg.render(matrices, vertices, light, overlay);
		this.leftLeg.render(matrices, vertices, light, overlay);
		matrices.pop();
		
		matrices.push();
		if (this.child) {
			matrices.scale(0.8f, 0.8f, 0.8f);
			matrices.translate(0, 1.0f, 0);
		}

		MinecraftClient client = MinecraftClient.getInstance();
		boolean hasEnchantments = client.player.getEquippedStack(EquipmentSlot.HEAD).hasEnchantments();
		this.head.render(matrices, getVertex(RenderLayer.getEntityTranslucent(getTexture()), hasEnchantments, client), light, overlay);
		matrices.pop();

	}

	public static VertexConsumer getVertex(RenderLayer renderLayer, boolean hasEnchantments, MinecraftClient client) {
		VertexConsumerProvider vertexConsumerProvider = client.getBufferBuilders().getEntityVertexConsumers();
		return hasEnchantments ? VertexConsumers.union(vertexConsumerProvider.getBuffer(RenderLayer.getEntityGlint()), vertexConsumerProvider.getBuffer(renderLayer)) : vertexConsumerProvider.getBuffer(renderLayer);
	}

	public Identifier getTexture() {
		return this.headTexture;
	}
}