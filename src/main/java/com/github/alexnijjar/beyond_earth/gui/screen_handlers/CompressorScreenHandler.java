package com.github.alexnijjar.beyond_earth.gui.screen_handlers;

import com.github.alexnijjar.beyond_earth.blocks.machines.entity.CompressorBlockEntity;
import com.github.alexnijjar.beyond_earth.registry.ModScreenHandlers;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class CompressorScreenHandler extends AbstractMachineScreenHandler {

    public CompressorScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, (CompressorBlockEntity) inventory.player.world.getBlockEntity(buf.readBlockPos()));

    }

    public CompressorScreenHandler(int syncId, PlayerInventory inventory, CompressorBlockEntity entity) {
        super(ModScreenHandlers.COMPRESSOR_SCREEN_HANDLER, syncId, inventory, entity, new Slot[] { new Slot(entity, 0, 40, 37), new Slot(entity, 1, 92, 36) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        } });
    }
}