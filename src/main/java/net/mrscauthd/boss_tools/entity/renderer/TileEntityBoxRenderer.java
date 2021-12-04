package net.mrscauthd.boss_tools.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

import net.minecraft.client.settings.GraphicsFanciness;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.machines.OxygenBubbleDistributorBlock;

@OnlyIn(Dist.CLIENT)
public class TileEntityBoxRenderer extends TileEntityRenderer<OxygenBubbleDistributorBlock.CustomTileEntity> {

    public static TextureAtlasSprite atlass = null;

    public TileEntityBoxRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public boolean isGlobalRenderer(OxygenBubbleDistributorBlock.CustomTileEntity te) {
        return true;
    }

	@Override
	public void render(OxygenBubbleDistributorBlock.CustomTileEntity tileEntityIn, float partialTicks, MatrixStack matrix, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

		if (tileEntityIn.isWorkingAreaVisible()) {
			IVertexConsumer builder = (IVertexConsumer) bufferIn.getBuffer(RenderType.getLines());
			AxisAlignedBB workingArea = tileEntityIn.getWorkingArea(BlockPos.ZERO, tileEntityIn.getRange());

			Matrix3f normal = matrix.getLast().getNormal();
			Matrix4f matrix4f = matrix.getLast().getMatrix();

			float startX = (float) workingArea.minX - 0.005F;
			float topY = (float) workingArea.minY - 0.005F;
			float startZ = (float) workingArea.minZ - 0.005F;

			float endX = (float) workingArea.maxX + 0.005F;
			float botY = (float) workingArea.maxY + 0.005F;
			float endZ = (float) workingArea.maxZ + 0.005F;

			//255 is default
			int rl = 78;
			int gl = 197;
			int bl = 231;

			int r = 255;
			int g = 255;
			int b = 255;

			//Bottom frame
			drawShapeOutline(builder, matrix4f, normal, startX, botY, startZ,   endX, botY, startZ, rl, gl, bl);
			drawShapeOutline(builder, matrix4f, normal, startX, botY,   endZ,   endX, botY,   endZ, rl, gl, bl);
			drawShapeOutline(builder, matrix4f, normal, startX, botY, startZ, startX, botY,   endZ, rl, gl, bl);
			drawShapeOutline(builder, matrix4f, normal,   endX, botY, startZ,   endX, botY,   endZ, rl, gl, bl);

			//Top frame
			drawShapeOutline(builder, matrix4f, normal, startX, topY, startZ,   endX, topY, startZ, rl, gl, bl);
			drawShapeOutline(builder, matrix4f, normal, startX, topY,   endZ,   endX, topY,   endZ, rl, gl, bl);
			drawShapeOutline(builder, matrix4f, normal, startX, topY, startZ, startX, topY,   endZ, rl, gl, bl);
			drawShapeOutline(builder, matrix4f, normal,   endX, topY, startZ,   endX, topY,   endZ, rl, gl, bl);

			//Vertical lines
			drawShapeOutline(builder, matrix4f, normal, startX, botY, startZ, startX, topY, startZ, rl, gl, bl);
			drawShapeOutline(builder, matrix4f, normal, startX, botY,   endZ, startX, topY,   endZ, rl, gl, bl);
			drawShapeOutline(builder, matrix4f, normal,   endX, botY, startZ,   endX, topY, startZ, rl, gl, bl);
			drawShapeOutline(builder, matrix4f, normal,   endX, botY,   endZ,   endX, topY,   endZ, rl, gl, bl);

			drawSurfaces(bufferIn,matrix4f, normal, startX, startZ, endX, endZ, botY, topY,r,g,b);
		}

	}

