package com.github.alexnijjar.beyond_earth.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.alexnijjar.beyond_earth.client.screens.PlayerOverlayScreen;
import com.github.alexnijjar.beyond_earth.entities.vehicles.LanderEntity;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntity;
import com.github.alexnijjar.beyond_earth.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.beyond_earth.items.armour.JetSuit;
import com.github.alexnijjar.beyond_earth.items.armour.SpaceSuit;
import com.github.alexnijjar.beyond_earth.util.OxygenUtils;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    public void baseTick(CallbackInfo ci) {

        ClientPlayerEntity player = ((ClientPlayerEntity) (Object) this);
        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);

        if (SpaceSuit.hasFullSet(player)) {
            PlayerOverlayScreen.shouldRenderOxygen = true;
            if (chest.getItem() instanceof SpaceSuit suit) {
                long oxygen = suit.getAmount(chest);

                // Render oxygen info
                double ratio = oxygen / (double) suit.getTankSize();
                PlayerOverlayScreen.oxygenRatio = ratio;
                PlayerOverlayScreen.doesNotNeedOxygen = OxygenUtils.worldHasOxygen(player.world, player) && !player.isSubmergedInWater();
            }
        } else {
            PlayerOverlayScreen.shouldRenderOxygen = false;
        }

        if (JetSuit.hasFullSet(player)) {
            PlayerOverlayScreen.shouldRenderBattery = true;
            if (chest.getItem() instanceof JetSuit suit) {

                // Render battery info
                double ratio = (double) suit.getStoredEnergy(chest) / (double) suit.getEnergyCapacity();
                PlayerOverlayScreen.batteryRatio = ratio;
            }
        } else {
            PlayerOverlayScreen.shouldRenderBattery = false;
        }

        if (player.getVehicle() instanceof VehicleEntity vehicle) {
            // Rocket
            if (vehicle.renderPlanetBar()) {
                PlayerOverlayScreen.shouldRenderBar = true;
                if (vehicle instanceof RocketEntity rocket) {
                    if (rocket.isFlying()) {
                        PlayerOverlayScreen.countdownSeconds = rocket.getCountdownSeconds();
                    }
                }
            }

            // Show the warning screen when falling in a lander
            if (vehicle instanceof LanderEntity lander) {

                double speed = lander.getVelocity().getY();
                if (speed < 0.0) {
                    PlayerOverlayScreen.shouldRenderWarning = true;
                    PlayerOverlayScreen.speed = speed * 55;
                } else {
                    PlayerOverlayScreen.disableAllVehicleOverlays();
                }
            }

        } else {
            PlayerOverlayScreen.disableAllVehicleOverlays();
        }
    }
}
