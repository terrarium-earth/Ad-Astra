package earth.terrarium.ad_astra.screen.handler;

import earth.terrarium.ad_astra.blocks.machines.entity.CryoFreezerBlockEntity;
import earth.terrarium.ad_astra.networking.NetworkHandling;
import earth.terrarium.ad_astra.networking.packets.server.MachineInfoPacket;
import earth.terrarium.ad_astra.registry.ModScreenHandlers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class CryoFreezerScreenHandler extends AbstractMachineScreenHandler {

    public CryoFreezerScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, (CryoFreezerBlockEntity) inventory.player.world.getBlockEntity(buf.readBlockPos()));
    }

    public CryoFreezerScreenHandler(int syncId, PlayerInventory inventory, CryoFreezerBlockEntity entity) {
        super(ModScreenHandlers.CRYO_FREEZER_SCREEN_HANDLER.get(), syncId, inventory, entity, new Slot[]{

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
                }});
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 15;
    }

    @Override
    public void syncClientScreen() {
        NetworkHandling.CHANNEL.sendToPlayer(new MachineInfoPacket(blockEntity.getEnergy(), ((CryoFreezerBlockEntity) blockEntity).getFluidContainer().getFluids()), this.player);
    }
}