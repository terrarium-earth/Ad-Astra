package earth.terrarium.ad_astra.screen.menu;

import earth.terrarium.ad_astra.blocks.machines.entity.OxygenDistributorBlockEntity;
import earth.terrarium.ad_astra.networking.NetworkHandling;
import earth.terrarium.ad_astra.networking.packets.server.MachineInfoPacket;
import earth.terrarium.ad_astra.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class OxygenDistributorMenu extends AbstractMachineMenu<OxygenDistributorBlockEntity> {

    public OxygenDistributorMenu(int syncId, Inventory inventory, FriendlyByteBuf buf) {
        this(syncId, inventory, (OxygenDistributorBlockEntity) inventory.player.level.getBlockEntity(buf.readBlockPos()));
    }

    public OxygenDistributorMenu(int syncId, Inventory inventory, OxygenDistributorBlockEntity entity) {
        super(ModMenuTypes.OXYGEN_DISTRIBUTOR_MENU.get(), syncId, inventory, entity, new Slot[]{

                // Left Insert.
                new Slot(entity, 0, 17, 82),
                // Left Extract.
                new Slot(entity, 1, 17, 112) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return false;
                    }
                }});
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 78;
    }

    @Override
    public void syncClientScreen() {
        NetworkHandling.CHANNEL.sendToPlayer(new MachineInfoPacket(machine.getEnergyStorage().getStoredEnergy(), machine.getFluidContainer().getFluids()), this.player);
    }
}