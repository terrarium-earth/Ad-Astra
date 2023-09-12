package earth.terrarium.adastra.client.renderers.blocks.machines;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.client.renderers.blocks.base.CustomGeoBlockRenderer;
import earth.terrarium.adastra.common.blockentities.machines.TinkerersWorkbenchBlockEntity;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import software.bernie.geckolib.cache.object.BakedGeoModel;

public class TinkerersWorkbenchBlockEntityRenderer extends CustomGeoBlockRenderer<TinkerersWorkbenchBlockEntity> {

    public TinkerersWorkbenchBlockEntityRenderer(RegistryEntry<Block> block) {
        super(block);
    }

    @Override
    public void actuallyRender(PoseStack poseStack, TinkerersWorkbenchBlockEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        int aboveLight = LevelRenderer.getLightColor(animatable.getLevel(), animatable.getBlockPos().above());
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, aboveLight, packedOverlay, red, green, blue, alpha);
    }
}
