package net.mrscauthd.beyond_earth.gui.screen_handlers;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;
import net.mrscauthd.beyond_earth.blocks.machines.entity.CompressorBlockEntity;
import net.mrscauthd.beyond_earth.registry.ModScreenHandlers;

public class CompressorScreenHandler extends AbstractMachineScreenHandler {

    public CompressorScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, (CompressorBlockEntity) inventory.player.world.getBlockEntity(buf.readBlockPos()));

    }

    public CompressorScreenHandler(int syncId, PlayerInventory inventory, CompressorBlockEntity entity) {
        super(ModScreenHandlers.COMPRESSOR_SCREEN_HANDLER, syncId, inventory, entity,
                new Slot[]{
                        new Slot(entity, 0, 40, 37),
                        new Slot(entity, 1, 92, 36) {
                            @Override
                            public boolean canInsert(ItemStack stack) {
                                return false;
                            }
                        }
                }
        );
    }
}