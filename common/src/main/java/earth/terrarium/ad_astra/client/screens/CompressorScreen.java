package earth.terrarium.ad_astra.client.screens;

import earth.terrarium.ad_astra.blocks.machines.entity.CompressorBlockEntity;
import earth.terrarium.ad_astra.screen.handler.CompressorScreenHandler;
import earth.terrarium.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class CompressorScreen extends AbstractMachineScreen<CompressorBlockEntity, CompressorScreenHandler> {

    public static final int ENERGY_LEFT = 147;
    public static final int ENERGY_TOP = 30;
    public static final int HAMMER_LEFT = 67;
    public static final int HAMMER_TOP = 63;
    private static final Identifier TEXTURE = new ModIdentifier("textures/gui/screens/compressor.png");

    public CompressorScreen(CompressorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, TEXTURE);
        this.backgroundWidth = 177;
        this.backgroundHeight = 196;
        this.playerInventoryTitleY = this.backgroundHeight - 93;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);


        GuiUtil.drawHammer(matrices, this.x + HAMMER_LEFT, this.y + HAMMER_TOP, this.machine.getCookTime(), this.machine.getCookTimeTotal());
        GuiUtil.drawEnergy(matrices, this.x + ENERGY_LEFT, this.y + ENERGY_TOP, this.handler.getEnergyAmount(), this.machine.getMaxCapacity());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        if (GuiUtil.isHovering(this.getEnergyBounds(), mouseX, mouseY)) {
            GuiUtil.drawEnergyTooltip(this, matrices, this.handler.getEnergyAmount(), this.machine.getMaxCapacity(), mouseX, mouseY);
        }

        // Burn time tooltip.
        if (GuiUtil.isHovering(this.getHammerBounds(), mouseX, mouseY)) {
            this.renderTooltip(matrices, Text.translatable("gauge.ad_astra.cook_time", this.machine.getCookTime(), this.machine.getCookTimeTotal()), mouseX, mouseY);
        }
    }

    public Rectangle getEnergyBounds() {
        return GuiUtil.getEnergyBounds(this.x + ENERGY_LEFT, this.y + ENERGY_TOP);
    }

    public Rectangle getHammerBounds() {
        return GuiUtil.getHammerBounds(this.x + HAMMER_LEFT, this.y + HAMMER_TOP);
    }
}