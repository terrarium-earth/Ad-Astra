package earth.terrarium.ad_astra.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.machine.entity.CoalGeneratorBlockEntity;
import earth.terrarium.ad_astra.common.screen.menu.CoalGeneratorMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class CoalGeneratorScreen extends AbstractMachineScreen<CoalGeneratorBlockEntity, CoalGeneratorMenu> {

    public static final int FIRE_LEFT = 78;
    public static final int FIRE_TOP = 53;
    public static final int ENERGY_LEFT = 146;
    public static final int ENERGY_TOP = 32;
    private static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/screens/coal_generator.png");

    public CoalGeneratorScreen(CoalGeneratorMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title, TEXTURE);
        this.imageWidth = 176;
        this.imageHeight = 189;
        this.inventoryLabelY = this.imageHeight - 93;
    }

    @Override
    protected void renderBg(PoseStack poseStack, float delta, int mouseX, int mouseY) {
        super.renderBg(poseStack, delta, mouseX, mouseY);

        GuiUtil.drawFire(poseStack, this.leftPos + FIRE_LEFT, this.topPos + FIRE_TOP, this.machine.getCookTime(), this.machine.getCookTimeTotal());
        GuiUtil.drawEnergy(poseStack, this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP, this.menu.getEnergyAmount(), this.machine.getMaxCapacity());
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        super.render(poseStack, mouseX, mouseY, delta);

        if (GuiUtil.isHovering(this.getEnergyBounds(), mouseX, mouseY)) {
            GuiUtil.drawEnergyTooltip(this, poseStack, this.menu.getEnergyAmount(), this.machine.getMaxCapacity(), mouseX, mouseY);
        }

        // Burn time tooltip.
        if (GuiUtil.isHovering(this.getFireBounds(), mouseX, mouseY)) {
            this.renderTooltip(poseStack, Component.translatable("gauge.ad_astra.burn_time", this.machine.getCookTime(), this.machine.getCookTimeTotal()), mouseX, mouseY);
        }
    }

    public Rectangle getEnergyBounds() {
        return GuiUtil.getEnergyBounds(this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP);
    }

    public Rectangle getFireBounds() {
        return GuiUtil.getFireBounds(this.leftPos + FIRE_LEFT, this.topPos + FIRE_TOP);
    }
}