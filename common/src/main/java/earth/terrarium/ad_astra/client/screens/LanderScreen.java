package earth.terrarium.ad_astra.client.screens;

import earth.terrarium.ad_astra.screen.handler.LanderScreenHandler;
import earth.terrarium.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class LanderScreen extends AbstractVehicleScreen<LanderScreenHandler> {

    private static final Identifier TEXTURE = new ModIdentifier("textures/gui/screens/lander.png");

    public LanderScreen(LanderScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, TEXTURE);
        this.backgroundWidth = 177;
        this.backgroundHeight = 174;
        this.playerInventoryTitleY = this.backgroundHeight - 93;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.drawBackground(matrices, delta, mouseX, mouseY);
    }

    @Override
    public int getTextColour() {
        return 0x2C282E;
    }
}