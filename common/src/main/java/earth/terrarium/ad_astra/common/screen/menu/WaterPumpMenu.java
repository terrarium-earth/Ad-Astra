package earth.terrarium.ad_astra.common.screen.menu;

import earth.terrarium.ad_astra.common.block.machine.entity.WaterPumpBlockEntity;
import earth.terrarium.ad_astra.common.networking.NetworkHandling;
import earth.terrarium.ad_astra.common.networking.packet.server.MachineInfoPacket;
import earth.terrarium.ad_astra.common.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class WaterPumpMenu extends AbstractMachineMenu<WaterPumpBlockEntity> {

    public WaterPumpMenu(int syncId, Inventory inventory, FriendlyByteBuf buf) {
        this(syncId, inventory, (WaterPumpBlockEntity) inventory.player.level.getBlockEntity(buf.readBlockPos()));
    }

    public WaterPumpMenu(int syncId, Inventory inventory, WaterPumpBlockEntity entity) {
        super(ModMenuTypes.WATER_PUMP_MENU.get(), syncId, inventory, entity, new Slot[]{});
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 14;
    }

    @Override
    public void syncClientScreen() {
        NetworkHandling.CHANNEL.sendToPlayer(new MachineInfoPacket(machine.getEnergyStorage().getStoredEnergy(), machine.getFluidContainer().getFluids()), this.player);
    }
}