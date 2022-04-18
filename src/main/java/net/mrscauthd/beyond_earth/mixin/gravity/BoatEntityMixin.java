package net.mrscauthd.beyond_earth.mixin.gravity;

import net.minecraft.entity.vehicle.BoatEntity;
import net.mrscauthd.beyond_earth.util.GravityUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin {
    @ModifyConstant(method = "updateVelocity", constant = @Constant(doubleValue = -0.03999999910593033, ordinal = 1))
    public double setGravity(double value) {
        return GravityUtil.getMixinGravity(value, this);
    }
}
