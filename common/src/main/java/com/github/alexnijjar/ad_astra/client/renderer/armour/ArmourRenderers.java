package com.github.alexnijjar.ad_astra.client.renderer.armour;

import org.apache.commons.lang3.NotImplementedException;

import com.github.alexnijjar.ad_astra.registry.ModItems;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.item.Item;

@Environment(EnvType.CLIENT)
public class ArmourRenderers {

	public static void register() {
		ArmourRenderers.registerArmour((entity, stack, slot, original) -> {
			EntityModelLoader modelLoader = MinecraftClient.getInstance().getEntityModelLoader();
			ModelPart layer = modelLoader.getModelPart(SpaceSuitModel.LAYER_LOCATION);
			return new SpaceSuitModel(layer, original, entity, slot, stack);
		}, ModItems.SPACE_HELMET.get(), ModItems.SPACE_SUIT.get(), ModItems.SPACE_PANTS.get(), ModItems.SPACE_BOOTS.get());

		ArmourRenderers.registerArmour((entity, stack, slot, original) -> {
			EntityModelLoader modelLoader = MinecraftClient.getInstance().getEntityModelLoader();
			ModelPart layer = modelLoader.getModelPart(NetheriteSpaceSuitModel.LAYER_LOCATION);
			return new SpaceSuitModel(layer, original, entity, slot, stack);
		}, ModItems.NETHERITE_SPACE_HELMET.get(), ModItems.NETHERITE_SPACE_SUIT.get(), ModItems.NETHERITE_SPACE_PANTS.get(), ModItems.NETHERITE_SPACE_BOOTS.get());

		ArmourRenderers.registerArmour((entity, stack, slot, original) -> {
			EntityModelLoader modelLoader = MinecraftClient.getInstance().getEntityModelLoader();
			ModelPart layer = modelLoader.getModelPart(JetSuitModel.LAYER_LOCATION);
			return new SpaceSuitModel(layer, original, entity, slot, stack);
		}, ModItems.JET_SUIT_HELMET.get(), ModItems.JET_SUIT.get(), ModItems.JET_SUIT_PANTS.get(), ModItems.JET_SUIT_BOOTS.get());
	}

	@ExpectPlatform
	public static void registerArmour(ArmourModelSupplier modelSupplier, Item... items) {
		throw new NotImplementedException();
	}
}