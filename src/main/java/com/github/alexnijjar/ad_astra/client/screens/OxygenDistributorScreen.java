package com.github.alexnijjar.beyond_earth.client.screens;

import java.awt.Rectangle;
import java.util.Arrays;

import com.github.alexnijjar.beyond_earth.blocks.machines.entity.FluidMachineBlockEntity;
import com.github.alexnijjar.beyond_earth.blocks.machines.entity.OxygenDistributorBlockEntity;
import com.github.alexnijjar.beyond_earth.client.screens.utils.ButtonSize;
import com.github.alexnijjar.beyond_earth.client.screens.utils.CustomButton;
import com.github.alexnijjar.beyond_earth.client.screens.utils.PlanetSelectionScreen.TooltipType;
import com.github.alexnijjar.beyond_earth.client.screens.utils.ScreenUtils;
import com.github.alexnijjar.beyond_earth.client.utils.ClientOxygenUtils;
import com.github.alexnijjar.beyond_earth.data.ButtonColour;
import com.github.alexnijjar.beyond_earth.gui.screen_handlers.OxygenDistributorScreenHandler;
import com.github.alexnijjar.beyond_earth.util.FluidUtils;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class OxygenDistributorScreen extends AbstractMachineScreen<OxygenDistributorScreenHandler> {

    private static final Identifier TEXTURE = new ModIdentifier("textures/screens/oxygen_distributor.png");
    private static final Identifier WARNING_SIGN = new ModIdentifier("textures/overlay/warning_sign.png");

    Text SHOW_TEXT = ScreenUtils.createText("show");
    Text HIDE_TEXT = ScreenUtils.createText("hide");

    public static final int INPUT_TANK_LEFT = 9;
    public static final int INPUT_TANK_TOP = 21;

    public static final int OUTPUT_TANK_LEFT = 75;
    public static final int OUTPUT_TANK_TOP = 21;

    public static final int ENERGY_LEFT = 144;
    public static final int ENERGY_TOP = 21;

    public static final int ARROW_LEFT = 48;
    public static final int ARROW_TOP = 36;

    CustomButton visibleButton;

    public OxygenDistributorScreen(OxygenDistributorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, TEXTURE);
        this.backgroundWidth = 177;
        this.backgroundHeight = 172;
        this.playerInventoryTitleY = this.backgroundHeight - 92;

    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

        super.drawBackground(matrices, delta, mouseX, mouseY);

        FluidMachineBlockEntity entity = (FluidMachineBlockEntity) blockEntity;

        GuiUtil.drawEnergy(matrices, this.x + ENERGY_LEFT, this.y + ENERGY_TOP, this.blockEntity.getEnergy(), this.blockEntity.getMaxGeneration());
        GuiUtil.drawFluidTank(matrices, this.x + INPUT_TANK_LEFT, this.y + INPUT_TANK_TOP, entity.inputTank.getAmount(), entity.inputTank.getCapacity(), entity.inputTank.getResource());
        GuiUtil.drawFluidTank(matrices, this.x + OUTPUT_TANK_LEFT, this.y + OUTPUT_TANK_TOP, entity.outputTank.getAmount(), entity.outputTank.getCapacity(), entity.outputTank.getResource());

        visibleButton.setMessage(ClientOxygenUtils.renderOxygenParticles ? HIDE_TEXT : SHOW_TEXT);

        // Render a warning sign if there is an oxygen leak detected.
        if (ClientOxygenUtils.oxygenLeak) {
            ScreenUtils.addTexture(matrices, this.width / 2 - 85, this.height / 2 - 100, 14, 15, WARNING_SIGN);
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        FluidMachineBlockEntity entity = (FluidMachineBlockEntity) blockEntity;

        // Energy tooltip.
        if (GuiUtil.isHovering(this.getEnergyBounds(), mouseX, mouseY)) {
            this.renderTooltip(matrices, Text.translatable("gauge_text.beyond_earth.storage", this.blockEntity.getEnergy(), this.blockEntity.getMaxGeneration()), mouseX, mouseY);
        }

        if (GuiUtil.isHovering(this.getInputTankBounds(), mouseX, mouseY)) {
            this.renderTooltip(matrices, Text.translatable("gauge_text.beyond_earth.liquid_storage", FluidUtils.dropletsToMillibuckets(entity.inputTank.getAmount()), FluidUtils.dropletsToMillibuckets(entity.inputTank.getCapacity())), mouseX, mouseY);
        }

        if (GuiUtil.isHovering(this.getOutputTankBounds(), mouseX, mouseY)) {
            this.renderTooltip(matrices, Text.translatable("gauge_text.beyond_earth.liquid_storage", FluidUtils.dropletsToMillibuckets(entity.outputTank.getAmount()), FluidUtils.dropletsToMillibuckets(entity.outputTank.getCapacity())), mouseX, mouseY);
        }

        if (ClientOxygenUtils.oxygenLeak) {
            if (GuiUtil.isHovering(getWarningSignBounds(), mouseX, mouseY)) {
                this.renderTooltip(matrices, Arrays.asList(Text.translatable("gauge_text.beyond_earth.oxygen_leak_warning[0]"), Text.translatable("gauge_text.beyond_earth.oxygen_leak_warning[1]"),
                        Text.translatable("gauge_text.beyond_earth.oxygen_leak_warning[2]"), Text.translatable("gauge_text.beyond_earth.oxygen_leak_warning[3]"), Text.translatable("gauge_text.beyond_earth.oxygen_leak_warning[4]")), mouseX, mouseY);
            }
        }
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawForeground(matrices, mouseX, mouseY);
        matrices.push();
        matrices.scale(0.75f, 0.75f, 0.75f);
        Text oxygenBlockAmount = Text.of("Oxygen Blocks: " + ClientOxygenUtils.getOxygenBlocksCount(this.blockEntity.getWorld(), this.blockEntity.getPos()) + " / " + OxygenDistributorBlockEntity.MAX_BLOCK_CHECKS);
        this.textRenderer.draw(matrices, oxygenBlockAmount, 90, 110, 0x404040);
        matrices.pop();
    }

    @Override
    protected void init() {
        super.init();
        visibleButton = new CustomButton(this.width / 2 + 10, this.height / 2 - 107, ClientOxygenUtils.renderOxygenParticles ? HIDE_TEXT : SHOW_TEXT, ButtonSize.NORMAL, ButtonColour.DARK_GREEN, TooltipType.NONE, null, pressed -> {
            ClientOxygenUtils.renderOxygenParticles = !ClientOxygenUtils.renderOxygenParticles;
        });
        visibleButton.doMask = false;
        this.addDrawableChild(visibleButton);
    }

    public Rectangle getInputTankBounds() {
        return GuiUtil.getFluidTankBounds(this.x + INPUT_TANK_LEFT, this.y + INPUT_TANK_TOP);
    }

    public Rectangle getOutputTankBounds() {
        return GuiUtil.getFluidTankBounds(this.x + OUTPUT_TANK_LEFT, this.y + OUTPUT_TANK_TOP);
    }

    public Rectangle getWarningSignBounds() {
        return new Rectangle(this.width / 2 - 85, this.height / 2 - 100, 14, 15);
    }

    public Rectangle getEnergyBounds() {
        return GuiUtil.getEnergyBounds(this.x + ENERGY_LEFT, this.y + ENERGY_TOP);
    }
}