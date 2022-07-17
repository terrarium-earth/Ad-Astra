package com.github.alexnijjar.beyond_earth.client.screens;

import com.github.alexnijjar.beyond_earth.gui.screen_handlers.NasaWorkbenchScreenHandler;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class NasaWorkbenchScreen extends AbstractMachineScreen<NasaWorkbenchScreenHandler> {

    private static final Identifier TEXTURE = new ModIdentifier("textures/gui/screens/nasa_workbench.png");

    public NasaWorkbenchScreen(NasaWorkbenchScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, TEXTURE);
        this.backgroundWidth = 176;
        this.backgroundHeight = 224;
        this.playerInventoryTitleY = this.backgroundHeight - 93;
    }
}
