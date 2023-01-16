package earth.terrarium.ad_astra.mixin;

import earth.terrarium.ad_astra.common.system.OxygenSystem;
import earth.terrarium.ad_astra.common.system.TemperatureSystem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    public void adastra_tick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.level instanceof ServerLevel level) {
            if (entity instanceof Player p && (p.isCreative() || p.isSpectator())) return;
            OxygenSystem.livingEntityTick(entity, level);
            TemperatureSystem.livingEntityTick(entity, level);
        }
    }
}
