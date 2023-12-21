package earth.terrarium.adastra.mixins.common.gravity;

import earth.terrarium.adastra.api.systems.GravityApi;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Boat.class)
public abstract class BoatMixin extends Entity {

    public BoatMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "floatBoat", at = @At("TAIL"))
    public void adastra$floatBoat(CallbackInfo ci) {
        double gravity = -0.04 * GravityApi.API.getGravity(this);
        Vec3 velocity = this.getDeltaMovement();
        this.setDeltaMovement(velocity.x(), velocity.y() + 0.04 + gravity, velocity.z());
    }
}
