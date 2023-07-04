package earth.terrarium.adastra.mixins.common.gravity;

import earth.terrarium.adastra.api.systems.GravityApi;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThrownExperienceBottle.class)
public abstract class ThrownExperienceBottleMixin extends Entity {

    public ThrownExperienceBottleMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "getGravity", at = @At("HEAD"), cancellable = true)
    public void adastra$getGravity(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(0.07f * GravityApi.API.getGravity(this));
    }
}
