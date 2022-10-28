package earth.terrarium.ad_astra.mixin.gravity;

import earth.terrarium.ad_astra.util.ModUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {AbstractMinecartEntity.class, ItemEntity.class, TntEntity.class})
public abstract class CommonGravityEntityMixin {

    @Unique
    private static final double CONSTANT = -0.04;

    @Inject(method = "tick", at = @At("TAIL"))
    public void adastra_tick(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if (!entity.hasNoGravity()) {
            Vec3d velocity = entity.getVelocity();
            double newGravity = CONSTANT * ModUtils.getPlanetGravity(entity.world);
            entity.setVelocity(velocity.getX(), velocity.getY() - CONSTANT + newGravity, velocity.getZ());
        }

        if (entity instanceof AbstractMinecartEntity && entity.getY() < entity.world.getBottomY() && ModUtils.isOrbitWorld(entity.world)) {
            ModUtils.teleportToWorld(ModUtils.getPlanetOrbit(entity.world), entity);
        }
    }
}