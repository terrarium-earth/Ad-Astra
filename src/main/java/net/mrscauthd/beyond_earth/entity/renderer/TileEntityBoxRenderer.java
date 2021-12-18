package net.mrscauthd.beyond_earth.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;

import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.machines.tile.OxygenBubbleDistributorBlockEntity;

@OnlyIn(Dist.CLIENT)
public class TileEntityBoxRenderer implements BlockEntityRenderer<OxygenBubbleDistributorBlockEntity> {

    public static TextureAtlasSprite atlass = null;

    public TileEntityBoxRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public boolean shouldRenderOffScreen(OxygenBubbleDistributorBlockEntity p_112306_) {
        return true;
    }

    @Override
    public void render(OxygenBubbleDistributorBlockEntity tileEntityIn, float partialTicks, PoseStack matrix, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (tileEntityIn.isWorkingAreaVisible()) {
            VertexConsumer builder = bufferIn.getBuffer(RenderType.lines());
            AABB workingArea = tileEntityIn.getWorkingArea(BlockPos.ZERO, tileEntityIn.getRange());

            Matrix3f normal = matrix.last().normal();
            Matrix4f matrix4f = matrix.last().pose();

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

    private void drawShapeOutline(VertexConsumer builder, Matrix4f matrix, Matrix3f normal, float x1, float y1, float z1, float x2, float y2, float z2, int r, int g, int b) {
        float nX = (float)(x2 - x1);
        float nY = (float)(y2 - y1);
        float nZ = (float)(z2 - z1);
        float sqrt = (float) Math.sqrt(nX * nX + nY * nY + nZ * nZ);
        nX = nX / sqrt;
        nY = nY / sqrt;
        nZ = nZ / sqrt;

        builder.vertex(matrix, x1, y1, z1).color(r, g, b, 0xFF).normal(normal, nX, nY, nZ).endVertex();
        builder.vertex(matrix, x2, y2, z2).color(r, g, b, 0xFF).normal(normal, nX, nY, nZ).endVertex();
    }

    private void drawSurfaces(MultiBufferSource buffer, Matrix4f matrix, Matrix3f normal, float startX, float startZ, float endX, float endZ, float botY, float topY, int r, int g, int b) {
        VertexConsumer builder;
        Minecraft minecraft = Minecraft.getInstance();
		GraphicsStatus graphicsFanciness = minecraft.options.graphicsMode;

        if (graphicsFanciness == GraphicsStatus.FABULOUS) {
            builder = buffer.getBuffer(RenderType.translucentMovingBlock());
        } else {
            builder = buffer.getBuffer(RenderType.translucentNoCrumbling());
        }

        if (atlass == null) {
            atlass = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation(BeyondEarthMod.MODID, "entities/tile_entity_box_oxygen_generator"));
        }

        float maxU = atlass.getU1();
        float minU = atlass.getU0();
        float maxV = atlass.getV1();
        float minV = atlass.getV0();

        //Down
        builder.vertex(matrix, startX, botY, startZ).color(r, g, b, 0xAA).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, -1, 0).endVertex();
        builder.vertex(matrix,   endX, botY, startZ).color(r, g, b, 0xAA).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, -1, 0).endVertex();
        builder.vertex(matrix,   endX, botY,   endZ).color(r, g, b, 0xAA).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, -1, 0).endVertex();
        builder.vertex(matrix, startX, botY,   endZ).color(r, g, b, 0xAA).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, -1, 0).endVertex();

        //Top
        builder.vertex(matrix,   endX, topY, startZ).color(r, g, b, 0xAA).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 1, 0).endVertex();
        builder.vertex(matrix, startX, topY, startZ).color(r, g, b, 0xAA).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 1, 0).endVertex();
        builder.vertex(matrix, startX, topY,   endZ).color(r, g, b, 0xAA).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 1, 0).endVertex();
        builder.vertex(matrix,   endX, topY,   endZ).color(r, g, b, 0xAA).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 1, 0).endVertex();

        //North
        builder.vertex(matrix, startX, botY, startZ).color(r, g, b, 0xAA).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, -1).endVertex();
        builder.vertex(matrix, startX, topY, startZ).color(r, g, b, 0xAA).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, -1).endVertex();
        builder.vertex(matrix,   endX, topY, startZ).color(r, g, b, 0xAA).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, -1).endVertex();
        builder.vertex(matrix,   endX, botY, startZ).color(r, g, b, 0xAA).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, -1).endVertex();

        //South
        builder.vertex(matrix,   endX, botY,   endZ).color(r, g, b, 0xAA).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, 1).endVertex();
        builder.vertex(matrix,   endX, topY,   endZ).color(r, g, b, 0xAA).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, 1).endVertex();
        builder.vertex(matrix, startX, topY,   endZ).color(r, g, b, 0xAA).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, 1).endVertex();
        builder.vertex(matrix, startX, botY,   endZ).color(r, g, b, 0xAA).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, 1).endVertex();

        //West
        builder.vertex(matrix, startX, botY,   endZ).color(r, g, b, 0xAA).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, -1, 0, 0).endVertex();
        builder.vertex(matrix, startX, topY,   endZ).color(r, g, b, 0xAA).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, -1, 0, 0).endVertex();
        builder.vertex(matrix, startX, topY, startZ).color(r, g, b, 0xAA).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, -1, 0, 0).endVertex();
        builder.vertex(matrix, startX, botY, startZ).color(r, g, b, 0xAA).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, -1, 0, 0).endVertex();

        //East
        builder.vertex(matrix,   endX, botY, startZ).color(r, g, b, 0xAA).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 1, 0, 0).endVertex();
        builder.vertex(matrix,   endX, topY, startZ).color(r, g, b, 0xAA).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 1, 0, 0).endVertex();
        builder.vertex(matrix,   endX, topY,   endZ).color(r, g, b, 0xAA).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 1, 0, 0).endVertex();
        builder.vertex(matrix,   endX, botY,   endZ).color(r, g, b, 0xAA).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 1, 0, 0).endVertex();

        //Inside

        //Down
        builder.vertex(matrix,   endX, botY, startZ).color(r, g, b, 0xAA).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, -1, 0).endVertex();
        builder.vertex(matrix, startX, botY, startZ).color(r, g, b, 0xAA).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, -1, 0).endVertex();
        builder.vertex(matrix, startX, botY,   endZ).color(r, g, b, 0xAA).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, -1, 0).endVertex();
        builder.vertex(matrix,   endX, botY,   endZ).color(r, g, b, 0xAA).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, -1, 0).endVertex();

        //Top
        builder.vertex(matrix, startX, topY, startZ).color(r, g, b, 0xAA).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 1, 0).endVertex();
        builder.vertex(matrix,   endX, topY, startZ).color(r, g, b, 0xAA).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 1, 0).endVertex();
        builder.vertex(matrix,   endX, topY,   endZ).color(r, g, b, 0xAA).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 1, 0).endVertex();
        builder.vertex(matrix, startX, topY,   endZ).color(r, g, b, 0xAA).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 1, 0).endVertex();

        //North
        builder.vertex(matrix,   endX, botY, startZ).color(r, g, b, 0xAA).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, -1).endVertex();
        builder.vertex(matrix,   endX, topY, startZ).color(r, g, b, 0xAA).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, -1).endVertex();
        builder.vertex(matrix, startX, topY, startZ).color(r, g, b, 0xAA).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, -1).endVertex();
        builder.vertex(matrix, startX, botY, startZ).color(r, g, b, 0xAA).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, -1).endVertex();

        //South
        builder.vertex(matrix, startX, botY,   endZ).color(r, g, b, 0xAA).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, 1).endVertex();
        builder.vertex(matrix, startX, topY,   endZ).color(r, g, b, 0xAA).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, 1).endVertex();
        builder.vertex(matrix,   endX, topY,   endZ).color(r, g, b, 0xAA).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, 1).endVertex();
        builder.vertex(matrix,   endX, botY,   endZ).color(r, g, b, 0xAA).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 0, 0, 1).endVertex();

        //West
        builder.vertex(matrix, endX, botY,   endZ).color(r, g, b, 0xAA).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, -1, 0, 0).endVertex();
        builder.vertex(matrix, endX, topY,   endZ).color(r, g, b, 0xAA).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, -1, 0, 0).endVertex();
        builder.vertex(matrix, endX, topY, startZ).color(r, g, b, 0xAA).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, -1, 0, 0).endVertex();
        builder.vertex(matrix, endX, botY, startZ).color(r, g, b, 0xAA).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, -1, 0, 0).endVertex();

        //East
        builder.vertex(matrix,   startX, botY, startZ).color(r, g, b, 0xAA).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 1, 0, 0).endVertex();
        builder.vertex(matrix,   startX, topY, startZ).color(r, g, b, 0xAA).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 1, 0, 0).endVertex();
        builder.vertex(matrix,   startX, topY,   endZ).color(r, g, b, 0xAA).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 1, 0, 0).endVertex();
        builder.vertex(matrix,   startX, botY,   endZ).color(r, g, b, 0xAA).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(240).normal(normal, 1, 0, 0).endVertex();
    }
}
