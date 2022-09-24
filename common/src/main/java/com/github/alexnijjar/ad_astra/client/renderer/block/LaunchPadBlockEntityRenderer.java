package com.github.alexnijjar.ad_astra.client.renderer.block;

import com.github.alexnijjar.ad_astra.blocks.launchpad.LaunchPadBlockEntity;
import com.github.alexnijjar.ad_astra.client.AdAstraClient;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class LaunchPadBlockEntityRenderer extends BuiltinModelItemRenderer, BlockEntityRenderer<LaunchPadBlockEntity> {
	public static final Identifier LAUNCH_PAD_MODEL = new ModIdentifier("block/launch_pad");

	public LaunchPadBlockEntityRenderer() {
	}

	public LaunchPadBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
	}

	@Override
	public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		AdAstraClient.renderBlock(LAUNCH_PAD_MODEL, MinecraftClient.getInstance().getTickDelta(), matrices, vertexConsumers, light, overlay);
		matrices.pop();
	}

	@Override
	public void render(LaunchPadBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		AdAstraClient.renderBlock(LAUNCH_PAD_MODEL, MinecraftClient.getInstance().getTickDelta(), matrices, vertexConsumers, light, overlay);
		matrices.pop();

	}
}