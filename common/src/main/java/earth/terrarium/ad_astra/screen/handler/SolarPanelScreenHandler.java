package earth.terrarium.ad_astra.screen.handler;

import earth.terrarium.ad_astra.blocks.machines.entity.SolarPanelBlockEntity;
import earth.terrarium.ad_astra.registry.ModScreenHandlers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;

public class SolarPanelScreenHandler extends AbstractMachineScreenHandler {

    public SolarPanelScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, (SolarPanelBlockEntity) inventory.player.world.getBlockEntity(buf.readBlockPos()));
    }

    public SolarPanelScreenHandler(int syncId, PlayerInventory inventory, SolarPanelBlockEntity entity) {
        super(ModScreenHandlers.SOLAR_PANEL_SCREEN_HANDLER.get(), syncId, inventory, entity);
    }

    @Override
    public int getPlayerInventoryOffset() {
        return 62;
    }
}