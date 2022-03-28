package net.mrscauthd.beyond_earth.client.renderer.globe;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.registry.Registry;
import net.mrscauthd.beyond_earth.blocks.globes.GlobeBlock;
import net.mrscauthd.beyond_earth.blocks.globes.GlobeBlockEntity;

public class GlobeBlockEntityRenderer implements BlockEntityRenderer<GlobeBlockEntity> {


    public GlobeBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(GlobeBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        GlobeModel model = GlobeRenderer.getModel();
        BlockState state = entity.getCachedState();

        model.globe.getChild("planet").yaw = entity.getYaw();
        GlobeRenderer.render(Registry.BLOCK.getId(state.getBlock()), model, state.get(GlobeBlock.FACING), matrices, vertexConsumers, light, overlay);
    }
}
