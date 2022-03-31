package net.mrscauthd.beyond_earth.client.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.blocks.machines.entity.CoalGeneratorBlockEntity;
import net.mrscauthd.beyond_earth.gui.GuiUtil;
import net.mrscauthd.beyond_earth.gui.screen_handlers.CoalGeneratorScreenHandler;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class CoalGeneratorScreen extends AbstractMachineScreen<CoalGeneratorScreenHandler> {

    private static final Identifier TEXTURE = new ModIdentifier("textures/screens/coal_generator.png");

    public static final int FIRE_LEFT = 77;
    public static final int FIRE_TOP = 49;

    public static final int ENERGY_LEFT = 144;
    public static final int ENERGY_TOP = 21;

    public CoalGeneratorScreen(CoalGeneratorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, TEXTURE);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);

        CoalGeneratorBlockEntity entity = (CoalGeneratorBlockEntity) blockEntity;

        GuiUtil.drawFire(matrices, this.x + FIRE_LEFT, this.y + FIRE_TOP, entity.getCookTime(), entity.getCookTimeTotal());
        GuiUtil.drawEnergy(matrices, this.x + ENERGY_LEFT, this.y + ENERGY_TOP, this.blockEntity.getEnergy(), this.blockEntity.getMaxGeneration());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        CoalGeneratorBlockEntity entity = (CoalGeneratorBlockEntity) blockEntity;

        // Energy tooltip.
        if (GuiUtil.isHover(this.getEnergyBounds(), mouseX, mouseY)) {
            this.renderTooltip(matrices, new TranslatableText("gauge_text.beyond_earth.storage", this.blockEntity.getEnergy(), this.blockEntity.getMaxGeneration()), mouseX, mouseY);
        }

        // Burn time tooltip.
        if (GuiUtil.isHover(this.getFireBounds(), mouseX, mouseY)) {
            this.renderTooltip(matrices, new TranslatableText("gauge.beyond_earth.burn_time", entity.getCookTime(), entity.getCookTimeTotal()), mouseX, mouseY);
        }
    }

    public Rectangle getEnergyBounds() {
        return GuiUtil.getEnergyBounds(this.x + ENERGY_LEFT, this.y + ENERGY_TOP);
    }

    public Rectangle getFireBounds() {
        return GuiUtil.getFireBounds(this.x + FIRE_LEFT, this.y + FIRE_TOP);
    }
}
