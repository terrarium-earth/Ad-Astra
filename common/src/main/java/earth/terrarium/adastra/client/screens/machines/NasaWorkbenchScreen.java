package earth.terrarium.adastra.client.screens.machines;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.base.MachineScreen;
import earth.terrarium.adastra.common.menus.machines.NasaWorkbenchMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class NasaWorkbenchScreen extends MachineScreen<NasaWorkbenchMenu, NasaWorkbenchBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/nasa_workbench.png");
    public static final Rect2i CLICK_AREA = new Rect2i(85, 52, 26, 25);

    public NasaWorkbenchScreen(NasaWorkbenchMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, TEXTURE, 177, 224);
    }

    @Override
    protected void init() {
        super.init();
        this.addSideConfigButton(145, -26);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        this.drawSmallOptionsBar(graphics, 139, -32);
    }

    @Override
    public void renderSideConfig(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.fill(leftPos - 1, topPos + 141, leftPos + 161, topPos + 217, getGuiColor());
    }

    @Override
    public int getSideConfigButtonXOffset() {
        return 43;
    }

    @Override
    public int getSideConfigButtonYOffset() {
        return imageHeight - 50;
    }

    @Override
    public void highlightSideConfigElement(GuiGraphics graphics, int index) {
        graphics.fill(leftPos + 46, topPos + 18, leftPos + 65, topPos + 19, 0xFF00FF00);
        graphics.fill(leftPos + 65, topPos + 18, leftPos + 66, topPos + 36, 0xFF00FF00);
        graphics.fill(leftPos + 65, topPos + 36, leftPos + 75, topPos + 37, 0xFF00FF00);
        graphics.fill(leftPos + 74, topPos + 36, leftPos + 75, topPos + 91, 0xFF00FF00);
        graphics.fill(leftPos + 75, topPos + 90, leftPos + 92, topPos + 91, 0xFF00FF00);
        graphics.fill(leftPos + 92, topPos + 90, leftPos + 93, topPos + 128, 0xFF00FF00);
        graphics.fill(leftPos + 74, topPos + 127, leftPos + 93, topPos + 128, 0xFF00FF00);
        graphics.fill(leftPos + 73, topPos + 109, leftPos + 74, topPos + 128, 0xFF00FF00);
        graphics.fill(leftPos + 65, topPos + 109, leftPos + 74, topPos + 110, 0xFF00FF00);
        graphics.fill(leftPos + 65, topPos + 109, leftPos + 66, topPos + 128, 0xFF00FF00);
        graphics.fill(leftPos + 46, topPos + 127, leftPos + 65, topPos + 128, 0xFF00FF00);
        graphics.fill(leftPos + 46, topPos + 109, leftPos + 47, topPos + 128, 0xFF00FF00);
        graphics.fill(leftPos + 38, topPos + 109, leftPos + 47, topPos + 110, 0xFF00FF00);
        graphics.fill(leftPos + 38, topPos + 109, leftPos + 39, topPos + 128, 0xFF00FF00);
        graphics.fill(leftPos + 19, topPos + 127, leftPos + 38, topPos + 128, 0xFF00FF00);
        graphics.fill(leftPos + 19, topPos + 90, leftPos + 20, topPos + 128, 0xFF00FF00);
        graphics.fill(leftPos + 20, topPos + 90, leftPos + 38, topPos + 91, 0xFF00FF00);
        graphics.fill(leftPos + 37, topPos + 36, leftPos + 38, topPos + 91, 0xFF00FF00);
        graphics.fill(leftPos + 38, topPos + 36, leftPos + 47, topPos + 37, 0xFF00FF00);
        graphics.fill(leftPos + 46, topPos + 18, leftPos + 47, topPos + 37, 0xFF00FF00);
    }
}
