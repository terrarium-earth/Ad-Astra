package earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_3;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.VehicleEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class RocketItemRendererTier3 extends BlockEntityWithoutLevelRenderer {

    public RocketItemRendererTier3() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, TransformType mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        VehicleEntityRenderer.renderRocketItem(RocketEntityRendererTier3.TEXTURE, RocketEntityModelTier3.LAYER_LOCATION, matrices, vertexConsumers, light, overlay);
    }
}