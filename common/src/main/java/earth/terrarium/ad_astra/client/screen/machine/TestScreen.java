package earth.terrarium.ad_astra.client.screen.machine;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.client.util.GuiUtils;
import earth.terrarium.ad_astra.common.screen.AbstractModContainerMenu;
import earth.terrarium.botarium.common.energy.base.EnergyAttachment;
import earth.terrarium.botarium.common.fluid.base.FluidAttachment;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class TestScreen extends AbstractContainerScreen<AbstractModContainerMenu<?>> {
    public TestScreen(AbstractModContainerMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        if (this.menu.getEntity() instanceof EnergyAttachment.Block block) {
            GuiUtils.createEnergyBar(poseStack, 435, 100, block.getEnergyStorage(this.menu.getEntity()));
        }

        if (this.menu.getEntity() instanceof FluidAttachment.Block block) {
            for (int i = 0; i < block.getFluidContainer(this.menu.getEntity()).getSize(); i++) {
                GuiUtils.createFluidBar(poseStack, 240 + 30 * (i + 1), 50, i, block.getFluidContainer(this.menu.getEntity()));
            }
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);

        if (this.menu.getEntity() instanceof EnergyAttachment.Block block) {
            GuiUtils.createEnergyTooltip(this, mouseX, mouseY, poseStack, 435, 100, block.getEnergyStorage(this.menu.getEntity()));
        }

        if (this.menu.getEntity() instanceof FluidAttachment.Block block) {
            for (int i = 0; i < block.getFluidContainer(this.menu.getEntity()).getSize(); i++) {
                GuiUtils.createFluidTooltip(this, mouseX, mouseY, poseStack, 240 + 30 * (i + 1), 50, i, block.getFluidContainer(this.menu.getEntity()));
            }
        }
    }
}
