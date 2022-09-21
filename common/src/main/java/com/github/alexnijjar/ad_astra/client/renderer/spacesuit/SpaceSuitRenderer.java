package com.github.alexnijjar.ad_astra.client.renderer.spacesuit;

import com.github.alexnijjar.ad_astra.items.armour.JetSuit;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SpaceSuitRenderer {

	public static Identifier SPACE_SUIT_TEXTURE = new ModIdentifier("textures/entity/armour/space_suit.png");
	public static Identifier NETHERITE_SPACE_SUIT_TEXTURE = new ModIdentifier("textures/entity/armour/netherite_space_suit.png");
	public static Identifier JET_SUIT_TEXTURE = new ModIdentifier("textures/entity/armour/jet_suit/jet_suit_5.png");

	public static void register() {
		ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
			EntityModelLoader modelLoader = MinecraftClient.getInstance().getEntityModelLoader();
			ModelPart layer = modelLoader.getModelPart(SpaceSuitModel.LAYER_LOCATION);
			SpaceSuitModel model = new SpaceSuitModel(layer, contextModel, entity, slot, stack, SPACE_SUIT_TEXTURE);
			ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, SPACE_SUIT_TEXTURE);
		}, ModItems.SPACE_HELMET, ModItems.SPACE_SUIT, ModItems.SPACE_PANTS, ModItems.SPACE_BOOTS);

		ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
			EntityModelLoader modelLoader = MinecraftClient.getInstance().getEntityModelLoader();
			ModelPart layer = modelLoader.getModelPart(NetheriteSpaceSuitModel.LAYER_LOCATION);
			SpaceSuitModel model = new SpaceSuitModel(layer, contextModel, entity, slot, stack, NETHERITE_SPACE_SUIT_TEXTURE);
			ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, NETHERITE_SPACE_SUIT_TEXTURE);
		}, ModItems.NETHERITE_SPACE_HELMET, ModItems.NETHERITE_SPACE_SUIT, ModItems.NETHERITE_SPACE_PANTS, ModItems.NETHERITE_SPACE_BOOTS);

		ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
			EntityModelLoader modelLoader = MinecraftClient.getInstance().getEntityModelLoader();
			ModelPart layer = modelLoader.getModelPart(JetSuitModel.LAYER_LOCATION);
			Identifier texture = JET_SUIT_TEXTURE;
			if (slot.equals(EquipmentSlot.CHEST)) {
				if (stack.getItem() instanceof JetSuit suit) {
					long energy = suit.getStoredEnergy(stack);
					texture = new ModIdentifier("textures/entity/armour/jet_suit/jet_suit_" + (energy == 0 ? 0 : ((int) Math.min((suit.getStoredEnergy(stack) * 5 / suit.getEnergyCapacity()) + 1, 5))) + ".png");
				}
			}
			SpaceSuitModel model = new SpaceSuitModel(layer, contextModel, entity, slot, stack, texture);
			ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, texture);

			if (slot.equals(EquipmentSlot.CHEST)) {
				if (JetSuit.hasFullSet(entity)) {
					JetSuit.spawnParticles(entity.world, entity, contextModel);
				}
			}
		}, ModItems.JET_SUIT_HELMET, ModItems.JET_SUIT, ModItems.JET_SUIT_PANTS, ModItems.JET_SUIT_BOOTS);
	}
}