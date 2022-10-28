package earth.terrarium.ad_astra.screen;

import earth.terrarium.ad_astra.screen.handler.PlanetSelectionScreenHandler;
import earth.terrarium.botarium.api.menu.ExtraDataMenuProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public record PlanetSelectionScreenHandlerFactory(int tier) implements ExtraDataMenuProvider {

    @Override
    public void writeExtraData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeInt(tier);
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        return new PlanetSelectionScreenHandler(syncId, player, tier);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("gui.ad_astra.planet_selection.name");
    }
}