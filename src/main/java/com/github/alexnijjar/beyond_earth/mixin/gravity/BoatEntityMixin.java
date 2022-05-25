package com.github.alexnijjar.beyond_earth.mixin.gravity;

import com.github.alexnijjar.beyond_earth.util.ModUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.entity.vehicle.BoatEntity;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin {
    @ModifyConstant(method = "updateVelocity", constant = @Constant(doubleValue = -0.03999999910593033, ordinal = 1))
    public double setGravity(double value) {
        return ModUtils.getMixinGravity(value, this);
    }
}