package earth.terrarium.adastra.client.screens.machines;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.components.GravitySlider;
import earth.terrarium.adastra.client.screens.base.MachineScreen;
import earth.terrarium.adastra.common.blockentities.machines.GravityNormalizerBlockEntity;
import earth.terrarium.adastra.common.constants.PlanetConstants;
import earth.terrarium.adastra.common.menus.machines.GravityNormalizerMenu;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.messages.ServerboundSetGravityNormalizerTargetParget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class GravityNormalizerScreen extends MachineScreen<GravityNormalizerMenu, GravityNormalizerBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/container/gravity_normalizer.png");

    private double sliderValue;
    private AbstractSliderButton slider;

    public GravityNormalizerScreen(GravityNormalizerMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, TEXTURE, STEEL_SLOT, 184, 201);
        this.titleLabelY += 3;
    }

    @Override
    protected void init() {
        super.init();
        slider = addRenderableWidget(new GravitySlider(
            leftPos + 25,
            topPos + 42,
            108,
            11,
            CommonComponents.EMPTY,
            entity.targetGravity(),
            0,
            2.04,
            value -> {
                sliderValue = value;
                NetworkHandler.CHANNEL.sendToServer(new ServerboundSetGravityNormalizerTargetParget(entity.getBlockPos(), (float) value));
            }));
        sliderValue = entity.targetGravity();
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(graphics, partialTick, mouseX, mouseY);
        Component text = Component.translatable("tooltip.ad_astra.gravity_amount", (int) ((sliderValue * PlanetConstants.EARTH_GRAVITY) * 100) / 100.0, 20.00);

        // colored shadow
        graphics.drawCenteredString(
            font,
            text,
            leftPos + 77,
            topPos + 70,
            0x32506e
        );

        // actual text
        graphics.drawCenteredString(
            font,
            text,
            leftPos + 78,
            topPos + 69,
            0x8cf5f5
        );
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (slider.isFocused()) {
            slider.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }
}
