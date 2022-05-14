package net.mrscauthd.beyond_earth.gui;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.mrscauthd.beyond_earth.gui.screen_handlers.PlanetSelectionScreenHandler;

public record PlanetSelectionHandlerFactory(int tier) implements ExtendedScreenHandlerFactory {

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeInt(tier);
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new PlanetSelectionScreenHandler(syncId, player, tier);
    }

    @Override
    public Text getDisplayName() {
        return Text.of("Planet Selection");
    }
}