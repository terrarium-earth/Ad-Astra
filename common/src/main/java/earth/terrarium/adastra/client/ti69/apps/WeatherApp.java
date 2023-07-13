package earth.terrarium.adastra.client.ti69.apps;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.ti69.client.Ti69App;
import earth.terrarium.adastra.client.ti69.Ti69Renderer;
import earth.terrarium.adastra.client.utils.ClientData;
import earth.terrarium.adastra.common.planets.AdAstraData;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class WeatherApp implements Ti69App {
    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "weather");

    @Override
    public void render(PoseStack pose, MultiBufferSource bufferSource, Matrix4f matrix4f, Font font, ClientLevel level, boolean rightHanded) {
        this.renderTime(bufferSource, matrix4f, font, level);
        short temperature = AdAstraData.planets().get(level.dimension()).temperature();
        // Current weather
        boolean raining = level.isRaining();
        boolean thundering = level.isThundering();
        int uOffset = thundering ? 16 : raining ? 8 : 0;
        this.renderIcon(matrix4f, Ti69Renderer.ICONS, 0, 17, uOffset, 0, 8, 8, 32, 32);

        // Temperature
        Component temperatureText = Component.translatable("text.adastra.weather.temperature", temperature);
        font.drawInBatch(temperatureText, 12.0f, 17.0f, 0xFFFFFF, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0xFFFFFF, LightTexture.FULL_BRIGHT);

        if (ClientData.clearTime > 0) {
            this.renderIcon(matrix4f, Ti69Renderer.ICONS, 0, 30, 16, 0, 8, 8, 32, 32);
            int days = (int) (ClientData.clearTime / 24000L);
            int hours = (int) Math.ceil((double) ClientData.clearTime / 1000L % 24);
            Component clearTime = Component.translatable("text.adastra.weather.temperature.in", days + "d " + hours + "h");
            font.drawInBatch(clearTime, 12.0f, 30.0f, 0xFFFFFF, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0xFFFFFF, LightTexture.FULL_BRIGHT);
        } else {
            if (!thundering && !raining) {
                this.renderIcon(matrix4f, Ti69Renderer.ICONS, 0, 30, ClientData.rainTime > ClientData.thunderTime || ClientData.thundering ? 16 : 8, 0, 8, 8, 32, 32);
            } else {
                this.renderIcon(matrix4f, Ti69Renderer.ICONS, 0, 30, ClientData.thunderTime < ClientData.rainTime && !thundering ? 16 : 0, 0, 8, 8, 32, 32);
            }
            long time = ClientData.thunderTime < ClientData.rainTime && !thundering ? ClientData.thunderTime : ClientData.rainTime;
            int days = (int) (time / 24000L);
            int hours = (int) Math.ceil((double) time / 1000L % 24);
            Component weatherTime = Component.translatable("text.adastra.weather.temperature.in", days + "d " + hours + "h");
            font.drawInBatch(weatherTime, 12.0f, 30.0f, 0xFFFFFF, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0xFFFFFF, LightTexture.FULL_BRIGHT);
        }
    }

    @Override
    public int color() {
        return 0xff2b3fb3;
    }
}
