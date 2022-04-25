package net.mrscauthd.beyond_earth.gui.screen_handlers;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;
import net.mrscauthd.beyond_earth.blocks.machines.entity.NasaWorkbenchBlockEntity;
import net.mrscauthd.beyond_earth.registry.ModScreenHandlers;

public class NasaWorkbenchScreenHandler extends AbstractMachineScreenHandler {

    public NasaWorkbenchScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, (NasaWorkbenchBlockEntity) inventory.player.world.getBlockEntity(buf.readBlockPos()));
    }

    public NasaWorkbenchScreenHandler(int syncId, PlayerInventory inventory, NasaWorkbenchBlockEntity entity) {
        super(ModScreenHandlers.NASA_WORKBENCH_SCREEN_HANDLER, syncId, inventory, entity, new Slot[] {

                // Nose.
                new Slot(entity, 0, 40, 18),

                // Body.
                new Slot(entity, 1, 31, 18 * 2), new Slot(entity, 2, 49, 18 * 2), new Slot(entity, 3, 31, 18 * 3), new Slot(entity, 4, 49, 18 * 3), new Slot(entity, 5, 31, 18 * 4),
                new Slot(entity, 6, 49, 18 * 4),

                // Left fin.
                new Slot(entity, 7, 13, 18 * 5),

                // Tank.
                new Slot(entity, 8, 31, 18 * 5), new Slot(entity, 9, 49, 18 * 5),

                // Right fin.
                new Slot(entity, 10, 67, 18 * 5),

                // Left fin.
                new Slot(entity, 11, 13, 18 * 6),

                // Engine.
                new Slot(entity, 12, 40, 18 * 6),

                // Right fin.
                new Slot(entity, 13, 67, 18 * 6),

                new Slot(entity, 14, 133, 74) {
                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return false;
                    }
                } });
    }

    @Override
    protected void setPlayerInventory(PlayerInventory inventory) {
        int m;
        int l;

        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                addSlot(new Slot(inventory, l + m * 9 + 9, 8 + l * 18, 142 + m * 18));
            }
        }

        for (m = 0; m < 9; ++m) {
            addSlot(new Slot(inventory, m, 8 + m * 18, 200));
        }
    }
}