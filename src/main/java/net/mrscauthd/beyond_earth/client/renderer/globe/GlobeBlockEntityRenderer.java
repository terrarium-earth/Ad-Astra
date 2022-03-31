package net.mrscauthd.beyond_earth.client.renderer.globe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.mrscauthd.beyond_earth.BeyondEarth;
import net.mrscauthd.beyond_earth.blocks.globes.GlobeBlock;
import net.mrscauthd.beyond_earth.blocks.globes.GlobeBlockEntity;

@Environment(EnvType.CLIENT)
public class GlobeBlockEntityRenderer implements BlockEntityRenderer<GlobeBlockEntity> {

    GlobeModel model;

    public GlobeBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.model = new GlobeModel(ctx.getLayerModelPart(GlobeModel.LAYER_LOCATION));
    }

    @Override
    public void render(GlobeBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        float lerp = MathHelper.lerp(tickDelta, entity.getCachedYaw(), entity.getYaw());

        // Testing, Temporary.
        if (entity.getAngularVelocity() > 0) {
            BeyondEarth.LOGGER.info(lerp + "");
        }

        this.model.globe.getChild("planet").yaw = lerp;

        BlockState state = entity.getCachedState();
        GlobeRenderer.render(Registry.BLOCK.getId(state.getBlock()), this.model, state.get(GlobeBlock.FACING), matrices, vertexConsumers, light, overlay);
    }
}
