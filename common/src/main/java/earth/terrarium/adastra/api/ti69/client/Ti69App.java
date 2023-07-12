package earth.terrarium.adastra.api.ti69.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public interface Ti69App {
    /**
     * Renders the contents of the app
     *
     * @param pose         The pose stack
     * @param bufferSource The buffer source
     * @param matrix4f     The matrix
     * @param font         The font
     * @param level        The client level
     * @param rightHanded  Whether the player is right-handed
     */
    void render(PoseStack pose, MultiBufferSource bufferSource, Matrix4f matrix4f, Font font, ClientLevel level, boolean rightHanded);

    /**
     * @return The background color of the app
     */
    int color();

    /**
     * Renders an icon
     *
     * @param matrix4f      The matrix
     * @param icon          The icon texture
     * @param x             The x position
     * @param y             The y position
     * @param uOffset       The u offset
     * @param vOffset       The v offset
     * @param uWidth        The u width
     * @param vHeight       The v height
     * @param textureWidth  The texture width
     * @param textureHeight The texture height
     */
    default void renderIcon(Matrix4f matrix4f, ResourceLocation icon, int x, int y, int uOffset, int vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight) {
        int x2 = x + uWidth;
        int y2 = y + vHeight;
        float minU = (float) uOffset / (float) textureWidth;
        float maxU = ((float) uOffset + (float) uWidth) / (float) textureWidth;
        float minV = (float) vOffset / (float) textureHeight;
        float maxV = ((float) vOffset + (float) vHeight) / (float) textureHeight;

        RenderSystem.setShaderTexture(0, icon);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix4f, (float) x, (float) y, 0).uv(minU, minV).endVertex();
        bufferBuilder.vertex(matrix4f, (float) x, (float) y2, 0).uv(minU, maxV).endVertex();
        bufferBuilder.vertex(matrix4f, (float) x2, (float) y2, 0).uv(maxU, maxV).endVertex();
        bufferBuilder.vertex(matrix4f, (float) x2, (float) y, 0).uv(maxU, minV).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
    }

    /**
     * Renders the time
     *
     * @param bufferSource The buffer source
     * @param matrix4f     The matrix
     * @param font         The font
     * @param level        The client level
     */
    default void renderTime(MultiBufferSource bufferSource, Matrix4f matrix4f, Font font, ClientLevel level) {
        double ratio = 1000.0 / 60.0;
        int dayTime = (int) ((level.getDayTime() + 6000L) % 12000L);
        boolean isPm = (int) ((level.getDayTime() + 6000L) % 24000L) >= 12000;
        int hours = dayTime / 1000 == 0 ? 12 : dayTime / 1000;
        int minutes = (int) ((dayTime % 1000) / ratio);
        String timeText = hours + ":" + (minutes < 10 ? "0" + minutes : minutes) + (isPm ? " PM" : " AM");
        font.drawInBatch(timeText, 0.0f, 5.0f, 0xFFFFFF, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0xFFFFFF, LightTexture.FULL_BRIGHT);
    }
}
