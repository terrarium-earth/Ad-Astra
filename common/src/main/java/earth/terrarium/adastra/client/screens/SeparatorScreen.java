package earth.terrarium.adastra.client.screens;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.base.MachineScreen;
import earth.terrarium.adastra.common.blockentities.SeparatorBlockEntity;
import earth.terrarium.adastra.common.menus.SeparatorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class SeparatorScreen extends MachineScreen<SeparatorMenu, SeparatorBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/separator.png");
    public static final Rect2i CLICK_AREA = new Rect2i(55, 111, 26, 25);

    public SeparatorScreen(SeparatorMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, TEXTURE, 184, 255);
    }

    @Override
    protected void init() {
        super.init();
        this.addRedstoneButton(131, 6);
        this.addSideConfigButton(110, 6);
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float f) {
        super.render(graphics, mouseX, mouseY, f);
        this.drawGear(graphics, 110, 6);
        this.drawEnergyBar(graphics, mouseX, mouseY, 138, 103, entity.getEnergyStorage(), entity.energyDifference());
        this.drawFluidBar(graphics, mouseX, mouseY, 54, 93, entity.getFluidContainer(), 0, entity.fluidDifference(0));
        this.drawFluidBar(graphics, mouseX, mouseY, 14, 105, entity.getFluidContainer(), 1, entity.fluidDifference(1));
        this.drawFluidBar(graphics, mouseX, mouseY, 94, 105, entity.getFluidContainer(), 2, entity.fluidDifference(2));
    }

    @Override
    public void renderSideConfig(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.fill(leftPos, topPos + 157, leftPos + 170, topPos + 245, getGuiColor());
        graphics.blit(TEXTURE, leftPos - 8, topPos + 157, 0, 33, 184, 9, this.imageWidth, this.imageHeight);
    }

    @Override
    public int getSideConfigButtonXOffset() {
        return 44;
    }

    @Override
    public int getSideConfigButtonYOffset() {
        return imageHeight - 50;
    }
}
