package net.mrscauthd.boss_tools.entity.renderer.flagtileentity;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.flag.FlagBlock;
import net.mrscauthd.boss_tools.flag.FlagTileEntity;

import javax.annotation.Nullable;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class TileEntityHeadRenderer extends TileEntityRenderer<FlagTileEntity> {

    private static final Map<FlagBlock.ISkullType, TileEntityHeadModel> MODELS = Util.make(Maps.newHashMap(), (p_209262_0_) -> {
        TileEntityHeadModel genericheadmodel1 = new TileEntityHeadModel();
        p_209262_0_.put(FlagBlock.Types.PLAYER, genericheadmodel1);
    });

    private static final Map<FlagBlock.ISkullType, ResourceLocation> SKINS = Util.make(Maps.newHashMap(), (p_209263_0_) -> {
        p_209263_0_.put(FlagBlock.Types.PLAYER, DefaultPlayerSkin.getDefaultSkinLegacy());
    });

    public TileEntityHeadRenderer(TileEntityRendererDispatcher p_i226015_1_) {
        super(p_i226015_1_);
    }

    public void render(FlagTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (tileEntityIn.getBlockState().get(FlagBlock.HALF) == DoubleBlockHalf.UPPER) {

            BlockState blockstate = tileEntityIn.getBlockState();
            boolean flag = blockstate.getBlock() instanceof FlagBlock;
            Direction direction = flag ? blockstate.get(FlagBlock.FACING) : null;
            render(direction, blockstate.get(FlagBlock.FACING).getHorizontalAngle(), ((FlagBlock) blockstate.getBlock()).getSkullType(), tileEntityIn.getPlayerProfile(), 0, matrixStackIn, bufferIn, combinedLightIn);
        }
    }

    public static void render(@Nullable Direction directionIn, float p_228879_1_, FlagBlock.ISkullType skullType, @Nullable GameProfile gameProfileIn, float animationProgress, MatrixStack matrixStackIn, IRenderTypeBuffer buffer, int combinedLight) {
        TileEntityHeadModel genericheadmodel = MODELS.get(skullType);
            matrixStackIn.push();
            if (directionIn == null) {
                matrixStackIn.translate(0.5D, 0.0D, 0.5D);
            } else {
                float f = 0.25F;
                matrixStackIn.translate((double) (0.5F - (float) directionIn.getXOffset() * 0.25F), 0.25D, (double) (0.5F - (float) directionIn.getZOffset() * 0.25F));
            }

            matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
            IVertexBuilder ivertexbuilder = buffer.getBuffer(getRenderType(skullType, gameProfileIn));
            genericheadmodel.func_225603_a_(animationProgress, p_228879_1_, 0.0F);
            genericheadmodel.render(matrixStackIn, ivertexbuilder, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.pop();
    }

    private static RenderType getRenderType(FlagBlock.ISkullType skullType, @Nullable GameProfile gameProfileIn) {
        ResourceLocation resourcelocation = SKINS.get(skullType);
        if (skullType == FlagBlock.Types.PLAYER && gameProfileIn != null) {
            Minecraft minecraft = Minecraft.getInstance();
            Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(gameProfileIn);
            return map.containsKey(MinecraftProfileTexture.Type.SKIN) ? RenderType.getEntityTranslucent(minecraft.getSkinManager().loadSkin(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN)) : RenderType.getEntityCutoutNoCull(DefaultPlayerSkin.getDefaultSkin(PlayerEntity.getUUID(gameProfileIn)));
        } else {
            return RenderType.getEntityCutoutNoCullZOffset(resourcelocation);
        }
    }
}
