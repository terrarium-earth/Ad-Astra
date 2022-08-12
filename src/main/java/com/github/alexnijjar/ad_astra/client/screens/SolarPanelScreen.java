package com.github.alexnijjar.ad_astra.client.screens;

import java.awt.Rectangle;

import com.github.alexnijjar.ad_astra.blocks.machines.AbstractMachineBlock;
import com.github.alexnijjar.ad_astra.screen.handler.SolarPanelScreenHandler;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SolarPanelScreen extends AbstractMachineScreen<SolarPanelScreenHandler> {

    private static final Identifier TEXTURE = new ModIdentifier("textures/gui/screens/solar_panel.png");

    public static final int SUN_LEFT = 35;
    public static final int SUN_TOP = 59;

    public static final int ENERGY_LEFT = 108;
    public static final int ENERGY_TOP = 69;

    public SolarPanelScreen(SolarPanelScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, TEXTURE);
        this.backgroundWidth = 177;
        this.backgroundHeight = 228;
        this.titleY = 46;
        this.playerInventoryTitleY = this.backgroundHeight - 93;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);

        GuiUtil.drawEnergy(matrices, this.x + ENERGY_LEFT, this.y + ENERGY_TOP, this.blockEntity.getEnergy(), this.blockEntity.getMaxGeneration());
        if (this.blockEntity.getCachedState().get(AbstractMachineBlock.LIT)) {
            GuiUtil.drawSun(matrices, this.x + SUN_LEFT, this.y + SUN_TOP);
        }
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawForeground(matrices, mouseX, mouseY);

        this.textRenderer.draw(matrices, new TranslatableText("gauge_text.ad_astra.max_generation"), this.titleY - 20, 8, 0x68d975);
        this.textRenderer.draw(matrices, new TranslatableText("gauge_text.ad_astra.energy_per_tick", this.blockEntity.getEnergyPerTick()), this.titleY - 21, 18, 0x68d975);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        if (GuiUtil.isHovering(this.getEnergyBounds(), mouseX, mouseY)) {
            GuiUtil.drawEnergyTooltip(this, matrices, this.blockEntity, mouseX, mouseY);
        }
    }

    public Rectangle getEnergyBounds() {
        return GuiUtil.getEnergyBounds(this.x + ENERGY_LEFT, this.y + ENERGY_TOP);
    }

    @Override
    public int getTextColour() {
        return 0x2C282E;
    }
}