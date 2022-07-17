package com.github.alexnijjar.beyond_earth.client.screens;

import java.awt.Rectangle;

import com.github.alexnijjar.beyond_earth.blocks.machines.entity.CryoFreezerBlockEntity;
import com.github.alexnijjar.beyond_earth.gui.screen_handlers.CryoFreezerScreenHandler;
import com.github.alexnijjar.beyond_earth.util.FluidUtils;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class CryoFreezerScreen extends AbstractMachineScreen<CryoFreezerScreenHandler> {

    private static final Identifier TEXTURE = new ModIdentifier("textures/gui/screens/cryo_freezer.png");

    public static final int SNOWFLAKE_LEFT = 68;
    public static final int SNOWFLAKE_TOP = 52;

    public static final int OUTPUT_TANK_LEFT = 90;
    public static final int OUTPUT_TANK_TOP = 21;

    public static final int ENERGY_LEFT = 144;
    public static final int ENERGY_TOP = 21;

    public static final int ARROW_LEFT = 48;
    public static final int ARROW_TOP = 36;

    public CryoFreezerScreen(CryoFreezerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, TEXTURE);
        this.backgroundWidth = 177;
        this.backgroundHeight = 168;
        this.playerInventoryTitleY = this.backgroundHeight - 92;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

        super.drawBackground(matrices, delta, mouseX, mouseY);

        CryoFreezerBlockEntity entity = (CryoFreezerBlockEntity) blockEntity;

        GuiUtil.drawEnergy(matrices, this.x + ENERGY_LEFT, this.y + ENERGY_TOP, this.blockEntity.getEnergy(), this.blockEntity.getMaxGeneration());
        GuiUtil.drawFluidTank(matrices, this.x + OUTPUT_TANK_LEFT, this.y + OUTPUT_TANK_TOP, entity.inputTank.getAmount(), entity.inputTank.getCapacity(), entity.inputTank.getResource());
        GuiUtil.drawSnowflake(matrices, this.x + SNOWFLAKE_LEFT, this.y + SNOWFLAKE_TOP, entity.getCookTime(), entity.getCookTimeTotal());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        CryoFreezerBlockEntity entity = (CryoFreezerBlockEntity) blockEntity;

        // Energy tooltip.
        if (GuiUtil.isHovering(this.getEnergyBounds(), mouseX, mouseY)) {
            this.renderTooltip(matrices, new TranslatableText("gauge_text.beyond_earth.storage", this.blockEntity.getEnergy(), this.blockEntity.getMaxGeneration()), mouseX, mouseY);
        }

        if (GuiUtil.isHovering(this.getOutputTankBounds(), mouseX, mouseY)) {
            this.renderTooltip(matrices, new TranslatableText("gauge_text.beyond_earth.liquid_storage", FluidUtils.dropletsToMillibuckets(entity.inputTank.getAmount()), FluidUtils.dropletsToMillibuckets(entity.inputTank.getCapacity())), mouseX, mouseY);
        }
    }

    public Rectangle getOutputTankBounds() {
        return GuiUtil.getFluidTankBounds(this.x + OUTPUT_TANK_LEFT, this.y + OUTPUT_TANK_TOP);
    }

    public Rectangle getEnergyBounds() {
        return GuiUtil.getEnergyBounds(this.x + ENERGY_LEFT, this.y + ENERGY_TOP);
    }
}