package earth.terrarium.ad_astra.screen.handler;

import earth.terrarium.ad_astra.blocks.machines.entity.SolarPanelBlockEntity;
import earth.terrarium.ad_astra.networking.NetworkHandling;
import earth.terrarium.ad_astra.networking.packets.server.MachineInfoPacket;
import earth.terrarium.ad_astra.registry.ModScreenHandlers;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class SolarPanelScreenHandler extends AbstractMachineScreenHandler<SolarPanelBlockEntity> {

    public SolarPanelScreenHandler(int syncId, Inventory inventory, FriendlyByteBuf buf) {
        this(syncId, inventory, (SolarPanelBlockEntity) inventory.player.level.getBlockEntity(buf.readBlockPos()));
    }

    public SolarPanelScreenHandler(int syncId, Inventory inventory, SolarPanelBlockEntity entity) {
        super(ModScreenHandlers.SOLAR_PANEL_SCREEN_HANDLER.get(), syncId, inventory, entity);
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 62;
    }

    @Override
    public void syncClientScreen() {
        NetworkHandling.CHANNEL.sendToPlayer(new MachineInfoPacket(machine.getEnergyStorage().getStoredEnergy(), List.of()), this.player);
    }
}