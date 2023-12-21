package earth.terrarium.adastra.mixins.common.gravity;

import earth.terrarium.adastra.api.systems.GravityApi;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({AbstractMinecart.class, ItemEntity.class, PrimedTnt.class})
public abstract class GravityEntityMixin extends Entity {

    public GravityEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void adastra$tick(CallbackInfo ci) {
        double gravity = 0.04 * GravityApi.API.getGravity(this);
        Vec3 velocity = this.getDeltaMovement();
        this.setDeltaMovement(velocity.x(), velocity.y() + 0.04 - gravity, velocity.z());
    }
}
