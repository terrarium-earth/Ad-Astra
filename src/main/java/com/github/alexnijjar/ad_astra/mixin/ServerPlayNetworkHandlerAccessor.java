package com.github.alexnijjar.ad_astra.mixin;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerPlayNetworkHandler.class)
public interface ServerPlayNetworkHandlerAccessor {

	@Accessor("player")
	ServerPlayerEntity getPlayer();

	@Accessor("floatingTicks")
	void setFloatingTicks(int value);

	@Accessor("vehicleFloatingTicks")
	void seVehicleFloatingTicks(int value);
}
