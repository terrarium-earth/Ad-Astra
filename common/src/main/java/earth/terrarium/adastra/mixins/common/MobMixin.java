package earth.terrarium.adastra.mixins.common;

import earth.terrarium.adastra.api.planets.PlanetApi;
import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.common.tags.ModEntityTypeTags;
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

    // Prevent mobs that can't survive without oxygen from spawning on planets without oxygen. Fixes
    // mobs like Alex's Mobs flies.
    @Inject(method = "checkSpawnObstruction", at = @At("HEAD"), cancellable = true)
    public void adastra$checkSpawnObstruction(LevelReader levelReader, CallbackInfoReturnable<Boolean> cir) {
        if (levelReader instanceof Level level
            && PlanetApi.API.isPlanet(level)
            && !OxygenApi.API.hasOxygen(level)
            && !getType().is(ModEntityTypeTags.LIVES_WITHOUT_OXYGEN)) {
            cir.setReturnValue(false);
        }
    }
}
