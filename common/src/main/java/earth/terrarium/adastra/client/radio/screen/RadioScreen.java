package earth.terrarium.adastra.client.radio.screen;

import com.teamresourceful.resourcefullib.client.screens.BaseCursorScreen;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.config.AdAstraConfigClient;
import earth.terrarium.adastra.client.config.RadioConfig;
import earth.terrarium.adastra.client.radio.audio.RadioHandler;
import earth.terrarium.adastra.client.utils.Debouncer;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.messages.ServerboundRequestStationsPacket;
import earth.terrarium.adastra.common.utils.radio.StationInfo;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class RadioScreen extends BaseCursorScreen {

    private static final Debouncer VOLUME_DEBOUNCER = new Debouncer();
    private static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/radio/ui.png");
    private static final ResourceLocation CLOCK = new ResourceLocation(AdAstra.MOD_ID, "textures/radio/clock.png");
    private static final int WIDTH = 253;
    private static final int HEIGHT = 138;

    private final List<StationInfo> stations = new ArrayList<>();
    private final Map<String, String> stationNames = new HashMap<>();

    @Nullable
    private final BlockPos pos;

    private RadioList list;

    public RadioScreen(@Nullable BlockPos pos) {
        super(CommonComponents.EMPTY);
        this.pos = pos;
        NetworkHandler.CHANNEL.sendToServer(new ServerboundRequestStationsPacket());
    }

    @Override
    protected void init() {
        int left = (this.width - WIDTH) / 2;
        int top = (this.height - HEIGHT) / 2;
        addRenderableWidget(new ImageButton(left + 116, top + 83, 21, 21, 253, 0, 21, TEXTURE, 512, 256, button -> {
            if (RadioConfig.volume >= 100) return;
            RadioConfig.volume = Math.min(100, RadioConfig.volume + (Screen.hasShiftDown() ? 10 : 1));
            VOLUME_DEBOUNCER.debounce(RadioScreen::saveConfigOnThread, 5000);
        }));
        addRenderableWidget(new ImageButton(left + 116, top + 105, 21, 21, 274, 0, 21, TEXTURE, 512, 256, button -> {
            if (RadioConfig.volume <= 0) return;
            RadioConfig.volume = Math.max(0, RadioConfig.volume - (Screen.hasShiftDown() ? 10 : 1));
            VOLUME_DEBOUNCER.debounce(RadioScreen::saveConfigOnThread, 5000);
        }));

        this.list = addRenderableWidget(new RadioList(left + 149, top + 84, this.pos));
        if (!this.stations.isEmpty()) {
            this.list.update(this.stations, RadioHandler.getPlaying());
        }
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(graphics);
        int left = (this.width - WIDTH) / 2;
        int top = (this.height - HEIGHT) / 2;

        graphics.blit(TEXTURE, left, top, 0, 0, WIDTH, HEIGHT, 512, 256);
        renderClock(graphics, left + 29, top + 92);
        graphics.drawString(font, RadioConfig.volume + "%", left + 102 - font.width(RadioConfig.volume + "%"), top + 114, 0x189418);
        graphics.drawString(font, "Day " + getDayTime() / 24000L, left + 16, top + 114, 0x189418);

        String playing = RadioHandler.getPlaying();
        if (playing != null && stationNames.containsKey(playing.toLowerCase(Locale.ROOT))) {
            renderScrollingString(graphics, font, Component.literal(stationNames.get(playing.toLowerCase(Locale.ROOT))), left + 65, top + 37, left + 188, top + 46, 0x189418);
        }

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    protected static void renderScrollingString(GuiGraphics graphics, Font font, Component text, int minX, int minY, int maxX, int maxY, int color) {
        int i = font.width(text);
        int k = maxX - minX;
        if (i > k) {
            int l = i - k;
            double d = (double) Util.getMillis() / 1000.0;
            double e = Math.max((double) l * 0.5, 3.0);
            double f = Math.sin(1.5707963267948966 * Math.cos(6.283185307179586 * d / e)) / 2.0 + 0.5;
            double g = Mth.lerp(f, 0.0, l);
            graphics.enableScissor(minX, minY, maxX, maxY);
            graphics.drawString(font, text, minX - (int) g, (minY + maxY - 9) / 2 + 1, color);
            graphics.disableScissor();
        } else {
            graphics.drawCenteredString(font, text, (minX + maxX) / 2, (minY + maxY - 9) / 2 + 1, color);
        }
    }

    public void renderClock(GuiGraphics graphics, int x, int y) {
        double ratio = 1000.0 / 60.0;

        int dayTime = (int) ((getDayTime() + 6000L) % 12000L);
        boolean isPm = (int) ((getDayTime() + 6000L) % 24000L) >= 12000;
        int hours = dayTime / 1000 == 0 ? 12 : dayTime / 1000;
        int minutes = (int) ((dayTime % 1000) / ratio);

        int firstHour = hours / 10;
        int secondHour = hours % 10;

        int firstMinute = minutes / 10;
        int secondMinute = minutes % 10;

        graphics.blit(CLOCK, x, y, 0, (firstHour % 5) * 13, 8, 13, 64, 64);
        graphics.blit(CLOCK, x + 8, y, (int) (secondHour / 5f) * 8, (secondHour % 5) * 13, 8, 13, 64, 64);
        graphics.blit(CLOCK, x + 16, y, 39, 0, 5, 13, 64, 64);
        graphics.blit(CLOCK, x + 21, y, (int) (firstMinute / 5f) * 8, (firstMinute % 5) * 13, 8, 13, 64, 64);
        graphics.blit(CLOCK, x + 29, y, (int) (secondMinute / 5f) * 8, (secondMinute % 5) * 13, 8, 13, 64, 64);

        graphics.blit(CLOCK, x + 37, y, 42, isPm ? 0 : 13, 22, 13, 64, 64);
    }

    private static long getDayTime() {
        if (Minecraft.getInstance().level == null) return 0;
        return Minecraft.getInstance().level.getDayTime();
    }

    private static void saveConfigOnThread() {
        Minecraft.getInstance().tell(() -> AdAstra.CONFIGURATOR.saveConfig(AdAstraConfigClient.class));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public static void handleStationUpdates(List<StationInfo> stations) {
        if (Minecraft.getInstance().screen instanceof RadioScreen screen) {
            screen.list.update(stations, RadioHandler.getPlaying());
            screen.stations.clear();
            screen.stationNames.clear();
            for (StationInfo station : stations) {
                screen.stationNames.put(station.url().toLowerCase(Locale.ROOT), station.name());
                screen.stations.add(station);
            }
        }
    }
}
