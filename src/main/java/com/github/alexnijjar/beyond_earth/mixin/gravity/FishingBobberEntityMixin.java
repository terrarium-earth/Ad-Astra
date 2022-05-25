package com.github.alexnijjar.beyond_earth.mixin.gravity;

import com.github.alexnijjar.beyond_earth.util.ModUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.entity.projectile.FishingBobberEntity;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin {
    @ModifyConstant(method = "tick", constant = @Constant(doubleValue = -0.03))
    double getGravity(double value) {
        return ModUtils.getMixinGravity(value, this);
    }
}