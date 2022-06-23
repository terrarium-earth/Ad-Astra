package com.github.alexnijjar.beyond_earth.gui.screen_handlers;

import com.github.alexnijjar.beyond_earth.blocks.machines.entity.OxygenBubbleDistributorBlockEntity;
import com.github.alexnijjar.beyond_earth.registry.ModScreenHandlers;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class OxygenBubbleDistributorScreenHandler extends AbstractMachineScreenHandler {

    public OxygenBubbleDistributorScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, (OxygenBubbleDistributorBlockEntity) inventory.player.world.getBlockEntity(buf.readBlockPos()));
    }

    public OxygenBubbleDistributorScreenHandler(int syncId, PlayerInventory inventory, OxygenBubbleDistributorBlockEntity entity) {
        super(ModScreenHandlers.OXYGEN_BUBBLE_DISTRIBUTOR_SCREEN_HANDLER, syncId, inventory, entity, new Slot[] {

                // Left Insert.
                new Slot(entity, 0, 26, 22),
                // Left Extract.
                new Slot(entity, 1, 26, 52) {
                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return false;
                    }
                } });
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 6;
    }
}