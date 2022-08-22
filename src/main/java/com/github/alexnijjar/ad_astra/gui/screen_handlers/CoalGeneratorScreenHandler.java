package com.github.alexnijjar.ad_astra.gui.screen_handlers;

import com.github.alexnijjar.ad_astra.blocks.machines.entity.CoalGeneratorBlockEntity;
import com.github.alexnijjar.ad_astra.registry.ModScreenHandlers;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class CoalGeneratorScreenHandler extends AbstractMachineScreenHandler {

    public CoalGeneratorScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, (CoalGeneratorBlockEntity) inventory.player.world.getBlockEntity(buf.readBlockPos()));

    }

    public CoalGeneratorScreenHandler(int syncId, PlayerInventory inventory, CoalGeneratorBlockEntity entity) {
        super(ModScreenHandlers.COAL_GENERATOR_SCREEN_HANDLER, syncId, inventory, entity, new Slot[] { new Slot(entity, 0, 77, 31) });
    }
}