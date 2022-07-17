package com.github.alexnijjar.beyond_earth.gui.screen_handlers;

import com.github.alexnijjar.beyond_earth.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.beyond_earth.gui.NoInventorySlot;
import com.github.alexnijjar.beyond_earth.registry.ModScreenHandlers;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantItemStorage;
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
                new NoInventorySlot(entity.getInventory(), 0, 8, 63) {
                    @Override
                    public boolean canInsert(ItemStack stack) {
                        if (!super.canInsert(stack)) {
                            return false;
                        }
                        Storage<FluidVariant> context = ContainerItemContext.withInitial(stack).find(FluidStorage.ITEM);
                        return context instanceof CombinedStorage || context instanceof FullItemFluidStorage || context instanceof SingleVariantItemStorage<FluidVariant>;
                    }
                },
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
                new NoInventorySlot(entity.getInventory(), 9, 98 + 18 * 3, 33), });
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 9;
    }
}