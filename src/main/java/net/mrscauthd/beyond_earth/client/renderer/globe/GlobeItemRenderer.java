package net.mrscauthd.beyond_earth.client.renderer.globe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class GlobeItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    private GlobeModel model;

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        // Get model from client.
        if (model == null) {
            model = GlobeRenderer.getModel();
        }

        // Constant spin.
        model.globe.getChild("planet").yaw -= 0.01;

        GlobeRenderer.render(Registry.ITEM.getId(stack.getItem()), model, matrices, vertexConsumers, light, overlay);
    }
}
