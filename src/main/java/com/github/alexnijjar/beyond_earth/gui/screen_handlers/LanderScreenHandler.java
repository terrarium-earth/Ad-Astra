package com.github.alexnijjar.beyond_earth.gui.screen_handlers;

import com.github.alexnijjar.beyond_earth.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.beyond_earth.gui.NoInventorySlot;
import com.github.alexnijjar.beyond_earth.registry.ModScreenHandlers;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class LanderScreenHandler extends AbstractVehicleScreenHandler {

    public LanderScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, (VehicleEntity) inventory.player.world.getEntityById(buf.readInt()));
    }

    public LanderScreenHandler(int syncId, PlayerInventory inventory, VehicleEntity entity) {
        super(ModScreenHandlers.LANDER_SCREEN_HANDLER, syncId, inventory, entity, new Slot[] {

                // Left input slot.
                new Slot(entity.getInventory(), 0, 8, 63),
                // Left output slot.
                new Slot(entity.getInventory(), 1, 26, 63) {
                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return false;
                    }
                },

                // Inventory
                new NoInventorySlot(entity.getInventory(), 2, 98, 15),
                //
                new NoInventorySlot(entity.getInventory(), 3, 98 + 18, 15),
                //
                new NoInventorySlot(entity.getInventory(), 4, 98 + 18 * 2, 15),
                //
                new NoInventorySlot(entity.getInventory(), 5, 98 + 18 * 3, 15),
                //
                new NoInventorySlot(entity.getInventory(), 6, 98, 33),
                //
                new NoInventorySlot(entity.getInventory(), 7, 98 + 18, 33),
                //
                new NoInventorySlot(entity.getInventory(), 8, 98 + 18 * 2, 33),
                //
                new NoInventorySlot(entity.getInventory(), 9, 98 + 18 * 3, 33),
                
                // Rocket slot.
                new Slot(entity.getInventory(), 10, 40, 24) {
                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return false;
                    }
                }, });
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 9;
    }
}