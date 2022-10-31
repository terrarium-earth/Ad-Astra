package earth.terrarium.ad_astra.screen;

import earth.terrarium.ad_astra.screen.handler.PlanetSelectionScreenHandler;
import earth.terrarium.botarium.api.menu.ExtraDataMenuProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

public record PlanetSelectionScreenHandlerFactory(int tier) implements ExtraDataMenuProvider {

    @Override
    public void writeExtraData(ServerPlayer player, FriendlyByteBuf buf) {
        buf.writeInt(tier);
    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory inventory, @NotNull Player player) {
        return new PlanetSelectionScreenHandler(syncId, player, tier);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("gui.ad_astra.planet_selection.name");
    }
}