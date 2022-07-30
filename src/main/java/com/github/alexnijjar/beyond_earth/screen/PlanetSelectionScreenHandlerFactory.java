package com.github.alexnijjar.beyond_earth.screen;

import com.github.alexnijjar.beyond_earth.screen.handler.PlanetSelectionScreenHandler;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public record PlanetSelectionScreenHandlerFactory(int tier) implements ExtendedScreenHandlerFactory {

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeInt(tier);
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        return new PlanetSelectionScreenHandler(syncId, player, tier);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("gui.beyond_earth.planet_selection.name");
    }
}