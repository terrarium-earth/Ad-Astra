package com.github.alexnijjar.ad_astra.mixin.gravity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.util.ModUtils;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;

@Mixin({ AbstractMinecartEntity.class, ItemEntity.class, TntEntity.class })
public abstract class CommonGravityEntityMixin {
    @ModifyConstant(method = "tick", constant = @Constant(doubleValue = -0.04))
    public double setGravity(double value) {
        if (AdAstra.CONFIG.world.doEntityGravity) {
            return ModUtils.getMixinGravity(value, this);
        } else {
            return value;
        }
    }
}