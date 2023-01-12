package earth.terrarium.ad_astra.client.screen.machine;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.common.screen.AbstractModContainerMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class TestScreen extends AbstractContainerScreen<AbstractModContainerMenu<?>> {
    public TestScreen(AbstractModContainerMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {

    }
}
