package earth.terrarium.ad_astra.screen.handler;

import earth.terrarium.ad_astra.blocks.machines.entity.CryoFreezerBlockEntity;
import earth.terrarium.ad_astra.networking.NetworkHandling;
import earth.terrarium.ad_astra.networking.packets.server.MachineInfoPacket;
import earth.terrarium.ad_astra.registry.ModScreenHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CryoFreezerScreenHandler extends AbstractMachineScreenHandler<CryoFreezerBlockEntity> {

    public CryoFreezerScreenHandler(int syncId, Inventory inventory, FriendlyByteBuf buf) {
        this(syncId, inventory, (CryoFreezerBlockEntity) inventory.player.level.getBlockEntity(buf.readBlockPos()));
    }

    public CryoFreezerScreenHandler(int syncId, Inventory inventory, CryoFreezerBlockEntity entity) {
        super(ModScreenHandlers.CRYO_FREEZER_SCREEN_HANDLER.get(), syncId, inventory, entity, new Slot[]{

                // Left Insert.
                new Slot(entity, 0, 26, 70),

                // Right Insert.
                new Slot(entity, 1, 113, 42),
                // Right Extract.
                new Slot(entity, 2, 113, 70) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
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
        NetworkHandling.CHANNEL.sendToPlayer(new MachineInfoPacket(machine.getEnergyStorage().getStoredEnergy(), machine.getFluidContainer().getFluids()), this.player);
    }
}