package com.github.alexnijjar.beyond_earth.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.items.armour.JetSuit;
import com.github.alexnijjar.beyond_earth.util.ModKeyBindings;
import com.github.alexnijjar.beyond_earth.util.ModUtils;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> ci) {
        if (BeyondEarth.CONFIG.spaceSuit.netheriteSpaceSuitHasFireResistance) {
            PlayerEntity player = ((PlayerEntity) (Object) this);
            if (source.isFire() || source.equals(DamageSource.HOT_FLOOR) ) {
                if (ModUtils.hasFullNetheriteSpaceSet(player)) {
                    player.setFireTicks(0);
                    ci.setReturnValue(false);
                }
            }
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        if (BeyondEarth.CONFIG.spaceSuit.enableJetSuitFlight) {
            PlayerEntity player = ((PlayerEntity) (Object) this);
            if (!player.hasVehicle()) {
                if (ModUtils.hasFullJetSuitSet(player)) {
                    ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
                    if (chest.getItem() instanceof JetSuit jetSuit) {
                        if (ModKeyBindings.jumpKeyDown(player)) {
                            jetSuit.fly(player, chest);
                        } else {
                            jetSuit.isFallFlying = false;
                        }
                    }
                }
            }
        }
    }

    @Inject(method = "checkFallFlying", at = @At("HEAD"), cancellable = true)
    public void checkFallFlying(CallbackInfoReturnable<Boolean> ci) {
        if (BeyondEarth.CONFIG.spaceSuit.enableJetSuitFlight) {
            PlayerEntity player = ((PlayerEntity) (Object) this);
            if (!player.hasVehicle()) {
                if (ModUtils.hasFullJetSuitSet(player)) {
                    if (player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof JetSuit jetSuit) {
                        if (ModKeyBindings.sprintKeyDown(player)) {
                            ci.setReturnValue(true);
                        }
                    }
                }
            }
        }
    }
}