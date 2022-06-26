package com.github.alexnijjar.beyond_earth.mixin.gravity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.util.ModUtils;

import net.minecraft.entity.projectile.FishingBobberEntity;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin {
    @ModifyConstant(method = "tick", constant = @Constant(doubleValue = -0.03))
    double getGravity(double value) {
        if (BeyondEarth.CONFIG.mainConfig.doEntityGravity) {
            return ModUtils.getMixinGravity(value, this);
        } else {
            return value;
        }
    }
}