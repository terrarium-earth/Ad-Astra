package earth.terrarium.ad_astra.screen.handler;

import earth.terrarium.ad_astra.blocks.machines.entity.WaterPumpBlockEntity;
import earth.terrarium.ad_astra.networking.NetworkHandling;
import earth.terrarium.ad_astra.networking.packets.server.MachineInfoPacket;
import earth.terrarium.ad_astra.registry.ModScreenHandlers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class WaterPumpScreenHandler extends AbstractMachineScreenHandler {

    public WaterPumpScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, (WaterPumpBlockEntity) inventory.player.world.getBlockEntity(buf.readBlockPos()));
    }

    public WaterPumpScreenHandler(int syncId, PlayerInventory inventory, WaterPumpBlockEntity entity) {
        super(ModScreenHandlers.WATER_PUMP_SCREEN_HANDLER.get(), syncId, inventory, entity, new Slot[]{});
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 14;
    }

    @Override
    public void syncClientScreen() {
        NetworkHandling.CHANNEL.sendToPlayer(new MachineInfoPacket(blockEntity.getEnergy(), ((WaterPumpBlockEntity) blockEntity).getFluidContainer().getFluids()), this.player);
    }
}