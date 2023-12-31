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
    public static final Rect2i CLICK_AREA = new Rect2i(93, 52, 26, 25);

    public NasaWorkbenchScreen(NasaWorkbenchMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, TEXTURE, STEEL_SLOT, 177, 224);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        if (this.sideConfigWidget.isActive()) {
            graphics.fill(leftPos + 54, topPos + 18, leftPos + 73, topPos + 19, 0xFF00FF00);
            graphics.fill(leftPos + 73, topPos + 18, leftPos + 74, topPos + 36, 0xFF00FF00);
            graphics.fill(leftPos + 73, topPos + 36, leftPos + 83, topPos + 37, 0xFF00FF00);
            graphics.fill(leftPos + 82, topPos + 36, leftPos + 83, topPos + 91, 0xFF00FF00);
            graphics.fill(leftPos + 83, topPos + 90, leftPos + 100, topPos + 91, 0xFF00FF00);
            graphics.fill(leftPos + 100, topPos + 90, leftPos + 101, topPos + 128, 0xFF00FF00);
            graphics.fill(leftPos + 82, topPos + 127, leftPos + 101, topPos + 128, 0xFF00FF00);
            graphics.fill(leftPos + 81, topPos + 109, leftPos + 82, topPos + 128, 0xFF00FF00);
            graphics.fill(leftPos + 73, topPos + 109, leftPos + 82, topPos + 110, 0xFF00FF00);
            graphics.fill(leftPos + 73, topPos + 109, leftPos + 74, topPos + 128, 0xFF00FF00);
            graphics.fill(leftPos + 54, topPos + 127, leftPos + 73, topPos + 128, 0xFF00FF00);
            graphics.fill(leftPos + 54, topPos + 109, leftPos + 55, topPos + 128, 0xFF00FF00);
            graphics.fill(leftPos + 46, topPos + 109, leftPos + 55, topPos + 110, 0xFF00FF00);
            graphics.fill(leftPos + 46, topPos + 109, leftPos + 47, topPos + 128, 0xFF00FF00);
            graphics.fill(leftPos + 27, topPos + 127, leftPos + 46, topPos + 128, 0xFF00FF00);
            graphics.fill(leftPos + 27, topPos + 90, leftPos + 28, topPos + 128, 0xFF00FF00);
            graphics.fill(leftPos + 28, topPos + 90, leftPos + 46, topPos + 91, 0xFF00FF00);
            graphics.fill(leftPos + 45, topPos + 36, leftPos + 46, topPos + 91, 0xFF00FF00);
            graphics.fill(leftPos + 46, topPos + 36, leftPos + 55, topPos + 37, 0xFF00FF00);
            graphics.fill(leftPos + 54, topPos + 18, leftPos + 55, topPos + 37, 0xFF00FF00);
        }
    }
}
