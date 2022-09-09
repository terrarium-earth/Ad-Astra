package com.github.alexnijjar.ad_astra.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.alexnijjar.ad_astra.client.renderer.spacesuit.NetheriteSpaceSuitModel;
import com.github.alexnijjar.ad_astra.client.renderer.spacesuit.SpaceSuitModel;
import com.github.alexnijjar.ad_astra.client.renderer.spacesuit.SpaceSuitRenderer;
import com.github.alexnijjar.ad_astra.items.armour.SpaceSuit;
import com.github.alexnijjar.ad_astra.items.vehicles.VehicleItem;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {

	@Inject(method = "renderRightArm", at = @At("HEAD"), cancellable = true)
	public void adastra_renderRightArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, CallbackInfo ci) {
		if (player.getOffHandStack().getItem() instanceof VehicleItem) {
			ci.cancel();
			return;
		}
		if (player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof SpaceSuit spaceSuit) {
			ci.cancel();
			this.adastra_renderArm(matrices, vertexConsumers, light, player, true);
		}
	}

	@Inject(method = "renderLeftArm", at = @At("HEAD"), cancellable = true)
	public void adastra_renderLeftArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, CallbackInfo ci) {
		if (player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof SpaceSuit spaceSuit) {
			ci.cancel();
			this.adastra_renderArm(matrices, vertexConsumers, light, player, false);
		}
	}

	// Render space suit arm in first person.
	@SuppressWarnings({ "unchecked", "rawtypes" })

	@Unique
	private void adastra_renderArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, boolean right) {
		ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);
		if (stack.getItem() instanceof SpaceSuit spaceSuit) {

			PlayerEntityRenderer renderer = (PlayerEntityRenderer) (Object) (this);

			Identifier texture;
			SpaceSuitModel model;
			EntityModelLoader modelLoader = MinecraftClient.getInstance().getEntityModelLoader();

			if (stack.isOf(ModItems.NETHERITE_SPACE_SUIT)) {
				texture = SpaceSuitRenderer.NETHERITE_SPACE_SUIT_TEXTURE;
				model = new SpaceSuitModel(modelLoader.getModelPart(NetheriteSpaceSuitModel.LAYER_LOCATION), (BipedEntityModel) renderer.getModel(), EquipmentSlot.CHEST, stack, texture);
			} else {
				texture = SpaceSuitRenderer.SPACE_SUIT_TEXTURE;
				model = new SpaceSuitModel(modelLoader.getModelPart(SpaceSuitModel.LAYER_LOCATION), (BipedEntityModel) renderer.getModel(), EquipmentSlot.CHEST, stack, texture);
			}

			matrices.push();
			matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(4));

			int decimal = ((SpaceSuit) stack.getItem()).getColor(stack);
			float r = (float) (decimal >> 16 & 0xFF) / 255.0f;
			float g = (float) (decimal >> 8 & 0xFF) / 255.0f;
			float b = (float) (decimal & 0xFF) / 255.0f;

			VertexConsumer vertex = SpaceSuitModel.getVertex(RenderLayer.getEntityTranslucent(texture), player.getEquippedStack(EquipmentSlot.CHEST).hasEnchantments());
			if (right) {
				model.rightArm.render(matrices, vertex, light, OverlayTexture.DEFAULT_UV, r, g, b, 1.0f);
			} else {
				model.leftArm.render(matrices, vertex, light, OverlayTexture.DEFAULT_UV, r, g, b, 1.0f);
			}
			matrices.pop();
		}
	}
}
