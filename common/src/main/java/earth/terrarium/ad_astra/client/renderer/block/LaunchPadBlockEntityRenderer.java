package earth.terrarium.ad_astra.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.blocks.launchpad.LaunchPadBlockEntity;
import earth.terrarium.ad_astra.client.AdAstraClient;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class LaunchPadBlockEntityRenderer extends BlockEntityWithoutLevelRenderer implements BlockEntityRenderer<LaunchPadBlockEntity> {

    public static final ResourceLocation LAUNCH_PAD_MODEL = new ModResourceLocation("block/launch_pad");

    public LaunchPadBlockEntityRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    public LaunchPadBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this();
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        matrices.pushPose();
        AdAstraClient.renderBlock(LAUNCH_PAD_MODEL, matrices, vertexConsumers, light, overlay);
        matrices.popPose();
    }

    @Override
    public void render(LaunchPadBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        matrices.pushPose();
        AdAstraClient.renderBlock(LAUNCH_PAD_MODEL, matrices, vertexConsumers, light, overlay);
        matrices.popPose();

    }
}