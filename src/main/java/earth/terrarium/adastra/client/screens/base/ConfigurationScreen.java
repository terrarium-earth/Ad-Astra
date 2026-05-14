package earth.terrarium.adastra.client.screens.base;

import earth.terrarium.adastra.client.components.machines.SidedConfigWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

import java.util.function.Consumer;

public interface ConfigurationScreen {

    SidedConfigWidget getSideConfigWidget();

    default boolean canConfigure() {
        return getSideConfigWidget().isActive();
    }

    static void ifPresent(Consumer<ConfigurationScreen> consumer) {
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof ConfigurationScreen configScreen && configScreen.canConfigure()) {
            consumer.accept(configScreen);
        }
    }

    static boolean isConfigurable() {
        return Minecraft.getInstance().screen instanceof ConfigurationScreen screen && screen.canConfigure();
    }
}
