package com.github.alexnijjar.beyond_earth.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.alexnijjar.beyond_earth.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.beyond_earth.util.ModUtils;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        ServerPlayNetworkHandlerAccessor handler = (ServerPlayNetworkHandlerAccessor) (ServerPlayNetworkHandlerMixin) (Object) this;
        ServerPlayerEntity player = handler.getPlayer();

        // Prevent the player from being kicked for flying a jet suit
        if (ModUtils.hasFullJetSuitSet(player)) {
            handler.setFloatingTicks(0);
        }

        // Prevent the player from being kicked for flying in a rocket
        if (player.getVehicle() instanceof VehicleEntity) {
            handler.seVehicleFloatingTicks(0);
        }
    }
}
