package com.github.alexnijjar.ad_astra.client.renderer.block.flag;

import java.util.List;
import java.util.Map;

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
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.DynamicSerializableUuid;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class FlagBlockEntityRenderer implements BlockEntityRenderer<FlagBlockEntity> {

	public FlagBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
	}

	@Override
	public void render(FlagBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		if (entity.getCachedState().get(FlagBlock.HALF).equals(DoubleBlockHalf.LOWER)) {
			Identifier model = new ModIdentifier("block/flag/" + Registry.BLOCK.getId(entity.getCachedState().getBlock()).getPath());
			matrices.push();

			switch (entity.getCachedState().get(FlagBlock.FACING)) {
			case NORTH -> {
				matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(0));
				matrices.translate(0.0f, 0.5f, 0.0f);
			}
			case EAST -> {
				matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(270));
				matrices.translate(0.0f, 0.5f, -1.0f);
			}
			case SOUTH -> {
				matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
				matrices.translate(-1.0f, 0.5f, -1.0f);
			}
			case WEST -> {
				matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
				matrices.translate(-1.0f, 0.5f, 0.0f);
			}
			default -> throw new IllegalArgumentException("Unexpected value: " + entity.getCachedState().get(FlagBlock.FACING));
			}

			renderFlag(model, tickDelta, matrices, vertexConsumers, light, overlay);

			matrices.pop();
		} else {
			ModelPart headModel = createHeadModel();
			ModelPart secondHeadModel = createHeadModel();

			VertexConsumer vertexConsumer = vertexConsumers.getBuffer(getRenderLayer(entity.getOwner()));

			int pivotY = 15;
			float flip = (float) Math.toRadians(180);
			float quarterTurn = (float) Math.toRadians(90);

			// Front image of player head
			matrices.push();
			switch (entity.getCachedState().get(FlagBlock.FACING)) {
			case NORTH -> {
				matrices.translate(-0.78f, 0.3f, 0.0f);
				headModel.setTransform(ModelTransform.of(13, pivotY, 8, 0, 0, flip));
			}
			case EAST -> {
				matrices.translate(0.0f, 0.3f, -0.78f);
				headModel.setTransform(ModelTransform.of(8, pivotY, 13, 0, quarterTurn, flip));
			}
			case SOUTH -> {
				matrices.translate(0.78f, 0.3f, 0.0f);
				headModel.setTransform(ModelTransform.of(11, pivotY, 8, 0, 0, flip));
			}
			case WEST -> {
				matrices.translate(0.0f, 0.3f, 0.78f);
				headModel.setTransform(ModelTransform.of(8, pivotY, 11, 0, quarterTurn, flip));
			}
			default -> throw new IllegalArgumentException("Unexpected value: " + entity.getCachedState().get(FlagBlock.FACING));
			}
			headModel.render(matrices, vertexConsumer, light, overlay);
			matrices.pop();

			matrices.push();
			// Back image of player head
			switch (entity.getCachedState().get(FlagBlock.FACING)) {
			case NORTH -> {
				matrices.translate(-0.78f, 0.3f, 0.0f);
				secondHeadModel.setTransform(ModelTransform.of(5, pivotY, 7.999f, 0, flip, flip));
			}
			case EAST -> {
				matrices.translate(0.0f, 0.3f, -0.78f);
				secondHeadModel.setTransform(ModelTransform.of(8.001f, pivotY, 5, 0, -quarterTurn, flip));
			}
			case SOUTH -> {
				matrices.translate(0.78f, 0.3f, 0.0f);
				secondHeadModel.setTransform(ModelTransform.of(3, pivotY, 7.999f, 0, flip, flip));
			}
			case WEST -> {
				matrices.translate(0.0f, 0.3f, 0.78f);
				secondHeadModel.setTransform(ModelTransform.of(8.001f, pivotY, 3, 0, -quarterTurn, flip));
			}
			default -> throw new IllegalArgumentException("Unexpected value: " + entity.getCachedState().get(FlagBlock.FACING));
			}
			secondHeadModel.render(matrices, vertexConsumer, light, overlay);
			matrices.pop();
		}
	}

	ModelPart createHeadModel() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 8).cuboid(0, 0, 0, 8.0f, 8.0f, 0.005f), ModelTransform.NONE);
		modelPartData.addChild("hat", ModelPartBuilder.create().uv(32, 8).cuboid(0, 0, 0, 8.0f, 8.0f, 0.005f), ModelTransform.NONE);
		TexturedModelData texturedModelData = TexturedModelData.of(modelData, 64, 64);
		return texturedModelData.createModel();
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
		return RenderLayer.getEntityCutoutNoCull(DefaultSkinHelper.getTexture(DynamicSerializableUuid.getUuidFromProfile(profile)));
	}

	public static void renderFlag(Identifier texture, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

		MinecraftClient client = MinecraftClient.getInstance();
		BakedModelManager manager = client.getBakedModelManager();
		BakedModel model = BakedModelManagerHelper.getModel(manager, texture);

		VertexConsumer vertexConsumer1 = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE));
		List<BakedQuad> quads1 = model.getQuads(null, null, client.world.random);
		MatrixStack.Entry entry1 = matrices.peek();

		for (BakedQuad quad : quads1) {
			vertexConsumer1.quad(entry1, quad, 1, 1, 1, light, overlay);
		}
	}
}