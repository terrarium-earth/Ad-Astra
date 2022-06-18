package com.github.alexnijjar.beyond_earth.mixin;

import com.github.alexnijjar.beyond_earth.items.armour.JetSuit;
import com.github.alexnijjar.beyond_earth.util.ModKeyBindings;
import com.github.alexnijjar.beyond_earth.util.ModUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        PlayerEntity player = ((PlayerEntity) (Object) this);
        if (source.isFire() || source.equals(DamageSource.HOT_FLOOR)) {
            if ((player.isOnFire() || source.equals(DamageSource.HOT_FLOOR)) && ModUtils.hasFullNetheriteSpaceSet(player)) {
                info.setReturnValue(false);
            }
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo info) {
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

    @Inject(method = "checkFallFlying", at = @At("HEAD"), cancellable = true)
    public void checkFallFlying(CallbackInfoReturnable<Boolean> info) {
        PlayerEntity player = ((PlayerEntity) (Object) this);

        if (!player.hasVehicle()) {
            if (ModUtils.hasFullJetSuitSet(player)) {
                if (player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof JetSuit jetSuit) {
                    if (ModKeyBindings.sprintKeyDown(player)) {
                        info.setReturnValue(true);
                    }
                }
            }
        }
    }
}