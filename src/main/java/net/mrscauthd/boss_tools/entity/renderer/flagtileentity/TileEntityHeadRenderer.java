package net.mrscauthd.boss_tools.entity.renderer.flagtileentity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.flag.FlagBlock;
import net.mrscauthd.boss_tools.flag.FlagTileEntity;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class TileEntityHeadRenderer implements BlockEntityRenderer<FlagTileEntity> {

    public TileEntityHeadRenderer(BlockEntityRendererProvider.Context context) {

    }

    private static final Map<FlagBlock.ISkullType, TileEntityHeadModel> MODELS = Util.make(Maps.newHashMap(), (p_209262_0_) -> {

        Map map = Map.of("head", new TileEntityHeadModel(Minecraft.getInstance().getEntityModels().bakeLayer(TileEntityHeadModel.LAYER_LOCATION)).head);
        ModelPart modelPart = new ModelPart(Collections.emptyList(), map);

        TileEntityHeadModel genericheadmodel = new TileEntityHeadModel(modelPart);

        p_209262_0_.put(FlagBlock.Types.PLAYER, genericheadmodel);
    });

    private static final Map<FlagBlock.ISkullType, ResourceLocation> SKINS = Util.make(Maps.newHashMap(), (p_209263_0_) -> {
        p_209263_0_.put(FlagBlock.Types.PLAYER, DefaultPlayerSkin.getDefaultSkin());
    });

    public static Map<SkullBlock.Type, TileEntityHeadModel> createSkullRenderers(EntityModelSet p_173662_) {
        ImmutableMap.Builder<SkullBlock.Type, TileEntityHeadModel> builder = ImmutableMap.builder();
        builder.put(SkullBlock.Types.PLAYER, new TileEntityHeadModel(p_173662_.bakeLayer(ModelLayers.PLAYER_HEAD)));
        return builder.build();
    }


    @Override
    public void render(FlagTileEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (tileEntityIn.getBlockState().getValue(FlagBlock.HALF) == DoubleBlockHalf.UPPER) {

            BlockState blockstate = tileEntityIn.getBlockState();
            boolean flag = blockstate.getBlock() instanceof FlagBlock;
            Direction direction = flag ? blockstate.getValue(FlagBlock.FACING) : null;
            render(direction, blockstate.getValue(FlagBlock.FACING).toYRot(), ((FlagBlock) blockstate.getBlock()).getSkullType(), tileEntityIn.getPlayerProfile(), 0, matrixStackIn, bufferIn, combinedLightIn);
        }
    }

    public static void render(@Nullable Direction directionIn, float p_228879_1_, FlagBlock.ISkullType skullType, @Nullable GameProfile gameProfileIn, float animationProgress, PoseStack matrixStackIn, MultiBufferSource buffer, int combinedLight) {
        TileEntityHeadModel genericheadmodel = MODELS.get(skullType);
            matrixStackIn.pushPose();
            if (directionIn == null) {
                matrixStackIn.translate(0.5D, 0.0D, 0.5D);
            } else {
                float f = 0.25F;
                matrixStackIn.translate((double) (0.5F - (float) directionIn.getStepX() * 0.25F), 0.25D, (double) (0.5F - (float) directionIn.getStepZ() * 0.25F));
            }

            matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
            VertexConsumer ivertexbuilder = buffer.getBuffer(getRenderType(skullType, gameProfileIn));
            genericheadmodel.setupAnim(animationProgress, p_228879_1_, 0.0F);
            genericheadmodel.renderToBuffer(matrixStackIn, ivertexbuilder, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.popPose();
    }

    private static RenderType getRenderType(FlagBlock.ISkullType skullType, @Nullable GameProfile gameProfileIn) {
        ResourceLocation resourcelocation = SKINS.get(skullType);
        if (skullType == FlagBlock.Types.PLAYER && gameProfileIn != null) {
            Minecraft minecraft = Minecraft.getInstance();
            Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().getInsecureSkinInformation(gameProfileIn);
            return map.containsKey(MinecraftProfileTexture.Type.SKIN) ? RenderType.entityTranslucent(minecraft.getSkinManager().registerTexture(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN)) : RenderType.entityCutoutNoCull(DefaultPlayerSkin.getDefaultSkin(Player.createPlayerUUID(gameProfileIn)));
        } else {
            return RenderType.entityCutoutNoCullZOffset(resourcelocation);
        }
    }
}
