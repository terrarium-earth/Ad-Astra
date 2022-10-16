package com.github.alexnijjar.ad_astra.client.renderer.block.flag;

import com.github.alexnijjar.ad_astra.blocks.flags.FlagBlock;
import com.github.alexnijjar.ad_astra.blocks.flags.FlagBlockEntity;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.BakedModelManagerHelper;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.client.texture.PlayerSkinTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.ChatUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.Vector4f;
import net.minecraft.util.registry.Registry;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class FlagBlockEntityRenderer implements BlockEntityRenderer<FlagBlockEntity> {

	public FlagBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

	@Override
	public void render(FlagBlockEntity entity, float partialTicks, MatrixStack stack, VertexConsumerProvider provider, int light, int overlay) {
		if (entity.getCachedState().get(FlagBlock.HALF).equals(DoubleBlockHalf.LOWER)) {
			Identifier model = new ModIdentifier("block/flag/" + Registry.BLOCK.getId(entity.getCachedState().getBlock()).getPath());
			stack.push();

			stack.translate(0.5D, 0.5D, 0.5D);
			stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(entity.getCachedState().get(FlagBlock.FACING).asRotation()));
			stack.translate(-0.5D, 0, -0.5D);

			renderFlag(model, partialTicks, stack, provider, light, overlay);

			stack.pop();
		} else {
			String url = entity.getUrl();
			if (url != null) {
				renderFullTexture(entity, stack, provider, light, overlay, false);
				renderFullTexture(entity, stack, provider, light, overlay, true);
			} else {
				renderFace(entity, stack, provider, light, overlay, false);
				renderFace(entity, stack, provider, light, overlay, true);
			}
		}
	}

	private static void renderFullTexture(FlagBlockEntity entity, MatrixStack stack, VertexConsumerProvider provider, int light, int overlay, boolean flip) {
		stack.push();
		stack.translate(0.5, 1, 0.5);
		stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(entity.getCachedState().get(FlagBlock.FACING).asRotation()));
		stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
		stack.translate(-1.375, -0.375, flip ? -0.01 : 0.01);

		if (!flip) flipY(stack, 1.25f);

		VertexConsumer vertexConsumer = provider.getBuffer(getFlagImage(entity.getUrl()));
		renderQuad(stack, vertexConsumer, 1.25f, 0.875f, 0, 0, 1f, 1f, light, overlay);
		stack.pop();
	}

	private static void renderFace(FlagBlockEntity entity, MatrixStack stack, VertexConsumerProvider provider, int light, int overlay, boolean flip) {
		stack.push();
		stack.translate(0.5, 1, 0.5);
		stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(entity.getCachedState().get(FlagBlock.FACING).asRotation()));
		stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
		stack.translate(-1, -0.25, flip ? -0.01 : 0.01);

		if (flip) flipY(stack, 0.5f);
		VertexConsumer vertexConsumer = provider.getBuffer(getRenderLayer(entity.getOwner()));
		renderQuad(stack, vertexConsumer, 0.5f, 0.5f, 8f/64f, 8f/64f, 8f/64f, 8f/64f, light, overlay);
		renderQuad(stack, vertexConsumer, 0.5f, 0.5f, 32f/64f, 8f/64f, 8f/64f, 8f/64f, light, overlay);
		stack.pop();
	}

	private static void renderQuad(MatrixStack stack, VertexConsumer consumer, float width, float height, float u, float v, float uWidth, float vHeight, int light, int overlay) {
		consumer.vertex(stack.peek().getModel(), 0, 0, 0).color(255, 255, 255, 255).texture(u, v).overlay(overlay).light(light).normal(0, 0, 1).next();
		consumer.vertex(stack.peek().getModel(), 0, height, 0).color(255, 255, 255, 255).texture(u, v + vHeight).overlay(overlay).light(light).normal(0, 0, 1).next();
		consumer.vertex(stack.peek().getModel(), width, height, 0).color(255, 255, 255, 255).texture(u + uWidth, v + vHeight).overlay(overlay).light(light).normal(0, 0, 1).next();
		consumer.vertex(stack.peek().getModel(), width, 0, 0).color(255, 255, 255, 255).texture(u + uWidth, v).overlay(overlay).light(light).normal(0, 0, 1).next();
	}

	private static void flipY(MatrixStack stack, float width) {
		Vector4f vec3f = new Vector4f(0, 0, 0, 0);
		vec3f.transform(stack.peek().getModel());
		stack.translate(-vec3f.getX(), -vec3f.getY(), -vec3f.getZ());
		stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
		stack.translate(vec3f.getX(), vec3f.getY(), vec3f.getZ());
		stack.translate(-width, 0, 0);
	}

	public static RenderLayer getRenderLayer(GameProfile profile) {
		if (profile == null) {
			// Default zombie head texture on the flag if the player head can not be found.
			return RenderLayer.getEntityCutoutNoCullZOffset(new Identifier("textures/entity/zombie/zombie.png"));
		}
		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraftClient.getSkinProvider().getTextures(profile);
		if (map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
			return RenderLayer.getEntityTranslucent(minecraftClient.getSkinProvider().loadSkin(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN));
		}
		return RenderLayer.getEntityCutoutNoCull(DefaultSkinHelper.getTexture(PlayerEntity.getUuidFromProfile(profile)));
	}

	public static RenderLayer getFlagImage(String url) {
		Identifier id = FlagTexture.getTextureId(url);
		TextureManager manager = MinecraftClient.getInstance().getTextureManager();
		AbstractTexture texture = manager.getOrDefault(id, MissingSprite.getMissingSpriteTexture());
		if (texture == MissingSprite.getMissingSpriteTexture()) manager.registerTexture(id, new FlagTexture(url));
		return RenderLayer.getEntityTranslucent(id);
	}

	public static void renderFlag(Identifier texture, float tickDelta, MatrixStack matrices, VertexConsumerProvider provider, int light, int overlay) {
		MinecraftClient minecraft = MinecraftClient.getInstance();
		BakedModel model = BakedModelManagerHelper.getModel(minecraft.getBakedModelManager(), texture);
		VertexConsumer vertexConsumer1 = provider.getBuffer(RenderLayer.getEntityCutout(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE));
		minecraft.getBlockRenderManager().getModelRenderer().render(matrices.peek(), vertexConsumer1, null, model, 1, 1, 1, light, overlay);
	}
}