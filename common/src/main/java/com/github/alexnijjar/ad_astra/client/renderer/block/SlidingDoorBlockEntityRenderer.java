package com.github.alexnijjar.ad_astra.client.renderer.block;

import java.util.List;

import com.github.alexnijjar.ad_astra.blocks.door.SlidingDoorBlock;
import com.github.alexnijjar.ad_astra.blocks.door.SlidingDoorBlockEntity;
import com.github.alexnijjar.ad_astra.registry.ModBlocks;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.BakedModelManagerHelper;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class SlidingDoorBlockEntityRenderer implements BlockEntityRenderer<SlidingDoorBlockEntity> {

    public static final Identifier IRON_SLIDING_DOOR_MODEL = new ModIdentifier("block/door/iron_sliding_door");
    public static final Identifier STEEL_SLIDING_DOOR_MODEL = new ModIdentifier("block/door/steel_sliding_door");
    public static final Identifier DESH_SLIDING_DOOR_MODEL = new ModIdentifier("block/door/desh_sliding_door");
    public static final Identifier OSTRUM_SLIDING_DOOR_MODEL = new ModIdentifier("block/door/ostrum_sliding_door");
    public static final Identifier CALORITE_SLIDING_DOOR_MODEL = new ModIdentifier("block/door/calorite_sliding_door");
    public static final Identifier AIRLOCK_MODEL = new ModIdentifier("block/door/airlock");
    public static final Identifier REINFORCED_DOOR_MODEL = new ModIdentifier("block/door/reinforced_door");

    public static final Identifier IRON_SLIDING_DOOR_MODEL_FLIPPED = new ModIdentifier("block/door/iron_sliding_door_flipped");
    public static final Identifier STEEL_SLIDING_DOOR_MODEL_FLIPPED = new ModIdentifier("block/door/steel_sliding_door_flipped");
    public static final Identifier DESH_SLIDING_DOOR_MODEL_FLIPPED = new ModIdentifier("block/door/desh_sliding_door_flipped");
    public static final Identifier OSTRUM_SLIDING_DOOR_MODEL_FLIPPED = new ModIdentifier("block/door/ostrum_sliding_door_flipped");
    public static final Identifier CALORITE_SLIDING_MODEL_FLIPPED = new ModIdentifier("block/door/calorite_sliding_door_flipped");
    public static final Identifier AIRLOCK_MODEL_FLIPPED = new ModIdentifier("block/door/airlock_flipped");
    public static final Identifier REINFORCED_DOOR_MODEL_FLIPPED = new ModIdentifier("block/door/reinforced_door_flipped");

    public SlidingDoorBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(SlidingDoorBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        float slide = MathHelper.lerp(tickDelta, entity.getPreviousSlideTicks(), entity.getSlideTicks()) / 81.0f;

        Identifier doorModelFlipped;
        Identifier doorModel;
        Block type = entity.getCachedState().getBlock();

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

        Direction degrees = entity.getCachedState().get(SlidingDoorBlock.FACING);

        matrices.push();
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
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(degrees.getOpposite().asRotation()));
        renderDoor((degrees.equals(Direction.WEST) || degrees.equals(Direction.EAST)) ? doorModel : doorModelFlipped, tickDelta, matrices, vertexConsumers, light, overlay);
        matrices.pop();

        matrices.push();
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
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(degrees.getOpposite().asRotation()));
        renderDoor((degrees.equals(Direction.WEST) || degrees.equals(Direction.EAST)) ? doorModelFlipped : doorModel, tickDelta, matrices, vertexConsumers, light, overlay);
        matrices.pop();
    }

    public void renderDoor(Identifier texture, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

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
