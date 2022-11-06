package earth.terrarium.ad_astra.screen.menu;

import earth.terrarium.ad_astra.blocks.machines.entity.CompressorBlockEntity;
import earth.terrarium.ad_astra.networking.NetworkHandling;
import earth.terrarium.ad_astra.networking.packets.server.MachineInfoPacket;
import earth.terrarium.ad_astra.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class CompressorMenu extends AbstractMachineMenu<CompressorBlockEntity> {

    public CompressorMenu(int syncId, Inventory inventory, FriendlyByteBuf buf) {
        this(syncId, inventory, (CompressorBlockEntity) inventory.player.level.getBlockEntity(buf.readBlockPos()));

    }

    public CompressorMenu(int syncId, Inventory inventory, CompressorBlockEntity entity) {
        super(ModMenus.COMPRESSOR_MENU.get(), syncId, inventory, entity, new Slot[]{new Slot(entity, 0, 40, 62), new Slot(entity, 1, 92, 62) {
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
        NetworkHandling.CHANNEL.sendToPlayer(new MachineInfoPacket(machine.getEnergyStorage().getStoredEnergy(), List.of()), this.player);
    }
}