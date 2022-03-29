package net.mrscauthd.beyond_earth.client.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.blocks.machines.entity.CompressorBlockEntity;
import net.mrscauthd.beyond_earth.gui.GuiUtil;
import net.mrscauthd.beyond_earth.gui.screen_handlers.CompressorScreenHandler;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class CompressorScreen extends AbstractMachineScreen<CompressorScreenHandler> {

    private static final Identifier TEXTURE = new ModIdentifier("textures/screens/compressor_gui.png");

    public static final int ENERGY_LEFT = 144;
    public static final int ENERGY_TOP = 21;
    public static final int ARROW_LEFT = 62;
    public static final int ARROW_TOP = 36;


    public CompressorScreen(CompressorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, TEXTURE);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);

        CompressorBlockEntity entity = (CompressorBlockEntity) blockEntity;

        GuiUtil.drawArrow(matrices, this.x + ARROW_LEFT, this.y + ARROW_TOP, entity.getCookTime(), entity.getCookTimeTotal());
        GuiUtil.drawEnergy(matrices, this.x + ENERGY_LEFT, this.y + ENERGY_TOP, this.blockEntity.getEnergy(), this.blockEntity.getMaxGeneration());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        CompressorBlockEntity entity = (CompressorBlockEntity) blockEntity;

        // Energy tooltip.
        if (GuiUtil.isHover(this.getEnergyBounds(), mouseX, mouseY)) {
            this.renderTooltip(matrices, new TranslatableText("gauge_text.beyond_earth.storage", this.blockEntity.getEnergy(), this.blockEntity.getMaxGeneration()), mouseX, mouseY);
        }

        // Burn time tooltip.
        if (GuiUtil.isHover(this.getArrowBounds(), mouseX, mouseY)) {
            this.renderTooltip(matrices, new TranslatableText("gauge.beyond_earth.cook_time", entity.getCookTime(), entity.getCookTimeTotal()), mouseX, mouseY);
        }
    }

    public Rectangle getEnergyBounds() {
        return GuiUtil.getEnergyBounds(this.x + ENERGY_LEFT, this.y + ENERGY_TOP);
    }

    public Rectangle getArrowBounds() {
        return GuiUtil.getArrowBounds(this.x + ARROW_LEFT, this.y + ARROW_TOP);
    }
}
