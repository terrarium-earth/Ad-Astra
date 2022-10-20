package earth.terrarium.ad_astra.client.renderer.block.flag;

import earth.terrarium.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class FlagItemRenderer extends BuiltinModelItemRenderer {

	public FlagItemRenderer() {
		super(MinecraftClient.getInstance().getBlockEntityRenderDispatcher(), MinecraftClient.getInstance().getEntityModelLoader());
	}

	@Override
	public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		Identifier model = new ModIdentifier("block/flag/" + Registry.ITEM.getId(stack.getItem()).getPath());
		FlagBlockEntityRenderer.renderFlag(model, MinecraftClient.getInstance().getTickDelta(), matrices, vertexConsumers, light, overlay);
	}
}