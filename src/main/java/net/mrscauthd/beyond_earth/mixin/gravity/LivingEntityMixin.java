package net.mrscauthd.beyond_earth.mixin.gravity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.mrscauthd.beyond_earth.util.ModUtils;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyConstant(method = "travel", constant = @Constant(doubleValue = 0.08))
    public double setGravity(double value) {
        return ModUtils.getMixinGravity(value, this);
    }

    // Make fall damage gravity-dependant.
    @ModifyVariable(method = "handleFallDamage", at = @At("HEAD"), ordinal = 1)
    private float handleFallDamage(float damageMultiplier) {
        LivingEntity entity = ((LivingEntity) (Object) this);
        return damageMultiplier * ModUtils.getPlanetGravity(entity.world.getRegistryKey());
    }

    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    public void handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource,
            CallbackInfoReturnable<Boolean> info) {
        LivingEntity entity = ((LivingEntity) (Object) this);
        if (fallDistance <= 3 / ModUtils.getPlanetGravity(entity.world.getRegistryKey())) {
            info.setReturnValue(false);
        }
    }
}