package net.mrscauthd.beyond_earth.client.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.gui.screen_handlers.SolarPanelScreenHandler;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class SolarPanelScreen extends AbstractMachineScreen<SolarPanelScreenHandler> {

    private static final Identifier TEXTURE = new ModIdentifier("textures/screens/solar_panel_gui.png");

    public SolarPanelScreen(SolarPanelScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, TEXTURE);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawForeground(matrices, mouseX, mouseY);

        // Just to silence IDEA.
        int first = this.titleY;

        if (this.blockEntity != null) {
            this.textRenderer.draw(matrices, new TranslatableText("gauge_text.beyond_earth.stored", this.blockEntity.getEnergy()), first, 28, 0x3C3C3C);
            this.textRenderer.draw(matrices, new TranslatableText("gauge_text.beyond_earth.capacity", this.blockEntity.getMaxGeneration()), first, 40, 0x3C3C3C);
            this.textRenderer.draw(matrices, new TranslatableText("gauge_text.beyond_earth.max_generation", this.blockEntity.getEnergyPerTick()), first, 52, 0x3C3C3C);
        }
    }
}
