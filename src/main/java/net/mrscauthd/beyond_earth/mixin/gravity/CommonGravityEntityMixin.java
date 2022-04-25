package net.mrscauthd.beyond_earth.mixin.gravity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.mrscauthd.beyond_earth.util.ModUtils;

@Mixin({ AbstractMinecartEntity.class, ItemEntity.class, TntEntity.class })
public abstract class CommonGravityEntityMixin {
    @ModifyConstant(method = "tick", constant = @Constant(doubleValue = -0.04))
    public double setGravity(double value) {
        return ModUtils.getMixinGravity(value, this);
    }
}