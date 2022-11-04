package earth.terrarium.ad_astra.client.renderer.block.globe;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class GlobeItemRenderer extends BlockEntityWithoutLevelRenderer {

    private long prevWorldTime;

    public GlobeItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType mode, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {

        GlobeModel model = GlobeModel.getModel();

        // Constant spin
        Minecraft client = Minecraft.getInstance();
        float tickDelta = client.getFrameTime();
        model.setYaw(Mth.lerp(tickDelta, prevWorldTime, client.level.getGameTime()) / -20.0f);
        prevWorldTime = client.level.getGameTime();

        GlobeRenderer.render(Registry.ITEM.getKey(stack.getItem()), model, Direction.NORTH, poseStack, buffer, packedLight, packedOverlay);
    }
}