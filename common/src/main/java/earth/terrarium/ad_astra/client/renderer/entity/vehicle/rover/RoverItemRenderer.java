package earth.terrarium.ad_astra.client.renderer.entity.vehicle.rover;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.VehicleRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;


public class RoverItemRenderer extends BlockEntityWithoutLevelRenderer {

    public RoverItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        VehicleRenderer.renderRoverItem(RoverRenderer.TEXTURE, RoverModel.LAYER_LOCATION, poseStack, buffer, packedLight, packedOverlay);
    }
}