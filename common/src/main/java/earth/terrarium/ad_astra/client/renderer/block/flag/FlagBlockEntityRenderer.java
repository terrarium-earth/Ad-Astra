package earth.terrarium.ad_astra.client.renderer.block.flag;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.ad_astra.blocks.flags.FlagBlock;
import earth.terrarium.ad_astra.blocks.flags.FlagBlockEntity;
import earth.terrarium.ad_astra.client.AdAstraClient;
import earth.terrarium.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.UuidUtil;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class FlagBlockEntityRenderer implements BlockEntityRenderer<FlagBlockEntity> {

    public FlagBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
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
        return RenderLayer.getEntityCutoutNoCull(DefaultSkinHelper.getTexture(UuidUtil.getProfileUuid(profile)));
    }

    @Override
    public void render(FlagBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (entity.getCachedState().get(FlagBlock.HALF).equals(DoubleBlockHalf.LOWER)) {
            Identifier flagTexture = new ModIdentifier("block/flag/" + Registry.BLOCK.getId(entity.getCachedState().getBlock()).getPath());
            matrices.push();

//			switch (entity.getCachedState().get(FlagBlock.FACING)) {
//			case NORTH -> {
//				matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(0));
//				matrices.translate(0.0f, 0.5f, 0.0f);
//			}
//			case EAST -> {
//				matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(270));
//				matrices.translate(0.0f, 0.5f, -1.0f);
//			}
//			case SOUTH -> {
//				matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
//				matrices.translate(-1.0f, 0.5f, -1.0f);
//			}
//			case WEST -> {
//				matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
//				matrices.translate(-1.0f, 0.5f, 0.0f);
//			}
//			default -> throw new IllegalArgumentException("Unexpected value: " + entity.getCachedState().get(FlagBlock.FACING));
//			}

            matrices.translate(0.5D, 1D, 0.5D);
            // TODO: Chech if this works
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-entity.getCachedState().get(FlagBlock.FACING).asRotation()));
            matrices.translate(-0.5D, -1D, -0.5D);

            AdAstraClient.renderBlock(flagTexture, matrices, vertexConsumers, light, overlay);

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
                // TODO: Check if this works
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
                default ->
                        throw new IllegalArgumentException("Unexpected value: " + entity.getCachedState().get(FlagBlock.FACING));
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
                default ->
                        throw new IllegalArgumentException("Unexpected value: " + entity.getCachedState().get(FlagBlock.FACING));
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
}