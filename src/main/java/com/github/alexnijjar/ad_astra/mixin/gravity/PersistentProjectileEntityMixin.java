package com.github.alexnijjar.ad_astra.mixin.gravity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.util.ModUtils;

import net.minecraft.entity.projectile.PersistentProjectileEntity;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin {

    @ModifyConstant(method = "tick", constant = @Constant(doubleValue = 0.05000000074505806))
    public double setGravity(double value) {
        if (AdAstra.CONFIG.world.doEntityGravity) {
            return ModUtils.getMixinGravity(value, this);
        } else {
            return value;
        }
    }
}