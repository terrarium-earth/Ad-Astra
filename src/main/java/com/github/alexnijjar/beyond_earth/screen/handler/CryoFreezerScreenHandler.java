package com.github.alexnijjar.beyond_earth.screen.handler;

import com.github.alexnijjar.beyond_earth.blocks.machines.entity.FluidMachineBlockEntity;
import com.github.alexnijjar.beyond_earth.registry.ModScreenHandlers;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class CryoFreezerScreenHandler extends AbstractMachineScreenHandler {

    public CryoFreezerScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, (FluidMachineBlockEntity) inventory.player.world.getBlockEntity(buf.readBlockPos()));
    }

    public CryoFreezerScreenHandler(int syncId, PlayerInventory inventory, FluidMachineBlockEntity entity) {
        super(ModScreenHandlers.CRYO_FREEZER_SCREEN_HANDLER, syncId, inventory, entity, new Slot[] {

                // Left Insert.
                new Slot(entity, 0, 26, 70),

                // Right Insert.
                new Slot(entity, 1, 113, 42),
                // Right Extract.
                new Slot(entity, 2, 113, 70) {
                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return false;
                    }
                } });
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 15;
    }
}