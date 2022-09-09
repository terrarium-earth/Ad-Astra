package com.github.alexnijjar.ad_astra.client.renderer.spacesuit;

import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SpaceSuitRenderer {

	public static Identifier SPACE_SUIT_TEXTURE = new ModIdentifier("textures/models/armor/space_suit.png");
	public static Identifier NETHERITE_SPACE_SUIT_TEXTURE = new ModIdentifier("textures/models/armor/netherite_space_suit.png");
	public static Identifier JET_SUIT_TEXTURE = new ModIdentifier("textures/models/armor/jet_suit.png");

	public static void register() {
		ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
			EntityModelLoader modelLoader = MinecraftClient.getInstance().getEntityModelLoader();
			ModelPart layer = modelLoader.getModelPart(SpaceSuitModel.LAYER_LOCATION);
			SpaceSuitModel model = new SpaceSuitModel(layer, contextModel, slot, stack, SPACE_SUIT_TEXTURE);
			ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, SPACE_SUIT_TEXTURE);
		}, ModItems.SPACE_HELMET, ModItems.SPACE_SUIT, ModItems.SPACE_PANTS, ModItems.SPACE_BOOTS);

		ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
			EntityModelLoader modelLoader = MinecraftClient.getInstance().getEntityModelLoader();
			ModelPart layer = modelLoader.getModelPart(NetheriteSpaceSuitModel.LAYER_LOCATION);
			SpaceSuitModel model = new SpaceSuitModel(layer, contextModel, slot, stack, NETHERITE_SPACE_SUIT_TEXTURE);
			ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, NETHERITE_SPACE_SUIT_TEXTURE);
		}, ModItems.NETHERITE_SPACE_HELMET, ModItems.NETHERITE_SPACE_SUIT, ModItems.NETHERITE_SPACE_PANTS, ModItems.NETHERITE_SPACE_BOOTS);
	}
}