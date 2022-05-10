package net.mrscauthd.beyond_earth.client.renderer.entity.vehicles.rockets.tier_4;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.mrscauthd.beyond_earth.client.renderer.entity.vehicles.VehicleEntityRenderer;

@Environment(EnvType.CLIENT)
public class RocketItemRendererTier4 implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    @Override
    public void render(ItemStack stack, Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        VehicleEntityRenderer.renderItem(RocketEntityRendererTier4.TEXTURE, RocketEntityModelTier4.LAYER_LOCATION, matrices, vertexConsumers, light, overlay);
    }
}