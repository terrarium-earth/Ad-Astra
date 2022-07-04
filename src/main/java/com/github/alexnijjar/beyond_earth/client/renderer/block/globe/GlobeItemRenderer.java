package com.github.alexnijjar.beyond_earth.client.renderer.block.globe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class GlobeItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        GlobeModel model = GlobeModel.getModel();

        // Constant spin.
        MinecraftClient client = MinecraftClient.getInstance();
        model.setYaw((client.world.getTime() + (client.isPaused() ? 0 : client.getTickDelta())) / -20);

        GlobeRenderer.render(Registry.ITEM.getId(stack.getItem()), model, Direction.NORTH, matrices, vertexConsumers, light, overlay);
    }
}