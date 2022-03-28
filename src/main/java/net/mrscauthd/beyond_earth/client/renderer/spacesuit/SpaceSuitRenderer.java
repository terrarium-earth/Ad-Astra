package net.mrscauthd.beyond_earth.client.renderer.spacesuit;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.items.armour.NetheriteSpaceSuit;
import net.mrscauthd.beyond_earth.registry.ModArmour;
import net.mrscauthd.beyond_earth.util.ModIdentifier;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SpaceSuitRenderer {

    @Environment(EnvType.CLIENT)
    public static void register() {

        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {

            Map<String, ModelPart> map = createModelMap(slot);

            String appendedPathText = stack.getItem() instanceof NetheriteSpaceSuit ? "netherite_" : "";

            Identifier texture = switch (slot) {
                case HEAD -> new ModIdentifier("textures/models/armor/" + appendedPathText + "space_suit_head.png");
                case CHEST, FEET -> new ModIdentifier("textures/models/armor/" + appendedPathText + "space_suit.png");
                case LEGS -> new ModIdentifier("textures/models/armor/" + appendedPathText + "space_suit_legs.png");
                default -> throw new IllegalStateException("Unexpected value: " + slot);
            };

            ModelPart model = new ModelPart(Collections.emptyList(), map);
            SpaceSuitModel.SPACE_SUIT_P1<LivingEntity> armourModel = new SpaceSuitModel.SPACE_SUIT_P1<>(model);
            armourModel.model = contextModel;
            armourModel.stack = stack;

            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armourModel, texture);

        }, ArrayUtils.addAll(ModArmour.SPACE_SUIT_SET, ModArmour.NETHERITE_SPACE_SUIT_SET));
    }

    @Environment(EnvType.CLIENT)
    private static Map<String, ModelPart> createModelMap(EquipmentSlot slot) {
        Map<String, ModelPart> map = new HashMap<>();

        EntityModelLoader modelLoader = MinecraftClient.getInstance().getEntityModelLoader();
        ModelPart layer = switch (slot) {
            case HEAD, CHEST, FEET -> modelLoader.getModelPart(SpaceSuitModel.SPACE_SUIT_P1.LAYER_LOCATION);
            case LEGS -> modelLoader.getModelPart(SpaceSuitModel.SPACE_SUIT_P2.LAYER_LOCATION);
            default -> throw new IllegalStateException("Unexpected value: " + slot);
        };

        switch (slot) {
            case HEAD -> map.put("head", new SpaceSuitModel.SPACE_SUIT_P1<>(layer).head);
            case CHEST -> {
                map.put("body", new SpaceSuitModel.SPACE_SUIT_P1<>(layer).body);
                map.put("right_arm", new SpaceSuitModel.SPACE_SUIT_P1<>(layer).rightArm);
                map.put("left_arm", new SpaceSuitModel.SPACE_SUIT_P1<>(layer).leftArm);
            }
            case LEGS -> {
                map.put("right_leg", new SpaceSuitModel.SPACE_SUIT_P2<>(layer).rightLeg);
                map.put("left_leg", new SpaceSuitModel.SPACE_SUIT_P2<>(layer).leftLeg);
            }
            case FEET -> {
                map.put("right_leg", new SpaceSuitModel.SPACE_SUIT_P1<>(layer).rightLeg);
                map.put("left_leg", new SpaceSuitModel.SPACE_SUIT_P1<>(layer).leftLeg);
            }
        }

        ModelPart empty = new ModelPart(Collections.emptyList(), Collections.emptyMap());

        // Add empty model parts as default values.
        map.putIfAbsent("head", empty);
        map.putIfAbsent("body", empty);
        map.putIfAbsent("right_arm", empty);
        map.putIfAbsent("left_arm", empty);
        map.putIfAbsent("right_leg", empty);

        map.putIfAbsent("left_leg", empty);
        return map;
    }
}
