package com.github.alexnijjar.ad_astra.screen;

import com.github.alexnijjar.ad_astra.screen.handler.PlanetSelectionScreenHandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

public record PlanetSelectionScreenHandlerFactory(int tier) implements NamedScreenHandlerFactory {

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
		return new PlanetSelectionScreenHandler(syncId, player, tier);
	}

	@Override
	public Text getDisplayName() {
		return Text.translatable("gui.ad_astra.planet_selection.name");
	}
}