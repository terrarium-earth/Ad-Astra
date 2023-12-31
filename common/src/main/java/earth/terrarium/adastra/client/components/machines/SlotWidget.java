package earth.terrarium.adastra.client.components.machines;

import com.teamresourceful.resourcefullib.client.components.CursorWidget;
import com.teamresourceful.resourcefullib.client.screens.CursorScreen;
import earth.terrarium.adastra.common.menus.configuration.SlotConfiguration;
import net.minecraft.client.gui.GuiGraphics;

public class SlotWidget extends ConfigurationWidget implements CursorWidget {

    public SlotWidget(SlotConfiguration configuration) {
        super(configuration, 16, 16);
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {

    }

    @Override
    public CursorScreen.Cursor getCursor() {
        return CursorScreen.Cursor.DEFAULT;
    }
}
