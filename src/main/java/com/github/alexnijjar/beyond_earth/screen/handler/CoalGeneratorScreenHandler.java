package com.github.alexnijjar.beyond_earth.screen.handler;

import com.github.alexnijjar.beyond_earth.blocks.machines.entity.CoalGeneratorBlockEntity;
import com.github.alexnijjar.beyond_earth.registry.ModScreenHandlers;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class CoalGeneratorScreenHandler extends AbstractMachineScreenHandler {

    public CoalGeneratorScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, (CoalGeneratorBlockEntity) inventory.player.world.getBlockEntity(buf.readBlockPos()));

    }

    public CoalGeneratorScreenHandler(int syncId, PlayerInventory inventory, CoalGeneratorBlockEntity entity) {
        super(ModScreenHandlers.COAL_GENERATOR_SCREEN_HANDLER, syncId, inventory, entity, new Slot[] { new Slot(entity, 0, 80, 40) });
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 11;
    }
}