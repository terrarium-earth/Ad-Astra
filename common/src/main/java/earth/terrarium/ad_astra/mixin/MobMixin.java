package earth.terrarium.ad_astra.mixin;

import earth.terrarium.ad_astra.common.entity.system.EntityOxygenStatus;
import earth.terrarium.ad_astra.common.entity.system.EntityOxygenSystem;
import earth.terrarium.ad_astra.common.util.OxygenUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {

    protected MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "checkSpawnObstruction", at = @At("HEAD"), cancellable = true)
    public void ad_astra$checkSpawnObstruction(LevelReader levelReader, CallbackInfoReturnable<Boolean> cir) {
        if (levelReader instanceof Level level && !OxygenUtils.levelHasOxygen(level)) {
            if (EntityOxygenSystem.getOxygenStatus(this, level) == EntityOxygenStatus.DAMAGE) {
                cir.setReturnValue(false);
            }
        }
    }
}
