package com.github.alexnijjar.ad_astra.client.renderer.spacesuit;

import com.github.alexnijjar.ad_astra.items.armour.NetheriteSpaceSuit;
import com.github.alexnijjar.ad_astra.items.armour.SpaceSuit;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumers;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class SpaceSuitModel extends BipedEntityModel<LivingEntity> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("space_suit"), "main");

	private final BipedEntityModel<LivingEntity> contextModel;
	@SuppressWarnings("unused")
	private final LivingEntity entity;
	private final Identifier texture;
	private final EquipmentSlot slot;
	private final ItemStack stack;

	public final ModelPart visor;
	public final ModelPart rightBoot;
	public final ModelPart leftBoot;

	public SpaceSuitModel(ModelPart root, BipedEntityModel<LivingEntity> contextModel, LivingEntity entity, EquipmentSlot slot, ItemStack stack, Identifier texture) {
		super(root);

		this.contextModel = contextModel;
		this.entity = entity;
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

		float a = 1.0f;
		if (!(stack.getItem() instanceof NetheriteSpaceSuit)) {
			a = decimal == 0xFFFFFF ? 0.3f : 1.0f;
		}

		MinecraftClient client = MinecraftClient.getInstance();
		boolean hasEnchantments = client.player.getEquippedStack(EquipmentSlot.HEAD).hasEnchantments();
		if (!this.stack.isOf(ModItems.SPACE_HELMET.get())) {
			this.head.render(matrices, vertices, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
			this.visor.render(matrices, getVertex(RenderLayer.getEntityTranslucent(this.texture), hasEnchantments), light, overlay, r, g, b, a);
		} else {
			this.head.render(matrices, vertices, light, overlay, r, g, b, 1.0f);
			this.visor.render(matrices, getVertex(RenderLayer.getEntityTranslucent(this.texture), hasEnchantments), light, overlay, r, g, b, a);
		}

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

	@SuppressWarnings("unused")
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData visor = modelPartData.addChild("visor", ModelPartBuilder.create().uv(32, 0).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new Dilation(1.0f)), ModelTransform.pivot(0.0f, 0.0f, 0.0f));

		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new Dilation(0.6f)), ModelTransform.pivot(0.0f, 0.0f, 0.0f));
		ModelPartData hat = modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(0, 0), ModelTransform.NONE);

		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(32, 16).cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new Dilation(0.6f)), ModelTransform.pivot(0.0f, 0.5f, 0.0f));

		ModelPartData backpack = body.addChild("backpack", ModelPartBuilder.create().uv(32, 44).cuboid(-6.0f, -8.0f, -2.0f, 12.0f, 16.0f, 4.0f, new Dilation(0.6f)), ModelTransform.pivot(0.0f, 5.0f, 5.0f));

		ModelPartData right_arm = modelPartData.addChild("right_arm", ModelPartBuilder.create(), ModelTransform.pivot(-5.0f, 2.0f, 0.0f));

		ModelPartData cube_r1 = right_arm.addChild("cube_r1", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, -6.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.6f)), ModelTransform.of(-1.0f, 4.0f, 0.0f, 0.0f, 0.0f, 0.0f));

		ModelPartData left_arm = modelPartData.addChild("left_arm", ModelPartBuilder.create(), ModelTransform.pivot(5.0f, 2.0f, 0.0f));

		ModelPartData cube_r2 = left_arm.addChild("cube_r2", ModelPartBuilder.create().uv(16, 16).cuboid(-2.0f, -6.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.6f)), ModelTransform.of(1.0f, 4.0f, 0.0f, 0.0f, 0.0f, 0.0f));

		ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(0, 32).cuboid(-2.0f, 0.6667f, -2.0f, 4.0f, 8.0f, 4.0f, new Dilation(0.7f)), ModelTransform.pivot(-2.0f, 11.3333f, 0.0f));

		ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(16, 32).cuboid(-2.0f, 0.6667f, -2.0f, 4.0f, 8.0f, 4.0f, new Dilation(0.7f)), ModelTransform.pivot(2.0f, 11.3333f, 0.0f));

		ModelPartData right_boot = modelPartData.addChild("right_boot", ModelPartBuilder.create().uv(32, 32).cuboid(-2.0f, 6.6667f, -2.0f, 4.0f, 6.0f, 4.0f, new Dilation(1.0f)).uv(0, 45).cuboid(-2.0f, 6.6667f, -2.0f, 4.0f, 6.0f, 4.0f, new Dilation(0.6f)),
				ModelTransform.pivot(-2.0f, 11.3333f, 0.0f));

		ModelPartData left_boot = modelPartData.addChild("left_boot", ModelPartBuilder.create().uv(48, 32).cuboid(-2.0f, 6.6667f, -2.0f, 4.0f, 6.0f, 4.0f, new Dilation(1.0f)).uv(16, 45).cuboid(-2.0f, 6.6667f, -2.0f, 4.0f, 6.0f, 4.0f, new Dilation(0.6f)),
				ModelTransform.pivot(2.0f, 11.3333f, 0.0f));
		return TexturedModelData.of(modelData, 64, 64);
	}
}