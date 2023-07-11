package earth.terrarium.adastra.client.ti69.apps;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.ti69.client.Ti69App;
import earth.terrarium.adastra.client.ti69.Ti69Renderer;
import earth.terrarium.adastra.client.utils.ClientData;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.constants.PlanetConstants;
import earth.terrarium.adastra.common.handlers.PlanetData;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class SensorApp implements Ti69App {
    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "sensor");

    @Override
    public void render(PoseStack pose, MultiBufferSource bufferSource, Matrix4f matrix4f, Font font, ClientLevel level, boolean rightHanded) {
        PlanetData data = ClientData.localData;
        if (data == null) return;
        this.renderTime(bufferSource, matrix4f, font, level);

        Component oxygen = data.oxygen() ? ConstantComponents.OXYGEN_TRUE : ConstantComponents.OXYGEN_FALSE;
        Component temperature = Component.translatable("text.adastra.temperature", data.temperature());
        Component gravity = Component.translatable("text.adastra.gravity", Math.round(data.gravity() * PlanetConstants.EARTH_GRAVITY * 1000) / 1000f);
        font.drawInBatch(oxygen, 12.0f, 17.0f, 0xFFFFFF, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0xFFFFFF, LightTexture.FULL_BRIGHT);
        font.drawInBatch(temperature, 12.0f, 30.0f, 0xFFFFFF, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0xFFFFFF, LightTexture.FULL_BRIGHT);
        font.drawInBatch(gravity, 12.0f, 43.0f, 0xFFFFFF, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0xFFFFFF, LightTexture.FULL_BRIGHT);

        this.renderIcon(matrix4f, Ti69Renderer.ICONS, 0, 17, 24, 0, 8, 8, 32, 32);
        this.renderIcon(matrix4f, Ti69Renderer.ICONS, 0, 30, 24, 8, 8, 8, 32, 32);
        this.renderIcon(matrix4f, Ti69Renderer.ICONS, 0, 43, 24, 16, 8, 8, 32, 32);
    }

    @Override
    public int color() {
        return 0xff3aabc3;
    }
}
