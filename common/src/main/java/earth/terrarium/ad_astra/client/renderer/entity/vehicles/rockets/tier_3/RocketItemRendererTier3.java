package earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_3;

import earth.terrarium.ad_astra.client.renderer.entity.vehicles.VehicleEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public class RocketItemRendererTier3 extends BuiltinModelItemRenderer {

    public RocketItemRendererTier3() {
        super(MinecraftClient.getInstance().getBlockEntityRenderDispatcher(), MinecraftClient.getInstance().getEntityModelLoader());
    }

    @Override
    public void render(ItemStack stack, Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        VehicleEntityRenderer.renderRocketItem(RocketEntityRendererTier3.TEXTURE, RocketEntityModelTier3.LAYER_LOCATION, matrices, vertexConsumers, light, overlay);
    }
}