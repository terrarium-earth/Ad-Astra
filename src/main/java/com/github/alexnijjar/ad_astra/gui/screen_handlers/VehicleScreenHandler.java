package com.github.alexnijjar.ad_astra.gui.screen_handlers;

import com.github.alexnijjar.ad_astra.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.ad_astra.registry.ModScreenHandlers;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class VehicleScreenHandler extends AbstractVehicleScreenHandler {

    public VehicleScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, (VehicleEntity) inventory.player.world.getEntityById(buf.readInt()));
    }

    public VehicleScreenHandler(int syncId, PlayerInventory inventory, VehicleEntity entity) {
        super(ModScreenHandlers.VEHICLE_SCREEN_HANDLER, syncId, inventory, entity, new Slot[] {

                // Left input slot.
                new Slot(entity.getInventory(), 0, 8, 63),
                // Left output slot.
                new Slot(entity.getInventory(), 1, 26, 63) {
                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return false;
                    }
                },

                // Inventory.
                new Slot(entity.getInventory(), 2, 97, 16),
                //
                new Slot(entity.getInventory(), 3, 97 + 18, 16),
                //
                new Slot(entity.getInventory(), 4, 97 + 18 * 2, 16),
                //
                new Slot(entity.getInventory(), 5, 97 + 18 * 3, 16),
                //
                new Slot(entity.getInventory(), 6, 97, 34),
                //
                new Slot(entity.getInventory(), 7, 97 + 18, 34),
                //
                new Slot(entity.getInventory(), 8, 97 + 18 * 2, 34),
                //
                new Slot(entity.getInventory(), 9, 97 + 18 * 3, 34), });
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 10;
    }
}