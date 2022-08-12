package com.github.alexnijjar.ad_astra.mixin;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.items.armour.JetSuit;
import com.github.alexnijjar.ad_astra.items.armour.NetheriteSpaceSuit;
import com.github.alexnijjar.ad_astra.util.ModKeyBindings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

	@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
	public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> ci) {
		if (AdAstra.CONFIG.spaceSuit.netheriteSpaceSuitHasFireResistance) {
			PlayerEntity player = ((PlayerEntity) (Object) this);
			if (source.isFire() || source.equals(DamageSource.HOT_FLOOR)) {
				if (NetheriteSpaceSuit.hasFullSet(player)) {
					player.setFireTicks(0);
					ci.setReturnValue(false);
				}
			}
		}
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void tick(CallbackInfo ci) {
		if (AdAstra.CONFIG.spaceSuit.enableJetSuitFlight) {
			PlayerEntity player = ((PlayerEntity) (Object) this);
			if (!player.hasVehicle()) {
				if (JetSuit.hasFullSet(player)) {
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
		if (AdAstra.CONFIG.spaceSuit.enableJetSuitFlight) {
			PlayerEntity player = ((PlayerEntity) (Object) this);
			if (!player.hasVehicle()) {
				if (JetSuit.hasFullSet(player)) {
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