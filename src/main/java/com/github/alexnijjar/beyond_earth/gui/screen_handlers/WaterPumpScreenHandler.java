package com.github.alexnijjar.beyond_earth.gui.screen_handlers;

import com.github.alexnijjar.beyond_earth.blocks.machines.entity.FluidMachineBlockEntity;
import com.github.alexnijjar.beyond_earth.registry.ModScreenHandlers;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class WaterPumpScreenHandler extends AbstractMachineScreenHandler {

    public WaterPumpScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, (FluidMachineBlockEntity) inventory.player.world.getBlockEntity(buf.readBlockPos()));
    }

    public WaterPumpScreenHandler(int syncId, PlayerInventory inventory, FluidMachineBlockEntity entity) {
        super(ModScreenHandlers.WATER_PUMP_SCREEN_HANDLER, syncId, inventory, entity, new Slot[] {});
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 14;
    }
}