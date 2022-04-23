package net.mrscauthd.beyond_earth.client.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.gui.screen_handlers.NasaWorkbenchScreenHandler;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class NasaWorkbenchScreen extends AbstractMachineScreen<NasaWorkbenchScreenHandler> {

    private static final Identifier TEXTURE = new ModIdentifier("textures/screens/nasa_workbench.png");

    public NasaWorkbenchScreen(NasaWorkbenchScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, TEXTURE);
        this.backgroundWidth = 176;
        this.backgroundHeight = 224;
        this.playerInventoryTitleY = this.backgroundHeight - 92;
    }
}
