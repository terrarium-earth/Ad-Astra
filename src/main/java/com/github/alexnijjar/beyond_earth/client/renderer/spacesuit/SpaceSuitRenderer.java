package com.github.alexnijjar.beyond_earth.client.renderer.spacesuit;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.github.alexnijjar.beyond_earth.registry.ModItems;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SpaceSuitRenderer {

    public static Identifier SPACE_SUIT_CHEST_LOCATION = new ModIdentifier("textures/models/armor/space_suit.png");
    public static Identifier NETHERITE_SPACE_SUIT_CHEST_LOCATION = new ModIdentifier("textures/models/armor/netherite_space_suit.png");
    public static Identifier JET_SUIT_CHEST_LOCATION = new ModIdentifier("textures/models/armor/jet_suit.png");

    public static void register() {

        // Space Suit.
        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
            Identifier texture = switch (slot) {
            case HEAD -> new ModIdentifier("textures/models/armor/space_suit_head.png");
            case CHEST, FEET -> SPACE_SUIT_CHEST_LOCATION;
            case LEGS -> new ModIdentifier("textures/models/armor/space_suit_pants.png");
            default -> throw new IllegalStateException("Unexpected value: " + slot);
            };

            ModelPart root = new ModelPart(Collections.emptyList(), createModelMap(slot, contextModel, texture, SpaceSuitModel.LAYER_LOCATION));
            SpaceSuitModel model = new SpaceSuitModel(root, contextModel, texture);

            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, texture);
        }, ModItems.SPACE_HELMET, ModItems.SPACE_SUIT, ModItems.SPACE_PANTS, ModItems.SPACE_BOOTS);

        // Netherite Space Suit.
        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
            Identifier texture = switch (slot) {
            case HEAD -> new ModIdentifier("textures/models/armor/netherite_space_suit_head.png");
            case CHEST, FEET -> NETHERITE_SPACE_SUIT_CHEST_LOCATION;
            case LEGS -> new ModIdentifier("textures/models/armor/netherite_space_suit_pants.png");
            default -> throw new IllegalStateException("Unexpected value: " + slot);
            };

            ModelPart root = new ModelPart(Collections.emptyList(), createModelMap(slot, contextModel, texture, SpaceSuitModel.LAYER_LOCATION));
            SpaceSuitModel model = new SpaceSuitModel(root, contextModel, texture);

            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, texture);
        }, ModItems.NETHERITE_SPACE_HELMET, ModItems.NETHERITE_SPACE_SUIT, ModItems.NETHERITE_SPACE_PANTS, ModItems.NETHERITE_SPACE_BOOTS);

        // Jet Suit.
        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
            Identifier texture = switch (slot) {
            case HEAD, CHEST, FEET -> JET_SUIT_CHEST_LOCATION;
            case LEGS -> new ModIdentifier("textures/models/armor/jet_suit_pants.png");
            default -> throw new IllegalStateException("Unexpected value: " + slot);
            };

            ModelPart root = new ModelPart(Collections.emptyList(), createModelMap(slot, contextModel, texture, JetSuitModel.LAYER_LOCATION));
            JetSuitModel model = new JetSuitModel(root, contextModel, texture);

            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, texture);
        }, ModItems.JET_SUIT_SPACE_HELMET, ModItems.JET_SUIT, ModItems.JET_SUIT_PANTS, ModItems.JET_SUIT_BOOTS);
    }

    private static Map<String, ModelPart> createModelMap(EquipmentSlot slot, BipedEntityModel<LivingEntity> contextModel, Identifier headTexture, EntityModelLayer entityLayer) {
        Map<String, ModelPart> map = new HashMap<>();

        EntityModelLoader modelLoader = MinecraftClient.getInstance().getEntityModelLoader();
        ModelPart layer;
        SpaceSuitModel model = null;
        SpaceSuitLegsModel legsModel = null;

        switch (slot) {
        case HEAD, CHEST, FEET -> {
            layer = modelLoader.getModelPart(entityLayer);
            model = new SpaceSuitModel(layer, contextModel, headTexture);
        }
        case LEGS -> {
            layer = modelLoader.getModelPart(SpaceSuitLegsModel.LAYER_LOCATION);
            legsModel = new SpaceSuitLegsModel(layer, contextModel, headTexture);
        }
        default -> throw new IllegalStateException("Unexpected value: " + slot);
        }

        switch (slot) {
        case HEAD -> {
            map.put("head", model.head);
        }
        case CHEST -> {
            map.put("body", model.body);
            map.put("right_arm", model.rightArm);
            map.put("left_arm", model.leftArm);
        }
        case LEGS -> {
            map.put("right_leg", legsModel.rightLeg);
            map.put("left_leg", legsModel.leftLeg);
        }
        case FEET -> {
            map.put("right_leg", model.rightLeg);
            map.put("left_leg", model.leftLeg);
        }
        default -> {

        }
        }

        ModelPart empty = new ModelPart(Collections.emptyList(), Collections.emptyMap());

        // Add empty model parts as default values.
        map.putIfAbsent("head", empty);
        map.putIfAbsent("hat", empty);
        map.putIfAbsent("body", empty);
        map.putIfAbsent("right_arm", empty);
        map.putIfAbsent("left_arm", empty);
        map.putIfAbsent("right_leg", empty);
        map.putIfAbsent("left_leg", empty);
        return map;
    }
}