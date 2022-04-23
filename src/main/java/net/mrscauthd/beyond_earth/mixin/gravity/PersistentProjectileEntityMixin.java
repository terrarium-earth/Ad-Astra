package net.mrscauthd.beyond_earth.mixin.gravity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.mrscauthd.beyond_earth.util.ModUtils;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin {

    @ModifyConstant(method = "tick", constant = @Constant(doubleValue = 0.05000000074505806))
    public double setGravity(double value) {
        return ModUtils.getMixinGravity(value, this);
    }
}