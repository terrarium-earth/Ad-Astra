package earth.terrarium.adastra.api.client.events;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class AdAstraClientEvents {

    private static final List<RenderSolarSystemEvent> RENDER_SOLAR_SYSTEM_LISTENERS = new ArrayList<>();

    @FunctionalInterface
    public interface RenderSolarSystemEvent {

        void render(GuiGraphics graphics, @Nullable ResourceLocation solarSystem, int width, int height);

        static void register(RenderSolarSystemEvent listener) {
            RENDER_SOLAR_SYSTEM_LISTENERS.add(listener);
        }

        @ApiStatus.Internal
        static void fire(GuiGraphics graphics, @Nullable ResourceLocation solarSystem, int width, int height) {
            for (var listener : RENDER_SOLAR_SYSTEM_LISTENERS) {
                listener.render(graphics, solarSystem, width, height);
            }
        }
    }
}
