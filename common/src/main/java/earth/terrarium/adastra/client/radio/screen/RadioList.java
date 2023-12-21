package earth.terrarium.adastra.client.radio.screen;

import com.google.common.collect.Sets;
import com.teamresourceful.resourcefullib.client.components.selection.ListEntry;
import com.teamresourceful.resourcefullib.client.components.selection.SelectionList;
import com.teamresourceful.resourcefullib.client.scissor.ScissorBoxStack;
import com.teamresourceful.resourcefullib.client.screens.CursorScreen;
import com.teamresourceful.resourcefullib.client.utils.CursorUtils;
import com.teamresourceful.resourcefullib.client.utils.ScreenUtils;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.config.AdAstraConfigClient;
import earth.terrarium.adastra.client.config.RadioConfig;
import earth.terrarium.adastra.client.radio.audio.RadioHandler;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.messages.ServerboundSetStationPacket;
import earth.terrarium.adastra.common.utils.radio.StationInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class RadioList extends SelectionList<RadioList.RadioEntry> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/radio/ui.png");

    private List<StationInfo> stations = new ArrayList<>();
    private String playing = null;

    public RadioList(int x, int y) {
        super(x, y, 91, 41, 12, entry -> {
            if (entry != null) {
                StationInfo info = entry.info;
                if (info == null) {
                    NetworkHandler.CHANNEL.sendToServer(new ServerboundSetStationPacket(""));
                } else if (!Objects.equals(RadioHandler.getPlaying(), info.url())) {
                    NetworkHandler.CHANNEL.sendToServer(new ServerboundSetStationPacket(info.url()));
                }
            }
        }, true);
    }

    public void update(List<StationInfo> stations, String url) {
        this.stations = stations;
        this.playing = url;
        update();
    }

    private void update() {
        RadioEntry selected = null;
        List<RadioEntry> entries = new ArrayList<>();
        Set<String> favoriteUrls = Sets.newHashSet(RadioConfig.favorites);
        List<StationInfo> favorites = new ArrayList<>();
        List<StationInfo> others = new ArrayList<>();

        for (StationInfo station : this.stations) {
            if (favoriteUrls.contains(station.url())) {
                favorites.add(station);
            } else {
                others.add(station);
            }
        }
        favorites.sort(Comparator.comparing(StationInfo::title));
        others.sort(Comparator.comparing(StationInfo::title));

        entries.add(new RadioEntry(null));

        for (StationInfo favorite : favorites) {
            RadioEntry entry = new RadioEntry(favorite);
            if (favorite.url().equals(this.playing)) selected = entry;
            entry.favorite = true;
            entries.add(entry);
        }

        for (StationInfo other : others) {
            RadioEntry entry = new RadioEntry(other);
            if (other.url().equals(this.playing)) selected = entry;
            entries.add(entry);
        }

        updateEntries(entries);
        if (selected != null) {
            setSelected(selected);
        }
    }

    public class RadioEntry extends ListEntry {

        @Nullable
        private final StationInfo info;
        private boolean favorite = false;

        public RadioEntry(@Nullable StationInfo info) {
            this.info = info;
        }

        @Override
        protected void render(@NotNull GuiGraphics graphics, @NotNull ScissorBoxStack stack, int id, int left, int top, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTick, boolean selected) {
            int v = selected ? 66 : hovered ? 42 : 54;
            graphics.blit(TEXTURE, left + 1, top, 253, v, 89, 12, 512, 256);
            int textStart = left + 3;
            if ((favorite || hovered) && this.info != null) {
                String text = favorite ? "★" : "☆";
                textStart = graphics.drawString(Minecraft.getInstance().font, text, textStart, top + 3, favorite ? 0xFFAA00 : 0xFFFFFF) + 2;
            }
            graphics.drawString(Minecraft.getInstance().font, getName(), textStart, top + 3, 0xFFFFFF);

            if (hovered) {
                if (info != null) {
                    ScreenUtils.setTooltip(Component.literal(info.name()));
                }
                CursorUtils.setCursor(true, CursorScreen.Cursor.POINTER);
            }
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            int width = Minecraft.getInstance().font.width(favorite ? "★" : "☆");
            if (info != null && mouseX > 0 && mouseX < width + 3 && mouseY > 0 && mouseY < 12) {
                List<String> favorites = new ArrayList<>(Arrays.asList(RadioConfig.favorites));
                if (favorite) {
                    favorites.remove(info.url());
                } else {
                    favorites.add(info.url());
                }
                RadioConfig.favorites = favorites.toArray(new String[0]);
                AdAstra.CONFIGURATOR.saveConfig(AdAstraConfigClient.class);
                update();
                return true;
            }
            return super.mouseClicked(mouseX, mouseY, button);
        }

        private Component getName() {
            return info == null ? Component.translatable("text.ad_astra.radio.none") : Component.literal(info.title());
        }

        @Override
        public void setFocused(boolean focused) {

        }

        @Override
        public boolean isFocused() {
            return false;
        }
    }
}
