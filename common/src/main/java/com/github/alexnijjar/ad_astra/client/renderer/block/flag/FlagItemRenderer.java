package com.github.alexnijjar.ad_astra.client.renderer.block.flag;

import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class FlagItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

	@Override
	public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		Identifier model = new ModIdentifier("block/flag/" + Registry.ITEM.getId(stack.getItem()).getPath());
		FlagBlockEntityRenderer.renderFlag(model, MinecraftClient.getInstance().getTickDelta(), matrices, vertexConsumers, light, overlay);
	}
}