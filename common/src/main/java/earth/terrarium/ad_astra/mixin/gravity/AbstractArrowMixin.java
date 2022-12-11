package earth.terrarium.ad_astra.mixin.gravity;

import earth.terrarium.ad_astra.common.config.AdAstraConfig;
import earth.terrarium.ad_astra.common.util.ModUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin {

    @Unique
    private static final double CONSTANT = 0.05;

    @Inject(method = "tick", at = @At("TAIL"))
    public void adastra_tick(CallbackInfo ci) {
        if (AdAstraConfig.doEntityGravity) {
            Entity entity = (Entity) (Object) this;
            if (!entity.isNoGravity()) {
                Vec3 velocity = entity.getDeltaMovement();
                double newGravity = CONSTANT * ModUtils.getPlanetGravity(entity.level);
                entity.setDeltaMovement(velocity.x(), velocity.y() + CONSTANT - newGravity, velocity.z());
            }
        }
    }
}