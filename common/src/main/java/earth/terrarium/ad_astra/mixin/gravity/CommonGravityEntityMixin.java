package earth.terrarium.ad_astra.mixin.gravity;

import earth.terrarium.ad_astra.common.util.ModUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {AbstractMinecart.class, ItemEntity.class, PrimedTnt.class})
public abstract class CommonGravityEntityMixin {

    @Unique
    private static final double CONSTANT = -0.04;

    @Inject(method = "tick", at = @At("TAIL"))
    public void adastra_tick(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if (!entity.isNoGravity()) {
            Vec3 velocity = entity.getDeltaMovement();
            double newGravity = CONSTANT * ModUtils.getEntityGravity(entity);
            entity.setDeltaMovement(velocity.x(), velocity.y() - CONSTANT + newGravity, velocity.z());
        }

        if (entity instanceof AbstractMinecart && entity.getY() < entity.level.getMinBuildHeight() && ModUtils.isOrbitlevel(entity.level)) {
            ModUtils.teleportToLevel(ModUtils.getPlanetOrbit(entity.level), entity);
        }
    }
}