    private void drawShapeOutline(IVertexConsumer builder, Matrix4f matrix, Matrix3f normal, float x1, float y1, float z1, float x2, float y2, float z2, int r, int g, int b) {
        float nX = (float)(x2 - x1);
        float nY = (float)(y2 - y1);
        float nZ = (float)(z2 - z1);
        float sqrt = (float) Math.sqrt(nX * nX + nY * nY + nZ * nZ);
        nX = nX / sqrt;
        nY = nY / sqrt;
        nZ = nZ / sqrt;

        builder.pos(matrix, x1, y1, z1).color(r, g, b, 0xFF).normal(normal, nX, nY, nZ).endVertex();
        builder.pos(matrix, x2, y2, z2).color(r, g, b, 0xFF).normal(normal, nX, nY, nZ).endVertex();
    }

    private void drawSurfaces(IRenderTypeBuffer buffer, Matrix4f matrix, Matrix3f normal, float startX, float startZ, float endX, float endZ, float botY, float topY, int r, int g, int b) {
        IVertexConsumer builder;
        GraphicsFanciness graphicsFanciness = Minecraft.getInstance().gameSettings.graphicFanciness;

        if (graphicsFanciness == GraphicsFanciness.FABULOUS) {
            builder = (IVertexConsumer) buffer.getBuffer(RenderType.getTranslucentMovingBlock());
        } else {
            builder = (IVertexConsumer) buffer.getBuffer(RenderType.getTranslucentNoCrumbling());
        }

        if (atlass == null) {
            atlass = Minecraft.getInstance().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(new ResourceLocation("boss_tools", "entities/tile_entity_box_oxygen_generator"));
        }

        float maxU = atlass.getMaxU();
        float minU = atlass.getMinU();
        float maxV = atlass.getMaxV();
        float minV = atlass.getMinV();

        //Down
        builder.pos(matrix, startX, botY, startZ).color(r, g, b, 0xAA).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, -1, 0).endVertex();
        builder.pos(matrix,   endX, botY, startZ).color(r, g, b, 0xAA).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, -1, 0).endVertex();
        builder.pos(matrix,   endX, botY,   endZ).color(r, g, b, 0xAA).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, -1, 0).endVertex();
        builder.pos(matrix, startX, botY,   endZ).color(r, g, b, 0xAA).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, -1, 0).endVertex();

        //Top
        builder.pos(matrix,   endX, topY, startZ).color(r, g, b, 0xAA).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 1, 0).endVertex();
        builder.pos(matrix, startX, topY, startZ).color(r, g, b, 0xAA).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 1, 0).endVertex();
        builder.pos(matrix, startX, topY,   endZ).color(r, g, b, 0xAA).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 1, 0).endVertex();
        builder.pos(matrix,   endX, topY,   endZ).color(r, g, b, 0xAA).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 1, 0).endVertex();

        //North
        builder.pos(matrix, startX, botY, startZ).color(r, g, b, 0xAA).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 0, -1).endVertex();
        builder.pos(matrix, startX, topY, startZ).color(r, g, b, 0xAA).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 0, -1).endVertex();
        builder.pos(matrix,   endX, topY, startZ).color(r, g, b, 0xAA).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 0, -1).endVertex();
        builder.pos(matrix,   endX, botY, startZ).color(r, g, b, 0xAA).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 0, -1).endVertex();

        //South
        builder.pos(matrix,   endX, botY,   endZ).color(r, g, b, 0xAA).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 0, 1).endVertex();
        builder.pos(matrix,   endX, topY,   endZ).color(r, g, b, 0xAA).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 0, 1).endVertex();
        builder.pos(matrix, startX, topY,   endZ).color(r, g, b, 0xAA).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 0, 1).endVertex();
        builder.pos(matrix, startX, botY,   endZ).color(r, g, b, 0xAA).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 0, 1).endVertex();

        //West
        builder.pos(matrix, startX, botY,   endZ).color(r, g, b, 0xAA).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, -1, 0, 0).endVertex();
        builder.pos(matrix, startX, topY,   endZ).color(r, g, b, 0xAA).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, -1, 0, 0).endVertex();
        builder.pos(matrix, startX, topY, startZ).color(r, g, b, 0xAA).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, -1, 0, 0).endVertex();
        builder.pos(matrix, startX, botY, startZ).color(r, g, b, 0xAA).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, -1, 0, 0).endVertex();

        //East
        builder.pos(matrix,   endX, botY, startZ).color(r, g, b, 0xAA).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 1, 0, 0).endVertex();
        builder.pos(matrix,   endX, topY, startZ).color(r, g, b, 0xAA).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 1, 0, 0).endVertex();
        builder.pos(matrix,   endX, topY,   endZ).color(r, g, b, 0xAA).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 1, 0, 0).endVertex();
        builder.pos(matrix,   endX, botY,   endZ).color(r, g, b, 0xAA).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 1, 0, 0).endVertex();

        //Inside

        //Down
        builder.pos(matrix,   endX, botY, startZ).color(r, g, b, 0xAA).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, -1, 0).endVertex();
        builder.pos(matrix, startX, botY, startZ).color(r, g, b, 0xAA).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, -1, 0).endVertex();
        builder.pos(matrix, startX, botY,   endZ).color(r, g, b, 0xAA).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, -1, 0).endVertex();
        builder.pos(matrix,   endX, botY,   endZ).color(r, g, b, 0xAA).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, -1, 0).endVertex();

        //Top
        builder.pos(matrix, startX, topY, startZ).color(r, g, b, 0xAA).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 1, 0).endVertex();
        builder.pos(matrix,   endX, topY, startZ).color(r, g, b, 0xAA).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 1, 0).endVertex();
        builder.pos(matrix,   endX, topY,   endZ).color(r, g, b, 0xAA).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 1, 0).endVertex();
        builder.pos(matrix, startX, topY,   endZ).color(r, g, b, 0xAA).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 1, 0).endVertex();

        //North
        builder.pos(matrix,   endX, botY, startZ).color(r, g, b, 0xAA).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 0, -1).endVertex();
        builder.pos(matrix,   endX, topY, startZ).color(r, g, b, 0xAA).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 0, -1).endVertex();
        builder.pos(matrix, startX, topY, startZ).color(r, g, b, 0xAA).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 0, -1).endVertex();
        builder.pos(matrix, startX, botY, startZ).color(r, g, b, 0xAA).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 0, -1).endVertex();

        //South
        builder.pos(matrix, startX, botY,   endZ).color(r, g, b, 0xAA).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 0, 1).endVertex();
        builder.pos(matrix, startX, topY,   endZ).color(r, g, b, 0xAA).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 0, 1).endVertex();
        builder.pos(matrix,   endX, topY,   endZ).color(r, g, b, 0xAA).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 0, 1).endVertex();
        builder.pos(matrix,   endX, botY,   endZ).color(r, g, b, 0xAA).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 0, 0, 1).endVertex();

        //West
        builder.pos(matrix, endX, botY,   endZ).color(r, g, b, 0xAA).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, -1, 0, 0).endVertex();
        builder.pos(matrix, endX, topY,   endZ).color(r, g, b, 0xAA).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, -1, 0, 0).endVertex();
        builder.pos(matrix, endX, topY, startZ).color(r, g, b, 0xAA).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, -1, 0, 0).endVertex();
        builder.pos(matrix, endX, botY, startZ).color(r, g, b, 0xAA).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, -1, 0, 0).endVertex();

        //East
        builder.pos(matrix,   startX, botY, startZ).color(r, g, b, 0xAA).tex(minU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 1, 0, 0).endVertex();
        builder.pos(matrix,   startX, topY, startZ).color(r, g, b, 0xAA).tex(minU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 1, 0, 0).endVertex();
        builder.pos(matrix,   startX, topY,   endZ).color(r, g, b, 0xAA).tex(maxU, maxV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 1, 0, 0).endVertex();
        builder.pos(matrix,   startX, botY,   endZ).color(r, g, b, 0xAA).tex(maxU, minV).overlay(OverlayTexture.NO_OVERLAY).lightmap(240).normal(normal, 1, 0, 0).endVertex();
    }
}
