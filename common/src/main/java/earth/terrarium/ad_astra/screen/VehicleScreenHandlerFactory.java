package earth.terrarium.ad_astra.screen;

import earth.terrarium.ad_astra.entities.vehicles.VehicleEntity;
import earth.terrarium.ad_astra.screen.handler.VehicleScreenHandler;
import earth.terrarium.botarium.api.menu.ExtraDataMenuProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public record VehicleScreenHandlerFactory(VehicleEntity vehicle) implements ExtraDataMenuProvider {

    @Override
    public void writeExtraData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeInt(vehicle.getId());
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        return new VehicleScreenHandler(syncId, inventory, vehicle);
    }

    @Override
    public Text getDisplayName() {
        return vehicle.getDisplayName();
    }
}