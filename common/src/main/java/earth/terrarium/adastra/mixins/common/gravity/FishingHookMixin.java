package earth.terrarium.adastra.mixins.common.gravity;

import earth.terrarium.adastra.api.systems.GravityApi;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingHook.class)
public abstract class FishingHookMixin extends Entity {

    public FishingHookMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void adastra$tick(CallbackInfo ci) {
        double gravity = -0.03 * GravityApi.API.getGravity(this);
        Vec3 velocity = this.getDeltaMovement();
        this.setDeltaMovement(velocity.x(), velocity.y() + 0.03 + gravity, velocity.z());
    }
}
