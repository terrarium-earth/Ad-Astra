package earth.terrarium.ad_astra.screen;

import earth.terrarium.ad_astra.entities.vehicles.VehicleEntity;
import earth.terrarium.ad_astra.screen.handler.LanderScreenHandler;
import earth.terrarium.botarium.api.menu.ExtraDataMenuProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

public record LanderScreenHandlerFactory(VehicleEntity vehicle) implements ExtraDataMenuProvider {

    @Override
    public void writeExtraData(ServerPlayer player, FriendlyByteBuf buf) {
        buf.writeInt(vehicle.getId());
    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory inventory, @NotNull Player player) {
        return new LanderScreenHandler(syncId, inventory, vehicle);
    }

    @Override
    public Component getDisplayName() {
        return vehicle.getDisplayName();
    }
}