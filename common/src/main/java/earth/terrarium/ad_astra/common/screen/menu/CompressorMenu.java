package earth.terrarium.ad_astra.common.screen.menu;

import earth.terrarium.ad_astra.common.block.machine.entity.CompressorBlockEntity;
import earth.terrarium.ad_astra.common.networking.NetworkHandling;
import earth.terrarium.ad_astra.common.networking.packet.server.MachineInfoPacket;
import earth.terrarium.ad_astra.common.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class CompressorMenu extends ProcessingMachineMenu<CompressorBlockEntity> {

    public CompressorMenu(int syncId, Inventory inventory, FriendlyByteBuf buf) {
        this(syncId, inventory, (CompressorBlockEntity) inventory.player.level.getBlockEntity(buf.readBlockPos()));

    }

    public CompressorMenu(int syncId, Inventory inventory, CompressorBlockEntity entity) {
        super(ModMenuTypes.COMPRESSOR_MENU.get(), syncId, inventory, entity,
                new Slot[]{
                        new Slot(entity, 0, 53, 56),
                        new Slot(entity, 1, 100, 56) {
                            @Override
                            public boolean mayPlace(ItemStack stack) {
                                return false;
                            }
                        }});
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 30;
    }

    @Override
    public void syncClientScreen() {
        super.syncClientScreen();
        NetworkHandling.CHANNEL.sendToPlayer(new MachineInfoPacket(machine.getEnergyStorage().getStoredEnergy(), List.of()), this.player);
    }
}