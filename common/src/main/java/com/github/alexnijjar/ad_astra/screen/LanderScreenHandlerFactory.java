package com.github.alexnijjar.ad_astra.screen;

import com.github.alexnijjar.ad_astra.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.ad_astra.screen.handler.LanderScreenHandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

public record LanderScreenHandlerFactory(VehicleEntity vehicle) implements NamedScreenHandlerFactory {
	
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
		return new LanderScreenHandler(syncId, inventory, vehicle);
	}

	@Override
	public Text getDisplayName() {
		return vehicle.getDisplayName();
	}
}