package earth.terrarium.adastra.client.renderers.blocks.machines;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.client.renderers.blocks.base.CustomGeoBlockRenderer;
import earth.terrarium.adastra.common.blockentities.machines.HydraulicPressBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import software.bernie.geckolib.cache.object.BakedGeoModel;

public class HydraulicPressBlockEntityRenderer extends CustomGeoBlockRenderer<HydraulicPressBlockEntity> {
    private final ItemRenderer itemRenderer;

    public HydraulicPressBlockEntityRenderer(RegistryEntry<Block> block) {
        super(block);
        itemRenderer = Minecraft.getInstance().getItemRenderer();
    }

    @Override
    public void postRender(PoseStack poseStack, HydraulicPressBlockEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.postRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        try (var ignored = new CloseablePoseStack(poseStack)) {
            ItemStack stack = animatable.getItem(1);
            if (stack.isEmpty()) return;
            boolean isBlock = stack.getItem() instanceof BlockItem;
            poseStack.translate(0, isBlock ? 0.575 : 0.46f, 0);
            poseStack.scale(0.55f, 0.55f, 0.55f);
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, Minecraft.getInstance().level, 0);
        }
    }
}
