package com.github.alexnijjar.beyond_earth.client.screens;

import com.github.alexnijjar.beyond_earth.gui.screen_handlers.SolarPanelScreenHandler;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SolarPanelScreen extends AbstractMachineScreen<SolarPanelScreenHandler> {

    private static final Identifier TEXTURE = new ModIdentifier("textures/screens/solar_panel.png");

    public SolarPanelScreen(SolarPanelScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, TEXTURE);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawForeground(matrices, mouseX, mouseY);

        // Just to silence IDEA.
        int first = this.titleY;

        if (this.blockEntity != null) {
            this.textRenderer.draw(matrices, Text.translatable("gauge_text.beyond_earth.stored", this.blockEntity.getEnergy()), first, 28, 0x3C3C3C);
            this.textRenderer.draw(matrices, Text.translatable("gauge_text.beyond_earth.capacity", this.blockEntity.getMaxGeneration()), first, 40, 0x3C3C3C);
            this.textRenderer.draw(matrices, Text.translatable("gauge_text.beyond_earth.max_generation", this.blockEntity.getEnergyPerTick()), first, 52, 0x3C3C3C);
        }
    }
}