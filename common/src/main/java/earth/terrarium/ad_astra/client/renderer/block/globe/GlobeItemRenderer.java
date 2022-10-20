package earth.terrarium.ad_astra.client.renderer.block.globe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class GlobeItemRenderer extends BuiltinModelItemRenderer {

	private long prevWorldTime;

	public GlobeItemRenderer() {
		super(MinecraftClient.getInstance().getBlockEntityRenderDispatcher(), MinecraftClient.getInstance().getEntityModelLoader());
	}

	@Override
	public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

		GlobeModel model = GlobeModel.getModel();

		// Constant spin
		MinecraftClient client = MinecraftClient.getInstance();
		float tickDelta = client.getTickDelta();
		model.setYaw(MathHelper.lerp(tickDelta, prevWorldTime, client.world.getTime()) / -20.0f);
		prevWorldTime = client.world.getTime();

		GlobeRenderer.render(Registry.ITEM.getId(stack.getItem()), model, Direction.NORTH, matrices, vertexConsumers, light, overlay);
	}
}