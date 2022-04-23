package net.mrscauthd.beyond_earth.mixin.gravity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.entity.projectile.FishingBobberEntity;
import net.mrscauthd.beyond_earth.util.ModUtils;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin {
    @ModifyConstant(method = "tick", constant = @Constant(doubleValue = -0.03))
    double getGravity(double value) {
        return ModUtils.getMixinGravity(value, this);
    }
}