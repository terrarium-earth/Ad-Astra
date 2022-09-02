package com.github.alexnijjar.ad_astra.client.renderer.spacesuit;

import com.github.alexnijjar.ad_astra.items.armour.SpaceSuit;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexConsumers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class AbstractSpaceSuitModel extends BipedEntityModel<LivingEntity> {

	private final BipedEntityModel<LivingEntity> contextModel;
	private final Identifier texture;
	private final EquipmentSlot slot;
	private final ItemStack stack;

	public final ModelPart visor;
	public final ModelPart rightBoot;
	public final ModelPart leftBoot;

	public AbstractSpaceSuitModel(ModelPart root, BipedEntityModel<LivingEntity> contextModel, EquipmentSlot slot, ItemStack stack, Identifier texture) {
		super(root);

		this.contextModel = contextModel;
		this.texture = texture;
		this.slot = slot;
		this.stack = stack;

		this.visor = root.getChild("visor");
		this.rightBoot = root.getChild("left_boot");
		this.leftBoot = root.getChild("right_boot");
		this.setVisible();

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
		this.visor.copyTransform(this.contextModel.head);
		this.body.copyTransform(this.contextModel.body);
		this.rightArm.copyTransform(this.contextModel.rightArm);
		this.leftArm.copyTransform(this.contextModel.leftArm);
		this.leftLeg.copyTransform(this.contextModel.leftLeg);
		this.rightLeg.copyTransform(this.contextModel.rightLeg);
		this.rightBoot.copyTransform(this.contextModel.rightLeg);
		this.leftBoot.copyTransform(this.contextModel.leftLeg);

		matrices.push();
		if (this.child) {
			matrices.scale(0.5f, 0.5f, 0.5f);
			matrices.translate(0, 1.5f, 0);
		}

		int decimal = ((SpaceSuit) this.stack.getItem()).getColor(stack);
		float r = (float) (decimal >> 16 & 0xFF) / 255.0f;
		float g = (float) (decimal >> 8 & 0xFF) / 255.0f;
		float b = (float) (decimal & 0xFF) / 255.0f;

		this.head.render(matrices, vertices, light, overlay, r, g, b, 1.0f);

		MinecraftClient client = MinecraftClient.getInstance();
		boolean hasEnchantments = client.player.getEquippedStack(EquipmentSlot.HEAD).hasEnchantments();
		this.visor.render(matrices, getVertex(RenderLayer.getEntityTranslucent(this.texture), hasEnchantments), light, overlay, r, g, b, 1.0f);

		this.body.render(matrices, vertices, light, overlay, r, g, b, 1.0f);
		this.rightArm.render(matrices, vertices, light, overlay, r, g, b, 1.0f);
		this.leftArm.render(matrices, vertices, light, overlay, r, g, b, 1.0f);

		this.rightLeg.render(matrices, vertices, light, overlay, r, g, b, 1.0f);
		this.leftLeg.render(matrices, vertices, light, overlay, r, g, b, 1.0f);

		this.rightBoot.render(matrices, vertices, light, overlay, r, g, b, 1.0f);
		this.leftBoot.render(matrices, vertices, light, overlay, r, g, b, 1.0f);

		matrices.pop();
	}

	public static VertexConsumer getVertex(RenderLayer renderLayer, boolean hasEnchantments) {
		VertexConsumerProvider vertexConsumerProvider = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
		return hasEnchantments ? VertexConsumers.union(vertexConsumerProvider.getBuffer(RenderLayer.getEntityGlint()), vertexConsumerProvider.getBuffer(renderLayer)) : vertexConsumerProvider.getBuffer(renderLayer);
	}

	private void setVisible() {
		this.setVisible(false);
		switch (this.slot) {
		case HEAD -> {
			this.head.visible = true;
			this.visor.visible = true;
		}
		case CHEST -> {
			this.body.visible = true;
			this.rightArm.visible = true;
			this.leftArm.visible = true;
		}
		case LEGS -> {
			this.rightLeg.visible = true;
			this.leftLeg.visible = true;
		}
		case FEET -> {
			this.rightBoot.visible = true;
			this.leftBoot.visible = true;
		}
		default -> {
		}
		}
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		this.visor.visible = false;
		this.rightBoot.visible = false;
		this.leftBoot.visible = false;
	}
}