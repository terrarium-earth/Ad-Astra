package com.github.alexnijjar.beyond_earth.gui.screen_handlers;

import com.github.alexnijjar.beyond_earth.blocks.machines.entity.OxygenDistributorBlockEntity;
import com.github.alexnijjar.beyond_earth.registry.ModScreenHandlers;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class OxygenDistributorScreenHandler extends AbstractMachineScreenHandler {

    public OxygenDistributorScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, (OxygenDistributorBlockEntity) inventory.player.world.getBlockEntity(buf.readBlockPos()));
    }

    public OxygenDistributorScreenHandler(int syncId, PlayerInventory inventory, OxygenDistributorBlockEntity entity) {
        super(ModScreenHandlers.OXYGEN_DISTRIBUTOR_SCREEN_HANDLER, syncId, inventory, entity, new Slot[] {

                // Left Insert.
                new Slot(entity, 0, 17, 58),
                // Left Extract.
                new Slot(entity, 1, 17, 88) {
                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return false;
                    }
                } });
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 54;
    }
}