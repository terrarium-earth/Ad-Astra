package earth.terrarium.ad_astra.screen.menu;

import earth.terrarium.ad_astra.block.machine.entity.SolarPanelBlockEntity;
import earth.terrarium.ad_astra.networking.NetworkHandling;
import earth.terrarium.ad_astra.networking.packet.server.MachineInfoPacket;
import earth.terrarium.ad_astra.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class SolarPanelMenu extends AbstractMachineMenu<SolarPanelBlockEntity> {

    public SolarPanelMenu(int syncId, Inventory inventory, FriendlyByteBuf buf) {
        this(syncId, inventory, (SolarPanelBlockEntity) inventory.player.level.getBlockEntity(buf.readBlockPos()));
    }

    public SolarPanelMenu(int syncId, Inventory inventory, SolarPanelBlockEntity entity) {
        super(ModMenuTypes.SOLAR_PANEL_SCREEN_HANDLER.get(), syncId, inventory, entity);
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