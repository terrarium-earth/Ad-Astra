package earth.terrarium.ad_astra.client.renderer.block.globe;

import earth.terrarium.ad_astra.blocks.globes.GlobeBlock;
import earth.terrarium.ad_astra.blocks.globes.GlobeBlockEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class GlobeBlockEntityRenderer implements BlockEntityRenderer<GlobeBlockEntity> {

	public GlobeBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
	}

	@Override
	public void render(GlobeBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

		GlobeModel model = GlobeModel.getModel();

		model.setYaw(MathHelper.lerp(tickDelta, entity.getCachedYaw(), entity.getYaw()));

		BlockState state = entity.getCachedState();
		GlobeRenderer.render(Registry.BLOCK.getId(state.getBlock()), model, state.get(GlobeBlock.FACING), matrices, vertexConsumers, light, overlay);
	}
}