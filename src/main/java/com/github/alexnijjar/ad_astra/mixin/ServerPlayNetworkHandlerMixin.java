package com.github.alexnijjar.ad_astra.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.alexnijjar.ad_astra.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.ad_astra.items.armour.JetSuit;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {

	@Shadow
	private int floatingTicks;
	@Shadow
	private int vehicleFloatingTicks;

	@Inject(method = "tick", at = @At("HEAD"))
	public void adastra_tick(CallbackInfo ci) {
		ServerPlayerEntity player = ((ServerPlayNetworkHandler) (Object) this).player;

		// Prevent the player from being kicked for flying a jet suit
		if (!player.isOnGround() && JetSuit.hasFullSet(player)) {
			this.floatingTicks = 0;
		}

		// Prevent the player from being kicked for flying in a rocket
		if (player.getVehicle() instanceof VehicleEntity) {
			this.vehicleFloatingTicks = 0;
		}
	}
}
