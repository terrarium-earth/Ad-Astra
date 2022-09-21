package com.github.alexnijjar.ad_astra.screen;

import com.github.alexnijjar.ad_astra.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.ad_astra.screen.handler.LanderScreenHandler;

import earth.terrarium.botarium.api.menu.ExtraDataMenuProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public record LanderScreenHandlerFactory(VehicleEntity vehicle) implements ExtraDataMenuProvider {
	
	@Override
	public void writeExtraData(ServerPlayerEntity player, PacketByteBuf buf) {
		buf.writeInt(vehicle.getId());
	}
	
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
		return new LanderScreenHandler(syncId, inventory, vehicle);
	}

	@Override
	public Text getDisplayName() {
		return vehicle.getDisplayName();
	}
}