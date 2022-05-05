package net.mrscauthd.beyond_earth.client.renderer;

import java.util.Map;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.blocks.flags.FlagBlock;
import net.mrscauthd.beyond_earth.blocks.flags.FlagBlockEntity;

@Environment(EnvType.CLIENT)
public class FlagBlockEntityRenderer implements BlockEntityRenderer<FlagBlockEntity> {

    public FlagBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(FlagBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        ModelPart model = createHeadModel();
        ModelPart model1 = createHeadModel();

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(getRenderLayer(entity.getOwner()));

        int pivotY = 15;
        float flip = (float) Math.toRadians(180);
        float quarterTurn = (float) Math.toRadians(90);

        // Front image of player head.
        switch (entity.getCachedState().get(FlagBlock.FACING)) {
        case NORTH -> model.setTransform(ModelTransform.of(13, pivotY, 8, 0, 0, flip));
        case EAST -> model.setTransform(ModelTransform.of(8, pivotY, 13, 0, quarterTurn, flip));
        case SOUTH -> model.setTransform(ModelTransform.of(11, pivotY, 8, 0, 0, flip));
        case WEST -> model.setTransform(ModelTransform.of(8, pivotY, 11, 0, quarterTurn, flip));
        default -> throw new IllegalArgumentException("Unexpected value: " + entity.getCachedState().get(FlagBlock.FACING));
        }
        model.render(matrices, vertexConsumer, light, overlay);

        // Back image of player head.
        switch (entity.getCachedState().get(FlagBlock.FACING)) {
        case NORTH -> model1.setTransform(ModelTransform.of(5, pivotY, 7.999f, 0, flip, flip));
        case EAST -> model1.setTransform(ModelTransform.of(8.001f, pivotY, 5, 0, -quarterTurn, flip));
        case SOUTH -> model1.setTransform(ModelTransform.of(3, pivotY, 7.999f, 0, flip, flip));
        case WEST -> model1.setTransform(ModelTransform.of(8.001f, pivotY, 3, 0, -quarterTurn, flip));
        default -> throw new IllegalArgumentException("Unexpected value: " + entity.getCachedState().get(FlagBlock.FACING));
        }
        model1.render(matrices, vertexConsumer, light, overlay);
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
        return RenderLayer.getEntityCutoutNoCull(DefaultSkinHelper.getTexture(PlayerEntity.getUuidFromProfile(profile)));
    }
}