package com.github.alexnijjar.beyond_earth.mixin.client;

import com.github.alexnijjar.beyond_earth.client.screens.PlayerOverlayScreen;
import com.github.alexnijjar.beyond_earth.entities.vehicles.LanderEntity;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntity;
import com.github.alexnijjar.beyond_earth.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.beyond_earth.items.armour.SpaceSuit;
import com.github.alexnijjar.beyond_earth.util.ModUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    public void baseTick(CallbackInfo info) {

        ClientPlayerEntity player = ((ClientPlayerEntity) (Object) this);
        boolean disableOverlays = false;

        PlayerOverlayScreen.shouldRenderOxygen = ModUtils.hasFullSpaceSet(player);

        if (ModUtils.hasFullSpaceSet(player)) {
            ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
            int oxygen = 0;
            if (chest.getItem() instanceof SpaceSuit suit) {
                NbtCompound nbt = chest.getNbt();

                if (nbt.contains("Oxygen")) {
                    oxygen = nbt.getInt("Oxygen");
                }

                // Render oxygen info.
                double ratio = oxygen / (float) suit.getMaxOxygen();
                PlayerOverlayScreen.oxygenRatio = ratio;
            }
        }

        if (player.getVehicle() instanceof VehicleEntity vehicle) {
            // Rocket.
            if (vehicle.renderPlanetBar()) {
                PlayerOverlayScreen.shouldRenderBar = true;
                if (vehicle instanceof RocketEntity rocket) {
                    if (rocket.isFlying()) {
                        PlayerOverlayScreen.countdownSeconds = rocket.getCountdownSeconds();
                    }
                }
            }

            // Show the warning screen when falling in a lander.
            if (vehicle instanceof LanderEntity lander) {

                double speed = lander.getVelocity().getY();
                if (speed != 0) {
                    PlayerOverlayScreen.shouldRenderWarning = true;
                    PlayerOverlayScreen.speed = speed * 60;
                } else {
                    disableOverlays = true;
                }
            }

        } else {
            disableOverlays = true;
        }

        if (disableOverlays) {
            // Disable all of the overlays.
            PlayerOverlayScreen.shouldRenderBar = false;
            PlayerOverlayScreen.countdownSeconds = 0;
            PlayerOverlayScreen.shouldRenderWarning = false;
        }
    }
}
