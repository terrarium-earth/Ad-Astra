package earth.terrarium.ad_astra.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import earth.terrarium.ad_astra.blocks.door.SlidingDoorBlock;
import earth.terrarium.ad_astra.blocks.door.SlidingDoorBlockEntity;
import earth.terrarium.ad_astra.client.AdAstraClient;
import earth.terrarium.ad_astra.registry.ModBlocks;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;

@Environment(EnvType.CLIENT)
public class SlidingDoorBlockEntityRenderer implements BlockEntityRenderer<SlidingDoorBlockEntity> {

    public static final ResourceLocation IRON_SLIDING_DOOR_MODEL = new ModResourceLocation("block/door/iron_sliding_door");
    public static final ResourceLocation STEEL_SLIDING_DOOR_MODEL = new ModResourceLocation("block/door/steel_sliding_door");
    public static final ResourceLocation DESH_SLIDING_DOOR_MODEL = new ModResourceLocation("block/door/desh_sliding_door");
    public static final ResourceLocation OSTRUM_SLIDING_DOOR_MODEL = new ModResourceLocation("block/door/ostrum_sliding_door");
    public static final ResourceLocation CALORITE_SLIDING_DOOR_MODEL = new ModResourceLocation("block/door/calorite_sliding_door");
    public static final ResourceLocation AIRLOCK_MODEL = new ModResourceLocation("block/door/airlock");
    public static final ResourceLocation REINFORCED_DOOR_MODEL = new ModResourceLocation("block/door/reinforced_door");

    public static final ResourceLocation IRON_SLIDING_DOOR_MODEL_FLIPPED = new ModResourceLocation("block/door/iron_sliding_door_flipped");
    public static final ResourceLocation STEEL_SLIDING_DOOR_MODEL_FLIPPED = new ModResourceLocation("block/door/steel_sliding_door_flipped");
    public static final ResourceLocation DESH_SLIDING_DOOR_MODEL_FLIPPED = new ModResourceLocation("block/door/desh_sliding_door_flipped");
    public static final ResourceLocation OSTRUM_SLIDING_DOOR_MODEL_FLIPPED = new ModResourceLocation("block/door/ostrum_sliding_door_flipped");
    public static final ResourceLocation CALORITE_SLIDING_MODEL_FLIPPED = new ModResourceLocation("block/door/calorite_sliding_door_flipped");
    public static final ResourceLocation AIRLOCK_MODEL_FLIPPED = new ModResourceLocation("block/door/airlock_flipped");
    public static final ResourceLocation REINFORCED_DOOR_MODEL_FLIPPED = new ModResourceLocation("block/door/reinforced_door_flipped");

    public SlidingDoorBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(SlidingDoorBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {

        float slide = Mth.lerp(tickDelta, entity.getPreviousSlideTicks(), entity.getSlideTicks()) / 81.0f;

        ResourceLocation doorModelFlipped;
        ResourceLocation doorModel;
        Block type = entity.getBlockState().getBlock();

        float offset = 0;
        if (type.equals(ModBlocks.IRON_SLIDING_DOOR.get())) {
            doorModel = IRON_SLIDING_DOOR_MODEL;
            doorModelFlipped = IRON_SLIDING_DOOR_MODEL_FLIPPED;
        } else if (type.equals(ModBlocks.STEEL_SLIDING_DOOR.get())) {
            doorModel = STEEL_SLIDING_DOOR_MODEL;
            doorModelFlipped = STEEL_SLIDING_DOOR_MODEL_FLIPPED;
        } else if (type.equals(ModBlocks.DESH_SLIDING_DOOR.get())) {
            doorModel = DESH_SLIDING_DOOR_MODEL;
            doorModelFlipped = DESH_SLIDING_DOOR_MODEL_FLIPPED;
        } else if (type.equals(ModBlocks.OSTRUM_SLIDING_DOOR.get())) {
            doorModel = OSTRUM_SLIDING_DOOR_MODEL;
            doorModelFlipped = OSTRUM_SLIDING_DOOR_MODEL_FLIPPED;
        } else if (type.equals(ModBlocks.CALORITE_SLIDING_DOOR.get())) {
            doorModel = CALORITE_SLIDING_DOOR_MODEL;
            doorModelFlipped = CALORITE_SLIDING_MODEL_FLIPPED;
        } else if (type.equals(ModBlocks.AIRLOCK.get())) {
            doorModel = AIRLOCK_MODEL;
            doorModelFlipped = AIRLOCK_MODEL_FLIPPED;
            slide -= 0.094f;
            offset = 0.155f;
        } else if (type.equals(ModBlocks.REINFORCED_DOOR.get())) {
            doorModel = REINFORCED_DOOR_MODEL_FLIPPED;
            doorModelFlipped = REINFORCED_DOOR_MODEL;
            slide -= 0.094f;
            offset = 0.095f;
        } else {
            return;
        }

        Direction degrees = entity.getBlockState().getValue(SlidingDoorBlock.FACING);

        matrices.pushPose();
        if (degrees.equals(Direction.NORTH)) {
            matrices.translate(-1.5f, 1.0f, 0.42f);
            matrices.translate(-slide, 0.0f, 0.0);
            matrices.translate(offset, 0.0f, 0.0);
        } else if (degrees.equals(Direction.EAST)) {
            matrices.translate(0.38f, 1.0f, 1.0f);
            matrices.translate(0.0f, 0.0f, -slide);
            matrices.translate(0.0f, 0.0f, -offset);
        } else if (degrees.equals(Direction.SOUTH)) {
            matrices.translate(2.5f, 1.0f, 0.56f);
            matrices.translate(slide, 0.0f, 0.0);
            matrices.translate(-offset, 0.0f, 0.0f);
        } else if (degrees.equals(Direction.WEST)) {
            matrices.translate(0.56f, 1.0f, 0.0f);
            matrices.translate(0.0f, 0.0f, slide);
            matrices.translate(0.0f, 0.0f, offset);
        }
        matrices.mulPose(Vector3f.YP.rotationDegrees(degrees.getOpposite().toYRot()));
        AdAstraClient.renderBlock(((degrees.equals(Direction.WEST) || degrees.equals(Direction.EAST)) ? doorModel : doorModelFlipped), matrices, vertexConsumers, light, overlay);
        matrices.popPose();

        matrices.pushPose();
        if (degrees.equals(Direction.NORTH)) {
            matrices.translate(0.0f, 1.0f, 0.42f);
            matrices.translate(slide, 0.0f, 0.0);
            matrices.translate(offset, 0.0f, 0.0);
        } else if (degrees.equals(Direction.EAST)) {
            matrices.translate(0.38f, 1.0f, 2.5f);
            matrices.translate(0.0f, 0.0f, slide);
            matrices.translate(0.0f, 0.0f, -offset);
        } else if (degrees.equals(Direction.SOUTH)) {
            matrices.translate(1.0f, 1.0f, 0.56f);
            matrices.translate(-slide, 0.0f, 0.0);
            matrices.translate(-offset, 0.0f, 0.0f);
        } else if (degrees.equals(Direction.WEST)) {
            matrices.translate(0.56f, 1.0f, -1.5f);
            matrices.translate(0.0f, 0.0f, -slide);
            matrices.translate(0.0f, 0.0f, offset);
        }
        matrices.mulPose(Vector3f.YP.rotationDegrees(degrees.getOpposite().toYRot()));
        AdAstraClient.renderBlock((degrees.equals(Direction.WEST) || degrees.equals(Direction.EAST)) ? doorModelFlipped : doorModel, matrices, vertexConsumers, light, overlay);
        matrices.popPose();
    }
}
