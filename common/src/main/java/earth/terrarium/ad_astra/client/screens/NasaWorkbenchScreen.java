package earth.terrarium.ad_astra.client.screens;

import earth.terrarium.ad_astra.blocks.machines.entity.NasaWorkbenchBlockEntity;
import earth.terrarium.ad_astra.screen.menu.NasaWorkbenchMenu;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

@Environment(EnvType.CLIENT)
public class NasaWorkbenchScreen extends AbstractMachineScreen<NasaWorkbenchBlockEntity, NasaWorkbenchMenu> {

    private static final ResourceLocation TEXTURE = new ModResourceLocation("textures/gui/screens/nasa_workbench.png");

    public NasaWorkbenchScreen(NasaWorkbenchMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title, TEXTURE);
        this.imageWidth = 177;
        this.imageHeight = 224;
        this.inventoryLabelY = this.imageHeight - 93;
    }

    @Override
    public int getTextColour() {
        return 0x2C282E;
    }
}
