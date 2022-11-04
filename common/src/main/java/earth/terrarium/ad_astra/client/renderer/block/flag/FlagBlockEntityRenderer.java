package earth.terrarium.ad_astra.client.renderer.block.flag;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import earth.terrarium.ad_astra.blocks.flags.FlagBlock;
import earth.terrarium.ad_astra.blocks.flags.FlagBlockEntity;
import earth.terrarium.ad_astra.client.AdAstraClient;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.Registry;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

@ParametersAreNonnullByDefault
@Environment(EnvType.CLIENT)
public class FlagBlockEntityRenderer implements BlockEntityRenderer<FlagBlockEntity> {

    public FlagBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    public static RenderType getRenderLayer(@Nullable GameProfile profile) {
        if (profile == null) {
            // Default zombie head texture on the flag if the player head can not be found.
            return RenderType.entityCutoutNoCullZOffset(new ResourceLocation("textures/entity/zombie/zombie.png"));
        }
        Minecraft minecraftClient = Minecraft.getInstance();
        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraftClient.getSkinManager().getInsecureSkinInformation(profile);
        if (map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
            return RenderType.entityTranslucent(minecraftClient.getSkinManager().registerTexture(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN));
        }
        return RenderType.entityCutoutNoCull(DefaultPlayerSkin.getDefaultSkin(UUIDUtil.getOrCreatePlayerUUID(profile)));
    }

    @Override
    public void render(FlagBlockEntity entity, float tickDelta, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (entity.getBlockState().getValue(FlagBlock.HALF).equals(DoubleBlockHalf.LOWER)) {
            ResourceLocation flagTexture = new ModResourceLocation("block/flag/" + Registry.BLOCK.getKey(entity.getBlockState().getBlock()).getPath());
            poseStack.pushPose();

//			switch (entity.getCachedState().get(FlagBlock.FACING)) {
//			case NORTH -> {
//				poseStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(0));
//				poseStack.translate(0.0f, 0.5f, 0.0f);
//			}
//			case EAST -> {
//				poseStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(270));
//				poseStack.translate(0.0f, 0.5f, -1.0f);
//			}
//			case SOUTH -> {
//				poseStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
//				poseStack.translate(-1.0f, 0.5f, -1.0f);
//			}
//			case WEST -> {
//				poseStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
//				poseStack.translate(-1.0f, 0.5f, 0.0f);
//			}
//			default -> throw new IllegalArgumentException("Unexpected value: " + entity.getCachedState().get(FlagBlock.FACING));
//			}

            poseStack.translate(0.5D, 1D, 0.5D);
            // TODO: Chech if this works
            poseStack.mulPose(Vector3f.YP.rotationDegrees(-entity.getBlockState().getValue(FlagBlock.FACING).toYRot()));
            poseStack.translate(-0.5D, -1D, -0.5D);

            AdAstraClient.renderBlock(flagTexture, poseStack, buffer, packedLight, packedOverlay);

            poseStack.popPose();
        } else {
            ModelPart headModel = createHeadModel();
            ModelPart secondHeadModel = createHeadModel();

            VertexConsumer vertexConsumer = buffer.getBuffer(getRenderLayer(entity.getOwner()));

            int pivotY = 15;
            float flip = (float) Math.toRadians(180);
            float quarterTurn = (float) Math.toRadians(90);

            // Front image of player head
            poseStack.pushPose();
            switch (entity.getBlockState().getValue(FlagBlock.FACING)) {
                // TODO: Check if this works
                case NORTH -> {
                    poseStack.translate(-0.78f, 0.3f, 0.0f);
                    headModel.loadPose(PartPose.offsetAndRotation(13, pivotY, 8, 0, 0, flip));
                }
                case EAST -> {
                    poseStack.translate(0.0f, 0.3f, -0.78f);
                    headModel.loadPose(PartPose.offsetAndRotation(8, pivotY, 13, 0, quarterTurn, flip));
                }
                case SOUTH -> {
                    poseStack.translate(0.78f, 0.3f, 0.0f);
                    headModel.loadPose(PartPose.offsetAndRotation(11, pivotY, 8, 0, 0, flip));
                }
                case WEST -> {
                    poseStack.translate(0.0f, 0.3f, 0.78f);
                    headModel.loadPose(PartPose.offsetAndRotation(8, pivotY, 11, 0, quarterTurn, flip));
                }
                default ->
                        throw new IllegalArgumentException("Unexpected value: " + entity.getBlockState().getValue(FlagBlock.FACING));
            }
            headModel.render(poseStack, vertexConsumer, packedLight, packedOverlay);
            poseStack.popPose();

            poseStack.pushPose();
            // Back image of player head
            switch (entity.getBlockState().getValue(FlagBlock.FACING)) {
                case NORTH -> {
                    poseStack.translate(-0.78f, 0.3f, 0.0f);
                    secondHeadModel.loadPose(PartPose.offsetAndRotation(5, pivotY, 7.999f, 0, flip, flip));
                }
                case EAST -> {
                    poseStack.translate(0.0f, 0.3f, -0.78f);
                    secondHeadModel.loadPose(PartPose.offsetAndRotation(8.001f, pivotY, 5, 0, -quarterTurn, flip));
                }
                case SOUTH -> {
                    poseStack.translate(0.78f, 0.3f, 0.0f);
                    secondHeadModel.loadPose(PartPose.offsetAndRotation(3, pivotY, 7.999f, 0, flip, flip));
                }
                case WEST -> {
                    poseStack.translate(0.0f, 0.3f, 0.78f);
                    secondHeadModel.loadPose(PartPose.offsetAndRotation(8.001f, pivotY, 3, 0, -quarterTurn, flip));
                }
                default ->
                        throw new IllegalArgumentException("Unexpected value: " + entity.getBlockState().getValue(FlagBlock.FACING));
            }
            secondHeadModel.render(poseStack, vertexConsumer, packedLight, packedOverlay);
            poseStack.popPose();
        }
    }

    ModelPart createHeadModel() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 8).addBox(0, 0, 0, 8.0f, 8.0f, 0.005f), PartPose.ZERO);
        modelPartData.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 8).addBox(0, 0, 0, 8.0f, 8.0f, 0.005f), PartPose.ZERO);
        LayerDefinition texturedModelData = LayerDefinition.create(modelData, 64, 64);
        return texturedModelData.bakeRoot();
    }
}