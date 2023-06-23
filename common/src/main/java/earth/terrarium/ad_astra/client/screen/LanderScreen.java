package earth.terrarium.ad_astra.client.screen;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.screen.menu.LanderMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class LanderScreen extends AbstractVehicleScreen<LanderMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/screens/lander.png");

    public LanderScreen(LanderMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title, TEXTURE);
        this.imageWidth = 177;
        this.imageHeight = 174;
        this.inventoryLabelY = this.imageHeight - 93;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float delta, int mouseX, int mouseY) {
        super.renderBg(graphics, delta, mouseX, mouseY);
    }

    @Override
    public int getTextColour() {
        return 0x2C282E;
    }
}