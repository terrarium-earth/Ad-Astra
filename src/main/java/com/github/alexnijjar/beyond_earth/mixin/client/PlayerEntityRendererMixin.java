package com.github.alexnijjar.beyond_earth.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.alexnijjar.beyond_earth.client.renderer.spacesuit.AbstractSpaceSuitModel;
import com.github.alexnijjar.beyond_earth.client.renderer.spacesuit.JetSuitModel;
import com.github.alexnijjar.beyond_earth.client.renderer.spacesuit.SpaceSuitModel;
import com.github.alexnijjar.beyond_earth.client.renderer.spacesuit.SpaceSuitRenderer;
import com.github.alexnijjar.beyond_earth.items.armour.JetSuit;
import com.github.alexnijjar.beyond_earth.items.armour.NetheriteSpaceSuit;
import com.github.alexnijjar.beyond_earth.items.armour.SpaceSuit;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {

    @Inject(method = "renderRightArm", at = @At("HEAD"), cancellable = true)
    public void renderRightArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, CallbackInfo info) {
        if (player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof SpaceSuit spaceSuit) {
            info.cancel();
            this.renderArm(matrices, vertexConsumers, light, player, true);
        }
    }

    @Inject(method = "renderLeftArm", at = @At("HEAD"), cancellable = true)
    public void renderLeftArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, CallbackInfo info) {
        if (player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof SpaceSuit spaceSuit) {
            info.cancel();
            this.renderArm(matrices, vertexConsumers, light, player, false);
        }
    }
    
    // Render space suit arm in first person.
    private void renderArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, boolean right) {
        if (player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof SpaceSuit spaceSuit) {

            PlayerEntityRenderer renderer = (PlayerEntityRenderer) (Object) (this);

            Identifier texture;
            SpaceSuitModel model = null;
            EntityModelLoader modelLoader = MinecraftClient.getInstance().getEntityModelLoader();
            if (spaceSuit instanceof JetSuit) {
                texture = SpaceSuitRenderer.JET_SUIT_CHEST_LOCATION;
                model = new JetSuitModel(modelLoader.getModelPart(JetSuitModel.LAYER_LOCATION), renderer.getModel(), texture);

            } else if (spaceSuit instanceof NetheriteSpaceSuit) {
                texture = SpaceSuitRenderer.NETHERITE_SPACE_SUIT_CHEST_LOCATION;
                model = new SpaceSuitModel(modelLoader.getModelPart(SpaceSuitModel.LAYER_LOCATION), renderer.getModel(), texture);
            } else {
                texture = SpaceSuitRenderer.SPACE_SUIT_CHEST_LOCATION;
                model = new SpaceSuitModel(modelLoader.getModelPart(SpaceSuitModel.LAYER_LOCATION), renderer.getModel(), texture);
            }

            matrices.push();
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(4));
            
            VertexConsumer vertex = AbstractSpaceSuitModel.getVertex(RenderLayer.getEntityCutout(texture), player.getEquippedStack(EquipmentSlot.CHEST).hasEnchantments(), MinecraftClient.getInstance());
            if (right) {
                model.rightArm.render(matrices, vertex, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
            } else {
                model.leftArm.render(matrices, vertex, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
            }
            matrices.pop();
        }
    }
}
