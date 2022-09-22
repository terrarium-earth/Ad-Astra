package com.github.alexnijjar.ad_astra.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.alexnijjar.ad_astra.items.armour.JetSuit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

@Mixin(FireworkRocketItem.class)
public class FireworkRocketItemMixin {

	// Cancel firework boost with jet suit
	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void adastra_use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> ci) {
		if (JetSuit.hasFullSet(user)) {
			ci.setReturnValue(TypedActionResult.pass(user.getStackInHand(hand)));
		}
	}
}
