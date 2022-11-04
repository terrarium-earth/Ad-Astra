package earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_2;

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
public class RocketItemRendererTier2 extends BlockEntityWithoutLevelRenderer {

    public RocketItemRendererTier2() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, TransformType mode, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        VehicleEntityRenderer.renderRocketItem(RocketEntityRendererTier2.TEXTURE, RocketEntityModelTier2.LAYER_LOCATION, poseStack, buffer, packedLight, packedOverlay);
    }
}