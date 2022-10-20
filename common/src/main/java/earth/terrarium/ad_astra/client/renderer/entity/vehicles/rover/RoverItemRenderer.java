package earth.terrarium.ad_astra.client.renderer.entity.vehicles.rover;

import earth.terrarium.ad_astra.client.renderer.entity.vehicles.VehicleEntityRenderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public class RoverItemRenderer extends BuiltinModelItemRenderer {

	public RoverItemRenderer() {
		super(MinecraftClient.getInstance().getBlockEntityRenderDispatcher(), MinecraftClient.getInstance().getEntityModelLoader());
	}

	@Override
	public void render(ItemStack stack, Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		VehicleEntityRenderer.renderRoverItem(RoverEntityRenderer.TEXTURE, RoverEntityModel.LAYER_LOCATION, matrices, vertexConsumers, light, overlay);
	}
}