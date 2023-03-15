package earth.terrarium.ad_astra.mixin;

import earth.terrarium.ad_astra.common.util.ModUtils;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void ad_astra$tick(CallbackInfo ci) {
        Entity entity = ((Entity) (Object) this);

        // Teleport the entity to the planet when they fall in the void while in an orbit dimension
        if (entity.getY() < entity.level.getMinBuildHeight() && ModUtils.isOrbitlevel(entity.level)) {
            ModUtils.teleportToLevel(ModUtils.getPlanetOrbit(entity.level), entity);
        }
    }
}