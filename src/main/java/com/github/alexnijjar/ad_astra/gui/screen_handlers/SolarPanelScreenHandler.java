package com.github.alexnijjar.beyond_earth.gui.screen_handlers;

import com.github.alexnijjar.beyond_earth.blocks.machines.entity.SolarPanelBlockEntity;
import com.github.alexnijjar.beyond_earth.registry.ModScreenHandlers;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;

public class SolarPanelScreenHandler extends AbstractMachineScreenHandler {

    public SolarPanelScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, (SolarPanelBlockEntity) inventory.player.world.getBlockEntity(buf.readBlockPos()));
    }

    public SolarPanelScreenHandler(int syncId, PlayerInventory inventory, SolarPanelBlockEntity entity) {
        super(ModScreenHandlers.SOLAR_PANEL_SCREEN_HANDLER, syncId, inventory, entity);
    }
}