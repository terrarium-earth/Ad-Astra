package earth.terrarium.ad_astra.client.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.screen.handler.LanderScreenHandler;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

@Environment(EnvType.CLIENT)
public class LanderScreen extends AbstractVehicleScreen<LanderScreenHandler> {

    private static final ResourceLocation TEXTURE = new ModResourceLocation("textures/gui/screens/lander.png");

    public LanderScreen(LanderScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title, TEXTURE);
        this.imageWidth = 177;
        this.imageHeight = 174;
        this.inventoryLabelY = this.imageHeight - 93;
    }

    @Override
    protected void renderBg(PoseStack matrices, float delta, int mouseX, int mouseY) {
        super.renderBg(matrices, delta, mouseX, mouseY);
    }

    @Override
    public int getTextColour() {
        return 0x2C282E;
    }
}