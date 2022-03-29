package net.mrscauthd.beyond_earth.client.renderer.globe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.registry.Registry;
import net.mrscauthd.beyond_earth.blocks.globes.GlobeBlock;
import net.mrscauthd.beyond_earth.blocks.globes.GlobeBlockEntity;

@Environment(EnvType.CLIENT)
public class GlobeBlockEntityRenderer implements BlockEntityRenderer<GlobeBlockEntity> {

    @SuppressWarnings("unused")
    public GlobeBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(GlobeBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        GlobeModel model = GlobeModel.getModel();

        MinecraftClient client = MinecraftClient.getInstance();
        float yaw = entity.oldYaw;
        if (!client.isPaused()) {
            yaw -= entity.getAngularVelocity() / 15;
            entity.oldYaw = yaw;
        }
        model.globe.getChild("planet").yaw = yaw;

        BlockState state = entity.getCachedState();
        GlobeRenderer.render(Registry.BLOCK.getId(state.getBlock()), model, state.get(GlobeBlock.FACING), matrices, vertexConsumers, light, overlay);
    }
}
