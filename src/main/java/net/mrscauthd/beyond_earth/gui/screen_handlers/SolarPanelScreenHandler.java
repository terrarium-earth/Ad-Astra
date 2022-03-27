package net.mrscauthd.beyond_earth.gui.screen_handlers;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.mrscauthd.beyond_earth.blocks.machines.entity.SolarPanelBlockEntity;
import net.mrscauthd.beyond_earth.registry.ModScreenHandlers;

public class SolarPanelScreenHandler extends AbstractMachineScreenHandler {

    public SolarPanelScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, (SolarPanelBlockEntity) inventory.player.world.getBlockEntity(buf.readBlockPos()));
    }

    public SolarPanelScreenHandler(int syncId, PlayerInventory inventory, SolarPanelBlockEntity entity) {
        super(ModScreenHandlers.SOLAR_PANEL_SCREEN_HANDLER, syncId, inventory, entity);
    }
}
