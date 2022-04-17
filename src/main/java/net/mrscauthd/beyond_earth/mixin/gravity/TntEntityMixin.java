package net.mrscauthd.beyond_earth.mixin.gravity;

import net.minecraft.entity.TntEntity;
import net.mrscauthd.beyond_earth.util.ModUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(TntEntity.class)
public abstract class TntEntityMixin {

    @ModifyConstant(method = "tick", constant = @Constant(doubleValue = -0.04))
    public double setGravity(double value) {
        TntEntity entity = ((TntEntity) (Object) this);
        return value * ModUtils.getPlanetGravity(entity.world.getRegistryKey());
    }
}