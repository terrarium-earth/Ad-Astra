package net.mrscauthd.beyond_earth.mixin.gravity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldEvents;
import net.mrscauthd.beyond_earth.util.ModUtils;
import net.mrscauthd.beyond_earth.world.SoundUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyConstant(method = "travel", constant = @Constant(doubleValue = 0.08))
    public double setGravity(double value) {
        LivingEntity entity = ((LivingEntity) (Object) this);
        return value * ModUtils.getPlanetGravity(entity.world.getRegistryKey());
    }

    // Make fall damage gravity-dependant.
    @ModifyVariable(method = "handleFallDamage", at = @At("HEAD"), ordinal = 1)
    private float handleFallDamage(float damageMultiplier) {
        LivingEntity entity = ((LivingEntity) (Object) this);
        return damageMultiplier * ModUtils.getPlanetGravity(entity.world.getRegistryKey());
    }

    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    public void handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> info) {
        LivingEntity entity = ((LivingEntity) (Object) this);
        if (fallDistance <= 3 / ModUtils.getPlanetGravity(entity.world.getRegistryKey())) {
            info.setReturnValue(false);
        }
    }
}
